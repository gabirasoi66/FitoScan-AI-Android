
package com.fitoscan.ai

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 80, 48, 48)
            setBackgroundColor(Color.rgb(247, 250, 247))
        }

        val title = TextView(this).apply {
            text = "FitoScan-AI"
            textSize = 32f
            setTextColor(Color.rgb(25, 90, 55))
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Asistentul tău agronomic inteligent"
            textSize = 17f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 60)
        }

        val scanButton = Button(this).apply {
            text = "SCANEAZĂ PLANTA"
            textSize = 18f
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Selectează Camera sau Galeria",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val cameraButton = Button(this).apply {
            text = "CAMERĂ"
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Camera va fi conectată în pasul următor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val galleryButton = Button(this).apply {
            text = "GALERIE"
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Galeria va fi conectată în pasul următor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val info = TextView(this).apply {
            text = "Fotografiază frunza, planta, fructul sau simptomul observat."
            textSize = 15f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 50, 0, 0)
        }

        val buttonParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 12, 0, 12)
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(scanButton, buttonParams)
        root.addView(cameraButton, buttonParams)
        root.addView(galleryButton, buttonParams)
        root.addView(info)

        setContentView(root)
    }
}
