package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object WeatherAlertsScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Meteo & Avertizări")
  FitoUi.card(a,r,"🌦 Condiții meteo","Date live indisponibile până la conectarea unei surse reale. Nu sunt afișate valori simulate.")
  FitoUi.card(a,r,"🌱 Context agronomic","Cultură → parcelă → fenofază → condiții → risc → monitorizare.")
  FitoUi.card(a,r,"🚜 Fereastră de lucru","Favorabil / Atenție / Nefavorabil, numai după evaluarea condițiilor reale.")
  FitoUi.card(a,r,"⚠️ Avertizări","Avertizările oficiale și alertele estimate de FitoScan–AI vor fi marcate separat.")
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
