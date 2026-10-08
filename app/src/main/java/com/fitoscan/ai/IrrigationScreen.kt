package com.fitoscan.ai
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object IrrigationScreen {
 fun show(a:AppCompatActivity, back:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  fun card(h:String,b:String)=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(24,20,24,20);setBackgroundColor(Color.WHITE);addView(TextView(a).apply{text=h;textSize=18f});addView(TextView(a).apply{text=b;textSize=15f;setPadding(0,8,0,0)})}
  r.addView(TextView(a).apply{text="Irigații, Apă & Energie";textSize=28f;gravity=Gravity.CENTER;setTextColor(Color.rgb(46,125,50))})
  r.addView(card("💧 Proiectează sistemul meu","Cultură → suprafață → sursă apă → debit → irigare → pompă → filtrare → conducte → automatizare → energie."))
  r.addView(card("🌱 Sisteme","Picurare • aspersie • microaspersie, alese după cultură, sol, relief și sursa de apă."))
  r.addView(card("⚡ Energie","Rețea • generator • fotovoltaic/baterii. Dimensionarea finală necesită date tehnice reale."))
  r.addView(card("🧮 Calcule","Debite, necesar de apă și timpi de udare vor fi marcate drept valori calculate."))
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}