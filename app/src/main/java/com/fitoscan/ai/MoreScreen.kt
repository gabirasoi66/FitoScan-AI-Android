package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

object MoreScreen {
    fun show(activity: AppCompatActivity, onJournal:()->Unit, onBack:()->Unit) {
        val root=LinearLayout(activity).apply {
            orientation=LinearLayout.VERTICAL; setPadding(28,28,28,44)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(TextView(activity).apply {
            text="Mai multe"; textSize=28f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(46,125,50))
        })

        val groups=linkedMapOf(
            "Culturi & Protecție" to listOf("Culturi","Tratamente","Ghid agronomic","Boli, dăunători & insecte benefice"),
            "Fermă & Management" to listOf("Ferme & Parcelele mele","Jurnal","Calendar","Rapoarte"),
            "Lucrări & Tehnică" to listOf("Echipamente & Utilaje","Calcule agricole","Irigații, Apă & Energie"),
            "Teren & Mediu" to listOf("Meteo","Avertizări","Hărți & Localizare"),
            "Comunicare" to listOf("Asistent vocal","Traducător"),
            "Siguranță" to listOf("SOS & Localizare","EIP & Siguranță"),
            "Aplicație" to listOf("Setări","Surse & verificare","Ajutor","Despre FitoScan-AI")
        )
        groups.forEach { (group,items) ->
            root.addView(TextView(activity).apply {
                text=group; textSize=18f; setTextColor(Color.rgb(22,50,79)); setPadding(4,20,4,7)
            })
            items.forEach { item ->
                root.addView(Button(activity).apply {
                    text=item
                    setOnClickListener {
                        if(item=="Jurnal") onJournal()
                        else Toast.makeText(activity,"Modul planificat: $item",Toast.LENGTH_SHORT).show()
                    }
                }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0,4,0,4)
                })
            }
        }
        root.addView(Button(activity).apply { text="ÎNAPOI LA ACASĂ"; setOnClickListener { onBack() } })
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }
}
