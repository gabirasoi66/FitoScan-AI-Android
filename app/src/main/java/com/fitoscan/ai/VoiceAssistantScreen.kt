package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object VoiceAssistantScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Asistent vocal")
  FitoUi.card(a,r,"🎙 Comenzi vocale","Navigare și întrebări despre scanare, jurnal, meteo, tratamente și AI Agronom.")
  FitoUi.card(a,r,"🔒 Siguranță","Asistentul nu confirmă diagnostice sau doze neverificate.")
  FitoUi.button(a,r,"Pornește ascultarea",false)
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
