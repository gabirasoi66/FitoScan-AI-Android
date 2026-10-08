package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object MoreScreen {
 fun show(a:AppCompatActivity,onJournal:()->Unit,onBack:()->Unit){
  val r=FitoUi.page(a,"Mai multe")
  val groups=linkedMapOf(
   "Culturi & Protecție" to listOf("Culturi","Tratamente","Ghid agronomic","Boli, dăunători & insecte benefice"),
   "Fermă & Management" to listOf("Ferme & Parcelele mele","Jurnal","Calendar","Rapoarte"),
   "Lucrări & Tehnică" to listOf("Echipamente & Utilaje","Calcule agricole","Irigații, Apă & Energie"),
   "Teren & Mediu" to listOf("Meteo","Avertizări","Hărți & Localizare"),
   "Comunicare" to listOf("Asistent vocal","Traducător"),
   "Siguranță" to listOf("SOS & Localizare","EIP & Siguranță"),
   "Aplicație" to listOf("Setări","Surse & verificare","Ajutor","Despre FitoScan-AI")
  )
  groups.forEach{(heading,items)->
   FitoUi.card(a,r,heading,items.joinToString(" • "))
   items.forEach{item->FitoUi.button(a,r,item){
    val back={show(a,onJournal,onBack)}
    when(item){
     "Jurnal"->onJournal()
     "Meteo","Avertizări"->WeatherAlertsScreen.show(a,back)
     "Echipamente & Utilaje"->EquipmentScreen.show(a,back)
     "Irigații, Apă & Energie"->IrrigationScreen.show(a,back)
     "Asistent vocal"->VoiceAssistantScreen.show(a,back)
     "Traducător"->TranslatorScreen.show(a,back)
     "Hărți & Localizare","SOS & Localizare"->SafetyLocationScreen.show(a,back)
     else->Toast.makeText(a,"Modul planificat: $item",Toast.LENGTH_SHORT).show()
    }
   }}
  }
  FitoUi.button(a,r,"← Acasă"){onBack()}
  FitoUi.show(a,r)
 }
}
