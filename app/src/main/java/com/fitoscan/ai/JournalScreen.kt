package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.util.Date

object JournalScreen {
    fun show(activity: AppCompatActivity, onBack: () -> Unit) {
        val repo = JournalRepository(activity)
        val root = LinearLayout(activity).apply {
            orientation=LinearLayout.VERTICAL; setPadding(32,32,32,48)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(TextView(activity).apply {
            text="Jurnal FitoScan"; textSize=28f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(46,125,50)); setPadding(0,0,0,18)
        })
        val cases=repo.loadCases()
        if(cases.isEmpty()) root.addView(TextView(activity).apply {
            text="Nu există încă niciun caz salvat."; textSize=17f; setTextColor(Color.DKGRAY)
            setPadding(12,24,12,24)
        })
        cases.forEach { c ->
            root.addView(LinearLayout(activity).apply {
                orientation=LinearLayout.VERTICAL; setPadding(24,20,24,20); setBackgroundColor(Color.WHITE)
                addView(TextView(activity).apply { text=c.crop; textSize=19f; setTextColor(Color.rgb(38,50,56)) })
                addView(TextView(activity).apply {
                    text="${c.assessment}\nCertitudine: ${c.certainty}\n${DateFormat.getDateTimeInstance().format(Date(c.createdAt))}"
                    textSize=15f; setTextColor(Color.rgb(96,125,139))
                })
            }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                setMargins(0,8,0,8)
            })
        }
        root.addView(Button(activity).apply { text="ÎNAPOI"; setOnClickListener { onBack() } })
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }
}
