package com.fitoscan.ai
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
object SafetyLocationScreen {
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=FitoUi.page(a,"Siguranță • Localizare • SOS")
  FitoUi.card(a,r,"📍 Locație reală","Coordonatele se afișează numai cu permisiune și după obținerea unei poziții GPS reale.")
  FitoUi.card(a,r,"🗺 Hărți și puncte sigure","Parcelele și punctele salvate vor putea fi folosite pentru navigare.")
  FitoUi.card(a,r,"🆘 Situații de urgență","Apelarea și distribuirea locației vor necesita confirmare explicită. Aplicația nu înlocuiește serviciile de urgență.")
  FitoUi.button(a,r,"Locația mea",false)
  FitoUi.button(a,r,"Sună 112",false)
  FitoUi.button(a,r,"Trimite locația",false)
  FitoUi.button(a,r,"Navighează la punct sigur",false)
  FitoUi.button(a,r,"← Înapoi"){back()}
  FitoUi.show(a,r)
 }
}
