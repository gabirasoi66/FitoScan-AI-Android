package com.fitoscan.ai

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val selectedImages = mutableListOf<Uri>()
    private lateinit var photoStrip: LinearLayout
    private lateinit var analyzeButton: Button
    private lateinit var counterText: TextView
    private var pendingCameraUri: Uri? = null

    companion object {
        private const val GALLERY_REQUEST = 101
        private const val CAMERA_REQUEST = 102
        private const val MAX_PHOTOS = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHomeScreen()
    }

    private fun showHomeScreen() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 28, 32, 40)
            setBackgroundColor(Color.rgb(247, 249, 246))
        }

        root.addView(TextView(this).apply {
            text = "🌿 FitoScan-AI"
            textSize = 30f
            setTextColor(Color.rgb(46, 125, 50))
            gravity = Gravity.CENTER
        })
        root.addView(TextView(this).apply {
            text = "Agricultură inteligentă. Recolte sănătoase."
            textSize = 15f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 4, 0, 18)
        })

        val search = Button(this).apply {
            text = "🔎 Caută în FitoScan…   🎙️"
            setOnClickListener {
                SearchScreen.show(this@MainActivity, { showScanScreen() }, { showHomeScreen() })
            }
        }
        root.addView(search, homeMargins())

        root.addView(TextView(this).apply {
            text = "Plante sănătoase\nRecolte mai bune"
            textSize = 27f
            setTextColor(Color.rgb(38,50,56))
            setPadding(10,18,10,10)
        })
        root.addView(TextView(this).apply {
            text = "Identifică plantele • Diagnostichează problemele • Recomandări agronomice • Meteo și ghiduri"
            textSize = 15f
            setTextColor(Color.DKGRAY)
            setPadding(10,0,10,14)
        })

        root.addView(Button(this).apply {
            text = "📷  SCANEAZĂ PLANTA  ›"
            textSize = 19f
            setOnClickListener { showScanScreen() }
        }, homeMargins())

        root.addView(TextView(this).apply {
            text = "Planul zilei – AI Agronom"
            textSize = 20f
            setTextColor(Color.rgb(22,50,79))
            setPadding(8,18,8,8)
        })
        root.addView(TextView(this).apply {
            text = "Planul zilei va folosi Jurnalul, Monitorizarea, Meteo și Avertizările. Nu sunt generate sarcini fictive."
            textSize = 15f
            setTextColor(Color.DKGRAY)
            setPadding(8,0,8,12)
        })

        val quick = listOf(
            "🌦 Meteo & Avertizări" to false,
            "🧪 Tratamente" to true,
            "📓 Jurnal" to true,
            "📈 Monitorizare" to false,
            "✨ Întreabă AI Agronom" to true
        )
        quick.forEach { (label, enabled) ->
            root.addView(Button(this).apply {
                text = label
                isEnabled = enabled
                setOnClickListener {
                    when(label) {
                        "📓 Jurnal" -> JournalScreen.show(this@MainActivity) { showHomeScreen() }
                        "🧪 Tratamente" -> TreatmentScreen.show(this@MainActivity, "", "") { showHomeScreen() }
                        "✨ Întreabă AI Agronom" -> AiAgronomScreen.show(this@MainActivity,{ showScanScreen() },{ showHomeScreen() })
                    }
                }
            }, homeMargins())
        }

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0,18,0,4)
        }
        val navItems = listOf("Acasă","Scanare","AI Agronom","Jurnal","Mai multe")
        navItems.forEach { item ->
            nav.addView(Button(this).apply {
                text = item
                textSize = 11f
                isEnabled = item != "Acasă"
                setOnClickListener {
                    when(item) {
                        "Scanare" -> showScanScreen()
                        "AI Agronom" -> AiAgronomScreen.show(this@MainActivity,{ showScanScreen() },{ showHomeScreen() })
                        "Jurnal" -> JournalScreen.show(this@MainActivity) { showHomeScreen() }
                        "Mai multe" -> MoreScreen.show(
                            this@MainActivity,
                            { JournalScreen.show(this@MainActivity) { showHomeScreen() } },
                            { showHomeScreen() }
                        )
                    }
                }
            }, LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1f))
        }
        root.addView(nav)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun homeMargins() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { setMargins(0,8,0,8) }

    private fun showScanScreen() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 48)
            setBackgroundColor(Color.rgb(247, 250, 247))
        }

        val title = TextView(this).apply {
            text = "Scanare plantă"
            textSize = 27f
            setTextColor(Color.rgb(46, 125, 50))
            gravity = Gravity.CENTER
        }

        val guide = TextView(this).apply {
            text = "Adăugați până la 4 fotografii: planta întreagă, zona afectată, detaliu și context."
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(0, 18, 0, 20)
        }

        counterText = TextView(this).apply {
            textSize = 16f
            setTextColor(Color.rgb(22, 50, 79))
            setPadding(0, 0, 0, 12)
        }

        photoStrip = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val stripScroll = HorizontalScrollView(this).apply {
            addView(photoStrip)
        }

        val cameraButton = Button(this).apply {
            text = "📷 FOTOGRAFIAZĂ"
            setOnClickListener { openCameraFullResolution() }
        }

        val galleryButton = Button(this).apply {
            text = "🖼 ALEGE DIN GALERIE"
            setOnClickListener { openGallery() }
        }

        analyzeButton = Button(this).apply {
            text = "✨ ANALIZEAZĂ"
            textSize = 17f
            isEnabled = selectedImages.isNotEmpty()
            setOnClickListener { prepareAnalysis() }
        }

        val clearButton = Button(this).apply {
            text = "ȘTERGE TOATE"
            setOnClickListener {
                selectedImages.clear()
                refreshPreview()
            }
        }

        val backButton = Button(this).apply {
            text = "ÎNAPOI"
            setOnClickListener {
                selectedImages.clear()
                showHomeScreen()
            }
        }

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 10, 0, 10) }

        root.addView(title)
        root.addView(guide)
        root.addView(counterText)
        root.addView(stripScroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 430
        ))
        root.addView(cameraButton, params)
        root.addView(galleryButton, params)
        root.addView(analyzeButton, params)
        root.addView(clearButton, params)
        root.addView(backButton, params)

        setContentView(ScrollView(this).apply { addView(root) })
        refreshPreview()
    }



    private fun openCameraFullResolution() {
        if (selectedImages.size >= MAX_PHOTOS) {
            Toast.makeText(this, "Ați selectat deja maximum 4 fotografii.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val photoFile = File.createTempFile("fitoscan_", ".jpg", photoDir)
            val uri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                photoFile
            )
            pendingCameraUri = uri

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivityForResult(intent, CAMERA_REQUEST)
        } catch (e: Exception) {
            pendingCameraUri = null
            Toast.makeText(this, "Camera nu poate fi deschisă.", Toast.LENGTH_LONG).show()
        }
    }

    private fun openGallery() {
        if (selectedImages.size >= MAX_PHOTOS) {
            Toast.makeText(this, "Ați selectat deja maximum 4 fotografii.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) pendingCameraUri = null
            return
        }

        when (requestCode) {
            CAMERA_REQUEST -> {
                pendingCameraUri?.let { uri ->
                    if (selectedImages.size < MAX_PHOTOS) {
                        selectedImages.add(uri)
                        refreshPreview()
                    }
                }
                pendingCameraUri = null
            }

            GALLERY_REQUEST -> {
                val uri = data?.data ?: return
                if (selectedImages.contains(uri)) {
                    Toast.makeText(this, "Această fotografie este deja adăugată.", Toast.LENGTH_SHORT).show()
                    return
                }
                if (selectedImages.size < MAX_PHOTOS) {
                    selectedImages.add(uri)
                    refreshPreview()
                }
            }
        }
    }

    private fun refreshPreview() {
        if (!::photoStrip.isInitialized) return

        photoStrip.removeAllViews()
        counterText.text = "Fotografii selectate: ${selectedImages.size}/$MAX_PHOTOS"

        selectedImages.forEachIndexed { index, uri ->
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(6, 6, 6, 6)
            }

            val image = ImageView(this).apply {
                setImageURI(uri)
                scaleType = ImageView.ScaleType.CENTER_CROP
                adjustViewBounds = false
            }

            val remove = Button(this).apply {
                text = "×"
                textSize = 20f
                setOnClickListener {
                    if (index < selectedImages.size) {
                        selectedImages.removeAt(index)
                        refreshPreview()
                    }
                }
            }

            card.addView(image, LinearLayout.LayoutParams(300, 300))
            card.addView(remove, LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ))
            photoStrip.addView(card)
        }

        analyzeButton.isEnabled = selectedImages.isNotEmpty()
    }

    private fun showAnalysisPlaceholder() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 50, 48, 50)
            setBackgroundColor(Color.rgb(247, 250, 247))
        }

        val title = TextView(this).apply {
            text = "Analiză FitoScan-AI"
            textSize = 28f
            setTextColor(Color.rgb(46, 125, 50))
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = """
                Fotografii pregătite: ${selectedImages.size}

                1. Identificare plantă
                2. Analiză simptome
                3. Cauze posibile
                4. Evaluare rezultat

                Fotografiile sunt pregătite la rezoluție utilă pentru transmiterea către backend-ul AI.
            """.trimIndent()
            textSize = 18f
            setTextColor(Color.DKGRAY)
            setPadding(0, 30, 0, 30)
        }

        val backButton = Button(this).apply {
            text = "ÎNAPOI LA PREVIEW"
            setOnClickListener { showScanScreen() }
        }

        root.addView(title)
        root.addView(status)
        root.addView(backButton)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun prepareAnalysis() {
        if (selectedImages.isEmpty()) return
        analyzeButton.isEnabled = false
        analyzeButton.text = "Se pregătesc fotografiile…"

        Thread {
            val preparedResult = runCatching {
                selectedImages.map { ImagePreprocessor.prepare(contentResolver, it) }
            }
            runOnUiThread {
                preparedResult.onSuccess { prepared ->
                    sendAnalysis(prepared)
                }.onFailure { error ->
                    analyzeButton.isEnabled = true
                    analyzeButton.text = "✨ ANALIZEAZĂ"
                    Toast.makeText(
                        this,
                        "Pregătirea fotografiilor a eșuat: ${error.message ?: "eroare necunoscută"}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }

    private fun sendAnalysis(prepared: List<PreparedImage>) {
        val requestJson = runCatching {
            AnalysisContract.buildRequest(prepared, AnalysisContext())
        }.getOrElse {
            analyzeButton.isEnabled = true
            analyzeButton.text = "✨ ANALIZEAZĂ"
            Toast.makeText(this, it.message ?: "Cerere invalidă.", Toast.LENGTH_LONG).show()
            return
        }

        analyzeButton.text = "Analiză AI în curs…"
        ApiTransport().postJson(requestJson) { networkResult ->
            runOnUiThread {
                analyzeButton.isEnabled = true
                analyzeButton.text = "✨ ANALIZEAZĂ"

                networkResult.onSuccess { raw ->
                    runCatching { AnalysisResponseParser.parse(raw) }
                        .onSuccess { result ->
                            ResultScreen.show(this@MainActivity, result) { showScanScreen() }
                        }
                        .onFailure { error ->
                            Toast.makeText(
                                this,
                                "Răspuns AI invalid: ${error.message ?: "eroare necunoscută"}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }.onFailure { error ->
                    Toast.makeText(
                        this,
                        "Analiza nu a reușit. Fotografiile au rămas în Preview. ${error.message ?: ""}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
