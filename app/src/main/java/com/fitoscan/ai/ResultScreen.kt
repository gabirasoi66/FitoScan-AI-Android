package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

object ResultScreen {
    fun show(activity: AppCompatActivity, result: FitoScanResult, onBack: () -> Unit) {
        val root = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 48)
            setBackgroundColor(Color.rgb(247, 249, 246))
        }
        root.addView(title(activity, "Rezultat FitoScan"))
        root.addView(card(activity, "🌿 Identificarea plantei",
            "${result.plant.commonName}\nParte analizată: ${result.analyzedPart}\nCertitudine: ${result.plant.certainty.label}\nStatus: ${result.evidenceStatus}"))
        root.addView(card(activity, "👁 Observații",
            result.observations.ifEmpty { listOf("Nu există observații suficiente.") }.joinToString("\n• ", "• ")))
        root.addView(card(activity, "🔬 Diagnostic fitosanitar",
            "${result.assessmentCategory}\n${result.assessment}\nCertitudine: ${result.assessmentCertainty.label}"))
        if (result.differentialDiagnosis.isNotEmpty()) {
            root.addView(card(activity, "Alte cauze posibile",
                result.differentialDiagnosis.joinToString("\n\n") {
                    "${it.name} — ${it.certainty.label}\nDe verificat: ${it.verification}"
                }))
        }
        if (result.verificationNeeded.isNotEmpty() || result.additionalPhotosNeeded.isNotEmpty()) {
            val lines = result.verificationNeeded + result.additionalPhotosNeeded.map { "Fotografie necesară: $it" }
            root.addView(card(activity, "Date suplimentare / verificare", lines.joinToString("\n• ", "• ")))
        }
        root.addView(card(activity, "Următorul pas", result.actionLevel))
        root.addView(card(activity, "🌱 Recomandare agronomică",
            result.agronomicRecommendation.ifEmpty {
                listOf("Nu se recomandă o intervenție specifică fără date suficiente.")
            }.joinToString("\n• ", "• ")))
        if (result.safetyNote.isNotBlank()) {
            root.addView(card(activity, "⚠️ Siguranță", result.safetyNote))
        }

        val actions = listOf(
            "📷 ADAUGĂ FOTO / REANALIZEAZĂ",
            "🧪 TRATAMENTE",
            "📓 SALVEAZĂ ÎN JURNAL",
            "📈 MONITORIZEAZĂ"
        )
        actions.forEach { label ->
            root.addView(Button(activity).apply {
                text = label
                isEnabled = true
                setOnClickListener {
                    when {
                        label.startsWith("📷") -> onBack()
                        label.startsWith("🧪") -> TreatmentScreen.show(
                            activity,
                            result.plant.commonName,
                            result.assessment
                        ) {
                            ResultScreen.show(activity, result, onBack)
                        }
                        label.startsWith("📓") -> {
                            JournalRepository(activity).saveCase(
                                JournalCase(
                                    crop = result.plant.commonName,
                                    scientificName = result.plant.scientificName,
                                    assessment = result.assessment,
                                    certainty = result.assessmentCertainty.label,
                                    actionLevel = result.actionLevel,
                                    recommendation = result.agronomicRecommendation
                                )
                            )
                            JournalScreen.show(activity) {
                                ResultScreen.show(activity, result, onBack)
                            }
                        }
                        label.startsWith("📈") -> {
                            val c = JournalCase(
                                crop = result.plant.commonName,
                                scientificName = result.plant.scientificName,
                                assessment = result.assessment,
                                certainty = result.assessmentCertainty.label,
                                actionLevel = result.actionLevel,
                                recommendation = result.agronomicRecommendation
                            )
                            JournalRepository(activity).saveCase(c)
                            MonitoringScreen.show(activity, c) {
                                ResultScreen.show(activity, result, onBack)
                            }
                        }
                    }
                }
            }, margins())
        }
        root.addView(TextView(activity).apply {
            text = "🤖 Analiză AI   ✓ Informație verificată   👤 Furnizat de utilizator   🧮 Calculat"
            textSize = 12f
            setTextColor(Color.DKGRAY)
            setPadding(0, 24, 0, 12)
        })
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }

    private fun title(a: AppCompatActivity, textValue: String) = TextView(a).apply {
        text = textValue; textSize = 28f
        setTextColor(Color.rgb(46,125,50)); gravity = Gravity.CENTER
        setPadding(0,0,0,20)
    }

    private fun card(a: AppCompatActivity, heading: String, body: String) =
        LinearLayout(a).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28,24,28,24)
            setBackgroundColor(Color.WHITE)
            addView(TextView(a).apply {
                text = heading; textSize = 18f
                setTextColor(Color.rgb(38,50,56))
            })
            addView(TextView(a).apply {
                text = body; textSize = 16f
                setTextColor(Color.rgb(96,125,139))
                setPadding(0,10,0,0)
            })
        }

    private fun margins() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { setMargins(0,10,0,10) }
}
