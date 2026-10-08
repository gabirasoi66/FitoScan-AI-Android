package com.fitoscan.ai
import android.graphics.Color
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object SafetyLocationScreen{
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  r.addView(TextView(a).apply{text="🆘 Siguranță • Localizare • SOS";textSize=25f;setTextColor(Color.rgb(198,40,40))})
  r.addView(TextView(a).apply{text="Coordonatele și precizia GPS apar numai dintr-o poziție reală și cu permisiune. FitoScan nu inventează coordonate.\n\nHărți: parcele și puncte sigure salvate.\n\nSOS: apelarea serviciilor de urgență și transmiterea locației necesită confirmare explicită.";textSize=16f;setPadding(0,18,0,18)})
  listOf("📍 LOCAȚIA MEA","🆘 SUNĂ 112","📤 TRIMITE LOCAȚIA","🧭 NAVIGHEAZĂ LA PUNCT SIGUR").forEach{r.addView(Button(a).apply{text=it;isEnabled=false})}
  r.addView(TextView(a).apply{text="FitoScan–AI nu înlocuiește serviciile de urgență.";textSize=13f})
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}