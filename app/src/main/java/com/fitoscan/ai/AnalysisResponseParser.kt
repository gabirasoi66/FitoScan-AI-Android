package com.fitoscan.ai

import org.json.JSONObject

object AnalysisResponseParser {
    fun parse(raw: String): FitoScanResult {
        val envelope = JSONObject(raw)
        if (!envelope.optBoolean("ok", false)) {
            error(envelope.optString("error", "Analiza AI nu a reușit."))
        }
        val d = envelope.optJSONObject("data") ?: error("Răspuns AI fără câmpul data.")

        val confidence = when (d.optString("confidence")) {
            "Ridicată" -> Certainty.HIGH
            "Medie" -> Certainty.MEDIUM
            "Scăzută" -> Certainty.LOW
            else -> Certainty.UNKNOWN
        }

        val status = d.optString("status", "neconfirmat")
        val causes = strings(d, "possible_causes")
        val differential = strings(d, "differential_diagnosis")

        return FitoScanResult(
            plant = PlantIdentification(
                commonName = d.optString("plant", "Necunoscută"),
                scientificName = "",
                certainty = confidence
            ),
            observations = strings(d, "observations"),
            assessmentCategory = status,
            assessment = causes.firstOrNull()
                ?: if (confidence == Certainty.UNKNOWN) "Date insuficiente" else status,
            assessmentCertainty = confidence,
            differentialDiagnosis = differential.map {
                DifferentialCause(name = it, certainty = Certainty.UNKNOWN)
            },
            verificationNeeded = strings(d, "verify") + strings(d, "diagnostic_path"),
            agronomicRecommendation = listOfNotNull(
                d.optString("next_action").takeIf { it.isNotBlank() }
            ),
            actionLevel = actionLevel(status, confidence),
            additionalPhotosNeeded = strings(d, "recommended_photos"),
            analyzedPart = d.optString("part", "necunoscută"),
            evidenceStatus = status,
            safetyNote = d.optString(
                "safety_note",
                "Verifică documentația oficială și situația din teren înainte de intervenție."
            )
        )
    }

    private fun strings(o: JSONObject, key: String): List<String> {
        val a = o.optJSONArray(key) ?: return emptyList()
        return (0 until a.length()).mapNotNull { a.optString(it).takeIf(String::isNotBlank) }
    }

    private fun actionLevel(status: String, confidence: Certainty): String =
        when {
            confidence == Certainty.UNKNOWN || status == "neconfirmat" -> "VERIFICĂ"
            status == "observație" -> "MONITORIZEAZĂ"
            else -> "CONSULTĂ OPȚIUNILE DE COMBATERE"
        }
}
