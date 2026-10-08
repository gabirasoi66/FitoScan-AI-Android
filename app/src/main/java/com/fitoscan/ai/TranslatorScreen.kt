package com.fitoscan.ai
import android.graphics.Color
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object TranslatorScreen{
 fun show(a:AppCompatActivity,back:()->Unit){
  val langs=listOf("Română","English","Français","Deutsch","Italiano","Español","Русский")
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  r.addView(TextView(a).apply{text="🌐 Traducător";textSize=28f;setTextColor(Color.rgb(46,125,50))})
  r.addView(TextView(a).apply{text="Conversație bidirecțională pentru lucrători agricoli. Limba interfeței și limbile conversației sunt independente.";textSize=16f})
  r.addView(Spinner(a).apply{adapter=ArrayAdapter(a,android.R.layout.simple_spinner_dropdown_item,langs)})
  r.addView(Spinner(a).apply{adapter=ArrayAdapter(a,android.R.layout.simple_spinner_dropdown_item,langs);setSelection(1)})
  r.addView(Button(a).apply{text="🎙 ÎNCEPE CONVERSAȚIA";isEnabled=false})
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}