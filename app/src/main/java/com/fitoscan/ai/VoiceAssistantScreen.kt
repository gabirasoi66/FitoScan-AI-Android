package com.fitoscan.ai
import android.graphics.Color
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
object VoiceAssistantScreen{
 fun show(a:AppCompatActivity,back:()->Unit){
  val r=LinearLayout(a).apply{orientation=LinearLayout.VERTICAL;setPadding(30,30,30,48);setBackgroundColor(Color.rgb(247,249,246))}
  r.addView(TextView(a).apply{text="🎙 Asistent vocal";textSize=28f;setTextColor(Color.rgb(46,125,50))})
  r.addView(TextView(a).apply{text="Comenzi vocale pentru scanare, jurnal, meteo, tratamente, căutare și AI Agronom. Aceleași reguli de siguranță ca în interfața text.";textSize=16f;setPadding(0,18,0,18)})
  r.addView(Button(a).apply{text="🎙 PORNEȘTE ASCULTAREA";isEnabled=false})
  r.addView(Button(a).apply{text="ÎNAPOI";setOnClickListener{back()}})
  a.setContentView(ScrollView(a).apply{addView(r)})
 }
}