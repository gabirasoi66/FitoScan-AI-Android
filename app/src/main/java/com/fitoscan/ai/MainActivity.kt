package com.fitoscan.ai

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var previewImage: ImageView
    private lateinit var analyzeButton: Button

    companion object {
        private const val CAMERA_REQUEST = 100
        private const val GALLERY_REQUEST = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHomeScreen()
    }

    private fun showHomeScreen() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 35, 48, 48)
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
            setPadding(0, 20, 0, 40)
        }

        val scanButton = Button(this).apply {
            text = "SCANEAZĂ PLANTA"
            textSize = 18f

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Fotografiază planta sau selectează o imagine",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val cameraButton = Button(this).apply {
            text = "CAMERĂ"

            setOnClickListener {
                openCamera()
            }
        }

        val galleryButton = Button(this).apply {
            text = "GALERIE"

            setOnClickListener {
                openGallery()
            }
        }

        previewImage = ImageView(this).apply {
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_CROP
            visibility = View.GONE
            setPadding(0, 20, 0, 20)
        }

        analyzeButton = Button(this).apply {
            text = "ANALIZEAZĂ PLANTA"
            textSize = 17f
            visibility = View.GONE

            setOnClickListener {
                showAnalysisResult()
            }
        }

        val info = TextView(this).apply {
            text = "Fotografiază frunza, planta, fructul sau simptomul observat."
            textSize = 15f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 30, 0, 20)
        }

        val buttonParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 12, 0, 12)
        }

        val imageParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            600
        )

        root.addView(title)
        root.addView(subtitle)
        root.addView(scanButton, buttonParams)
        root.addView(cameraButton, buttonParams)
        root.addView(galleryButton, buttonParams)
        root.addView(previewImage, imageParams)
        root.addView(analyzeButton, buttonParams)
        root.addView(info)

        val scrollView = ScrollView(this).apply {
            addView(root)
        }

        setContentView(scrollView)
    }

    private fun openCamera() {

        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "Camera nu poate fi deschisă.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openGallery() {

        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        galleryIntent.type = "image/*"

        startActivityForResult(
            galleryIntent,
            GALLERY_REQUEST
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {

            CAMERA_REQUEST -> {

                val bitmap =
                    data?.extras?.get("data") as? Bitmap

                if (bitmap != null) {

                    previewImage.setImageBitmap(bitmap)
                    previewImage.visibility = View.VISIBLE
                    analyzeButton.visibility = View.VISIBLE
                }
            }

            GALLERY_REQUEST -> {

                val imageUri = data?.data

                if (imageUri != null) {

                    previewImage.setImageURI(imageUri)
                    previewImage.visibility = View.VISIBLE
                    analyzeButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showAnalysisResult() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 50, 48, 50)
            setBackgroundColor(Color.rgb(247, 250, 247))
        }

        val title = TextView(this).apply {
            text = "Rezultat analiză"
            textSize = 28f
            setTextColor(Color.rgb(25, 90, 55))
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 35)
        }

        val status = TextView(this).apply {
            text = "✓ Imagine procesată"
            textSize = 20f
            setTextColor(Color.rgb(25, 120, 65))
            setPadding(0, 15, 0, 25)
        }

        val result = TextView(this).apply {
            text = """
                ANALIZĂ FITOSANITARĂ – MOD DEMO

                Plantă:
                În curs de identificare

                Simptom observ
