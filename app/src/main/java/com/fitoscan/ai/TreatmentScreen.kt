package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

object TreatmentScreen {
    fun show(activity: AppCompatActivity, crop: String, problem: String, onBack: () -> Unit) {
        val root = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32,32,32,48)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(text(activity, "Tratamente", 28f, Color.rgb(46,125,50), true))
        root.addView(card(activity, "Cultura", crop.ifBlank { "Necunoscută" }))
        root.addView(card(activity, "Problema selectată", problem.ifBlank { "Date insuficiente" }))
        root.addView(card(activity, "Combatere integrată",
            "Prevenție → măsuri culturale → mecanice → biologice → opțiuni chimice, numai când sunt justificate."))
        root.addView(card(activity, "Verificarea produselor",
            "Produsele comerciale și dozele apar numai după verificarea autorizării pentru cultură și țintă."))
        root.addView(card(activity, "Calculator soluție",
            "Volume pregătite: 5 L • 10 L • 15 L • 20 L • 50 L • 100 L • volum personalizat.\n\n" +
            "Calculul este activ numai când există o doză verificată. Dozele pe suprafață necesită și volumul de apă/ha."))
        listOf("🔎 CAUTĂ TRATAMENTE VERIFICATE","🧮 CALCULATOR SOLUȚIE","🧰 ECHIPAMENT & EIP","🌦 VERIFICĂ METEO").forEach {
            root.addView(Button(activity).apply { text=it; isEnabled=false }, margins())
        }
        root.addView(Button(activity).apply { text="ÎNAPOI LA REZULTAT"; setOnClickListener { onBack() } }, margins())
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }

    private fun card(a: AppCompatActivity, h:String, b:String)=LinearLayout(a).apply {
        orientation=LinearLayout.VERTICAL; setPadding(26,22,26,22); setBackgroundColor(Color.WHITE)
        addView(text(a,h,18f,Color.rgb(38,50,56),false))
        addView(text(a,b,15f,Color.rgb(96,125,139),false).apply { setPadding(0,8,0,0) })
    }
    private fun text(a:AppCompatActivity,v:String,size:Float,color:Int,center:Boolean)=TextView(a).apply {
        text=v; textSize=size; setTextColor(color); if(center) gravity=Gravity.CENTER
    }
    private fun margins()=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        setMargins(0,10,0,10)
    }
}
