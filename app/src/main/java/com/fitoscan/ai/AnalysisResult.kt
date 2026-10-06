package com.fitoscan.ai

enum class Certainty(val label: String) {
    HIGH("Ridicată"), MEDIUM("Medie"), LOW("Scăzută"), UNKNOWN("Necunoscută")
}

data class PlantIdentification(
    val commonName: String = "Necunoscută",
    val scientificName: String = "",
    val certainty: Certainty = Certainty.UNKNOWN,
    val alternatives: List<String> = emptyList()
)

data class DifferentialCause(
    val name: String,
    val supportingEvidence: String = "",
    val contradictingEvidence: String = "",
    val verification: String = "",
    val certainty: Certainty = Certainty.UNKNOWN
)

data class FitoScanResult(
    val plant: PlantIdentification = PlantIdentification(),
    val observations: List<String> = emptyList(),
    val assessmentCategory: String = "Date insuficiente",
    val assessment: String = "Date insuficiente",
    val assessmentCertainty: Certainty = Certainty.UNKNOWN,
    val differentialDiagnosis: List<DifferentialCause> = emptyList(),
    val verificationNeeded: List<String> = emptyList(),
    val agronomicRecommendation: List<String> = emptyList(),
    val actionLevel: String = "VERIFICĂ",
    val additionalPhotosNeeded: List<String> = emptyList(),
    val analyzedPart: String = "necunoscută",
    val evidenceStatus: String = "neconfirmat",
    val safetyNote: String = ""
)
