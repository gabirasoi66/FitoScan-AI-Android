package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object EquipmentScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Echipamente & Utilaje")
  FitoUi.card(a,r,"🔎 Alegerea echipamentului","Selecție după cultură, lucrare, suprafață și condițiile de lucru.")
  FitoUi.card(a,r,"🧰 Categorii","Unelte, pulverizare, fertilizare, irigații, utilaje, recoltare, măsurare și EIP.")
  FitoUi.card(a,r,"⚙️ Fișa echipamentului","Alegere, calibrare, utilizare, curățare, întreținere și siguranță.")
  FitoUi.card(a,r,"🧪 Protecția plantelor","Duzele, presiunea și debitul depind de echipamentul utilizat.")
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
