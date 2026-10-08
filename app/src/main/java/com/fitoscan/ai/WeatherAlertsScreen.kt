package com.fitoscan.ai
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object WeatherAlertsScreen {
 fun show(a:AppCompatActivity, back:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  fun card(h:String,b:String)=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(24,20,24,20);setBackgroundColor(Color.WHITE);addView(TextView(a).apply{text=h;textSize=18f});addView(TextView(a).apply{text=b;textSize=15f;setPadding(0,8,0,0)})}
  r.addView(TextView(a).apply{text="Meteo & Avertizări";textSize=28f;gravity=Gravity.CENTER;setTextColor(Color.rgb(46,125,50))})
  r.addView(card("🌦 Condiții meteo","Date live numai după conectarea unei surse reale; FitoScan nu simulează temperatura, ploaia sau vântul."))
  r.addView(card("🌱 Context agronomic","Cultură → parcelă → fenofază → condiții → risc → monitorizare."))
  r.addView(card("🚜 Fereastră de lucru","FAVORABIL • ATENȚIE • NEFAVORABIL, cu justificare."))
  r.addView(card("⚠️ Avertizări","AVERTIZARE OFICIALĂ și ALERTĂ FITOSCAN–AI rămân categorii separate."))
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}