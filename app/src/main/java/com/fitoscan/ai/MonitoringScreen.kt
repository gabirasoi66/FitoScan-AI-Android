package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

object MonitoringScreen {
    fun show(activity: AppCompatActivity, case: JournalCase, onBack: () -> Unit) {
        val root=LinearLayout(activity).apply {
            orientation=LinearLayout.VERTICAL; setPadding(32,32,32,48)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(TextView(activity).apply {
            text="Monitorizare"; textSize=28f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(46,125,50))
        })
        root.addView(TextView(activity).apply {
            text="""
                ${case.crop}
                Diagnostic inițial: ${case.assessment}

                Flux monitorizare:
                fotografie inițială → diagnostic → intervenție → fotografie nouă → reevaluare AI → evoluție

                Stări:
                • Îmbunătățire
                • Stabil
                • Agravare
                • Problemă nouă
                • Date insuficiente

                Reevaluarea nu suprascrie analiza inițială; va fi adăugată în istoricul cazului.
            """.trimIndent()
            textSize=17f; setTextColor(Color.DKGRAY); setPadding(0,24,0,24)
        })
        root.addView(Button(activity).apply {
            text="📷 ADAUGĂ FOTO PENTRU REEVALUARE"; isEnabled=false
        })
        root.addView(Button(activity).apply { text="ÎNAPOI"; setOnClickListener { onBack() } })
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }
}
