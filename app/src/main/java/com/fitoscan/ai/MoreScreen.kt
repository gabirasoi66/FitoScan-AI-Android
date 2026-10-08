package com.fitoscan.ai
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object MoreScreen {
 fun show(a:AppCompatActivity,onJournal:()->Unit,onBack:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(28,28,28,44);setBackgroundColor(Color.rgb(247,249,246))}
  r.addView(TextView(a).apply{text="Mai multe";textSize=28f;gravity=Gravity.CENTER;setTextColor(Color.rgb(46,125,50))})
  val g=linkedMapOf("Culturi & Protecție" to listOf("Culturi","Tratamente","Ghid agronomic","Boli, dăunători & insecte benefice"),"Fermă & Management" to listOf("Ferme & Parcelele mele","Jurnal","Calendar","Rapoarte"),"Lucrări & Tehnică" to listOf("Echipamente & Utilaje","Calcule agricole","Irigații, Apă & Energie"),"Teren & Mediu" to listOf("Meteo","Avertizări","Hărți & Localizare"),"Comunicare" to listOf("Asistent vocal","Traducător"),"Siguranță" to listOf("SOS & Localizare","EIP & Siguranță"),"Aplicație" to listOf("Setări","Surse & verificare","Ajutor","Despre FitoScan-AI"))
  g.forEach{(h,items)->r.addView(TextView(a).apply{text=h;textSize=18f;setPadding(4,20,4,7)});items.forEach{item->r.addView(Button(a).apply{text=item;setOnClickListener{when(item){"Jurnal"->onJournal();"Meteo","Avertizări"->WeatherAlertsScreen.show(a){show(a,onJournal,onBack)};"Echipamente & Utilaje"->EquipmentScreen.show(a){show(a,onJournal,onBack)};"Irigații, Apă & Energie"->IrrigationScreen.show(a){show(a,onJournal,onBack)};"Asistent vocal"->VoiceAssistantScreen.show(a){show(a,onJournal,onBack)};"Traducător"->TranslatorScreen.show(a){show(a,onJournal,onBack)};"Hărți & Localizare","SOS & Localizare"->SafetyLocationScreen.show(a){show(a,onJournal,onBack)};else->Toast.makeText(a,"Modul planificat: $item",Toast.LENGTH_SHORT).show()}}},LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))}}
  r.addView(Button(a).apply{text="ÎNAPOI LA ACASĂ";setOnClickListener{onBack()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}