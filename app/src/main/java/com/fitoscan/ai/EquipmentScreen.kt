package com.fitoscan.ai
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object EquipmentScreen {
 fun show(a:AppCompatActivity, back:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  fun card(h:String,b:String)=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(24,20,24,20);setBackgroundColor(Color.WHITE);addView(TextView(a).apply{text=h;textSize=18f});addView(TextView(a).apply{text=b;textSize=15f;setPadding(0,8,0,0)})}
  r.addView(TextView(a).apply{text="Echipamente, Scule & Utilaje";textSize=27f;gravity=Gravity.CENTER;setTextColor(Color.rgb(46,125,50))})
  r.addView(card("🔎 Ce echipament îmi trebuie?","Selecție după cultură, lucrare, suprafață și condițiile de lucru."))
  r.addView(card("Categorii","Unelte • pulverizare/atomizare • fertilizare • irigații • utilaje • recoltare • măsurare • EIP."))
  r.addView(card("⚙️ Fișa echipamentului","Alegere • calibrare • utilizare • curățare • întreținere • depozitare • siguranță."))
  r.addView(card("🧪 Aplicare fitosanitară","Duzele, debitul și presiunea se stabilesc pentru echipamentul concret; fără setări universale."))
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}