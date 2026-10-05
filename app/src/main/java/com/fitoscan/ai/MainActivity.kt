package com.fitoscan.ai

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "FitoScan-AI"
            textSize = 28f
            gravity = android.view.Gravity.CENTER
        }

        setContentView(textView)
    }
}
