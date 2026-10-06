package com.fitoscan.ai

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

object SearchScreen {
    fun show(activity: AppCompatActivity, onScan:()->Unit, onBack:()->Unit) {
        val root=LinearLayout(activity).apply {
            orientation=LinearLayout.VERTICAL; setPadding(28,28,28,40)
            setBackgroundColor(Color.rgb(247,249,246))
        }
        root.addView(TextView(activity).apply {
            text="Căutare"; textSize=27f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(46,125,50))
        })
        val input=EditText(activity).apply {
            hint="Caută în FitoScan…"; textSize=17f; isSingleLine=true
        }
        val results=LinearLayout(activity).apply { orientation=LinearLayout.VERTICAL }
        val search=Button(activity).apply {
            text="CAUTĂ"
            setOnClickListener {
                results.removeAllViews()
                val found=UniversalSearch.search(input.text.toString(),JournalRepository(activity).loadCases())
                if(found.isEmpty()) results.addView(TextView(activity).apply {
                    text="Nu au fost găsite rezultate."; setPadding(10,20,10,20)
                })
                found.groupBy { it.category }.forEach { (cat,items) ->
                    results.addView(TextView(activity).apply {
                        text=cat.label; textSize=18f; setTextColor(Color.rgb(25,118,210)); setPadding(0,20,0,8)
                    })
                    items.forEach { item ->
                        results.addView(Button(activity).apply {
                            text="${item.title}\n${item.subtitle}"
                            setOnClickListener {
                                if(item.action=="SCAN") onScan()
                                else Toast.makeText(activity,"Modul pregătit: ${item.category.label}",Toast.LENGTH_SHORT).show()
                            }
                        }, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
                    }
                }
            }
        }
        root.addView(input); root.addView(search); root.addView(results)
        root.addView(Button(activity).apply { text="ÎNAPOI"; setOnClickListener { onBack() } })
        activity.setContentView(ScrollView(activity).apply { addView(root) })
    }
}
