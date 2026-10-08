package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object TranslatorScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Traducător")
  FitoUi.card(a,r,"🌐 Traducere bidirecțională","Selectează limbile celor doi interlocutori. Traducerea vocală va necesita conectarea serviciului de recunoaștere și traducere.")
  val languages=listOf("Română","English","Français","Deutsch","Italiano","Español","Русский")
  val from=Spinner(a).apply{adapter=ArrayAdapter(a,android.R.layout.simple_spinner_dropdown_item,languages)}
  val to=Spinner(a).apply{adapter=ArrayAdapter(a,android.R.layout.simple_spinner_dropdown_item,languages);setSelection(1)}
  r.addView(from);r.addView(to)
  FitoUi.button(a,r,"⇄ Inversează limbile"){val x=from.selectedItemPosition;from.setSelection(to.selectedItemPosition);to.setSelection(x)}
  FitoUi.button(a,r,"Începe conversația",false)
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
