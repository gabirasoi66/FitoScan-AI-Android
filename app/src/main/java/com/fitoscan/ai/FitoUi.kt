package com.fitoscan.ai

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

object FitoUi {
    private val green = Color.rgb(46,125,50)
    private val ink = Color.rgb(38,50,56)
    private val muted = Color.rgb(96,125,139)
    private val bg = Color.rgb(247,249,246)
    fun dp(a:AppCompatActivity, n:Int) = (n*a.resources.displayMetrics.density+0.5f).toInt()
    fun rounded(color:Int, radius:Float=14f):GradientDrawable = GradientDrawable().apply {setColor(color);cornerRadius=radius}
    fun page(a:AppCompatActivity, title:String):LinearLayout {
        a.window.statusBarColor=bg
        a.window.navigationBarColor=bg
        @Suppress("DEPRECATION")
        a.window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        val r=LinearLayout(a).apply {orientation=LinearLayout.VERTICAL;setBackgroundColor(bg);setPadding(dp(a,16),dp(a,16),dp(a,16),dp(a,28))}
        // Android 15 edge-to-edge: keep content clear of the system bars.
        if(Build.VERSION.SDK_INT>=30){
            r.setOnApplyWindowInsetsListener { v, ins ->
                val sys=ins.getInsets(android.view.WindowInsets.Type.statusBars() or android.view.WindowInsets.Type.navigationBars())
                v.setPadding(dp(a,16),sys.top+dp(a,16),dp(a,16),sys.bottom+dp(a,28))
                ins
            }
        }
        r.addView(TextView(a).apply {text=title;textSize=22f;setTextColor(green);setTypeface(null,Typeface.BOLD);setPadding(0,0,0,dp(a,12))})
        return r
    }
    fun text(a:AppCompatActivity, content:String):TextView=TextView(a).apply {text=content;textSize=14f;setTextColor(ink);setLineSpacing(dp(a,2).toFloat(),1f)}
    fun card(a:AppCompatActivity, parent:LinearLayout, title:String, body:String){
        val c=LinearLayout(a).apply {orientation=LinearLayout.VERTICAL;setPadding(dp(a,14),dp(a,12),dp(a,14),dp(a,12));background=rounded(Color.WHITE,dp(a,14).toFloat())}
        c.addView(TextView(a).apply {text=title;textSize=16f;setTextColor(ink);setTypeface(null,Typeface.BOLD)})
        c.addView(text(a,body).apply {setPadding(0,dp(a,6),0,0)})
        parent.addView(c,LinearLayout.LayoutParams(-1,-2).apply{bottomMargin=dp(a,10)})
    }
    fun button(a:AppCompatActivity,parent:LinearLayout,label:String,enabled:Boolean=true,action:(()->Unit)?=null){
        val b=Button(a).apply {text=label;textSize=14f;isAllCaps=false;isEnabled=enabled;setTextColor(if(enabled) Color.WHITE else muted);background=rounded(if(enabled) green else Color.rgb(227,232,228),dp(a,12).toFloat());setOnClickListener{action?.invoke()}}
        parent.addView(b,LinearLayout.LayoutParams(-1,dp(a,48)).apply{bottomMargin=dp(a,9)})
    }
    fun show(a:AppCompatActivity,content:LinearLayout){a.setContentView(ScrollView(a).apply{isFillViewport=true;addView(content)})}
}
