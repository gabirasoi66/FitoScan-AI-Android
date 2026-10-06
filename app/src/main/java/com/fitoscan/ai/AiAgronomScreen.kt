package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

object AiAgronomScreen {
    fun show(activity: AppCompatActivity, onScan:()->Unit, onBack:()->Unit) {
        val root=LinearLayout(activity).apply {
            orientation=LinearLayout.VERTICAL; setPadding(30,30,30,44)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(TextView(activity).apply {
            text="✨ AI Agronom"; textSize=28f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(46,125,50))
        })
        root.addView(TextView(activity).apply {
            text="Asistent agronomic contextual"; textSize=16f; gravity=Gravity.CENTER
            setTextColor(Color.DKGRAY); setPadding(0,4,0,20)
        })

        val input=EditText(activity).apply {
            hint="Ex.: Ce poate cauza petele de pe frunze?"
            minLines=3; gravity=Gravity.TOP; setPadding(20,18,20,18)
        }
        root.addView(input, margins())

        root.addView(Button(activity).apply {
            text="ÎNTREABĂ AI AGRONOM"
            setOnClickListener {
                val q=input.text.toString().trim()
                if(q.isBlank()) Toast.makeText(activity,"Introduceți o întrebare.",Toast.LENGTH_SHORT).show()
                else Toast.makeText(activity,"Conversația AI va fi activată după conectarea contractului backend.",Toast.LENGTH_LONG).show()
            }
        }, margins())

        root.addView(card(activity,"Ce poate folosi AI Agronom",
            "Rezultatul scanării • Jurnalul cazului • Monitorizarea • cultura/parcela confirmată • meteo și avertizări când sunt disponibile."))
        root.addView(card(activity,"Regulă de siguranță",
            "Nu inventează produse, doze, autorizări sau certitudini. Pentru simptome vizuale poate solicita fotografii suplimentare."))

        root.addView(Button(activity).apply {
            text="📷 SCANEAZĂ PLANTA"; setOnClickListener { onScan() }
        }, margins())
        root.addView(Button(activity).apply { text="ÎNAPOI"; setOnClickListener { onBack() } }, margins())
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }

    private fun card(a:AppCompatActivity,h:String,b:String)=LinearLayout(a).apply {
        orientation=LinearLayout.VERTICAL; setPadding(24,20,24,20); setBackgroundColor(Color.WHITE)
        addView(TextView(a).apply { text=h; textSize=18f; setTextColor(Color.rgb(38,50,56)) })
        addView(TextView(a).apply { text=b; textSize=15f; setTextColor(Color.rgb(96,125,139)); setPadding(0,8,0,0) })
    }
    private fun margins()=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        setMargins(0,9,0,9)
    }
}
