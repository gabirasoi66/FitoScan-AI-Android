package com.fitoscan.ai

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

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
        private const val GREEN = 0xFF2E7D32.toInt()
        private const val INK = 0xFF263238.toInt()
        private const val MUTED = 0xFF607D8B.toInt()
        private const val BG = 0xFFF7F9F6.toInt()
        private const val BLUE = 0xFF16324F.toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHomeScreen()
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density + 0.5f).toInt()

    private fun shape(color: Int, radius: Int = 14) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
    }

    private fun text(value: String, size: Float = 14f, color: Int = INK, bold: Boolean = false) =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            includeFontPadding = true
        }

    private fun button(value: String, primary: Boolean = false, action: () -> Unit) =
        Button(this).apply {
            text = value
            textSize = if (primary) 16f else 14f
            isAllCaps = false
            minHeight = dp(48)
            minimumHeight = dp(48)
            setPadding(dp(12), dp(8), dp(12), dp(8))
            backgroundTintList = null
            background = shape(if (primary) GREEN else Color.WHITE, 12)
            setTextColor(if (primary) Color.WHITE else GREEN)
            setOnClickListener { action() }
        }

    private fun spacing(bottom: Int = 10) =
        LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(bottom) }

    private fun page(): LinearLayout {
        window.statusBarColor = BG
        window.navigationBarColor = BG
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(BG)
            setPadding(dp(16), dp(12), dp(16), dp(16))
        }
    }

    private fun showPage(content: LinearLayout, footer: View? = null) {
        val shell = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(BG)
        }
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            clipToPadding = false
            addView(content)
        }
        shell.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        if (footer != null) shell.addView(footer)
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            shell.setOnApplyWindowInsetsListener { view, insets ->
                val bars = insets.getInsets(
                    WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
                )
                view.setPadding(0, bars.top, 0, bars.bottom)
                insets
            }
        } else {
            @Suppress("DEPRECATION")
            shell.fitsSystemWindows = true
        }
        setContentView(shell)
        if (android.os.Build.VERSION.SDK_INT >= 30) shell.requestApplyInsets()
    }

    private fun showHomeScreen() {
        val root = page()
        root.addView(text("🌿 FitoScan–AI", 23f, GREEN, true), spacing(3))
        root.addView(text("Agricultură inteligentă. Recolte sănătoase.", 12f, MUTED), spacing(14))

        root.addView(button("⌕  Caută în FitoScan") {
            SearchScreen.show(this, { showScanScreen() }, { showHomeScreen() })
        }, spacing(14))

        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
            background = shape(0xFFE8F5E9.toInt(), 16)
        }
        hero.addView(text("Plante sănătoase.\nRecolte mai bune.", 22f, BLUE, true), spacing(8))
        hero.addView(text("Identificare plante, analiză a simptomelor și asistență agronomică.", 13f, INK), spacing(12))
        hero.addView(button("📷  SCANEAZĂ PLANTA", true) { showScanScreen() })
        root.addView(hero, spacing(18))

        root.addView(text("Planul zilei", 17f, BLUE, true), spacing(6))
        root.addView(text("Recomandările vor apărea aici pe baza datelor reale din jurnal și meteo. Nu sunt afișate sarcini inventate.", 13f, MUTED), spacing(16))

        root.addView(text("Acces rapid", 17f, BLUE, true), spacing(8))
        root.addView(button("🌦  Meteo și avertizări") {
            WeatherAlertsScreen.show(this) { showHomeScreen() }
        }, spacing(8))
        root.addView(button("🧪  Tratamente") {
            TreatmentScreen.show(this, "", "") { showHomeScreen() }
        }, spacing(8))
        root.addView(button("📓  Jurnal agricol") {
            JournalScreen.show(this) { showHomeScreen() }
        }, spacing(8))
        root.addView(button("📈  Monitorizare (alege un caz din Jurnal)") {
            JournalScreen.show(this) { showHomeScreen() }
        }, spacing(8))
        root.addView(button("✨  Întreabă AI Agronom") {
            AiAgronomScreen.show(this, { showScanScreen() }, { showHomeScreen() })
        }, spacing(8))

        showPage(root, navigation())
    }

    private fun navigation(): View {
        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), dp(5), dp(4), dp(5))
            setBackgroundColor(Color.WHITE)
        }
        val items = listOf("Acasă", "Scanare", "AI Agronom", "Jurnal", "Mai multe")
        items.forEach { item ->
            val active = item == "Acasă"
            nav.addView(TextView(this).apply {
                text = item
                textSize = 10f
                gravity = Gravity.CENTER
                setTextColor(if (active) GREEN else INK)
                setTypeface(null, if (active) Typeface.BOLD else Typeface.NORMAL)
                setPadding(dp(2), dp(10), dp(2), dp(10))
                setOnClickListener {
                    when (item) {
                        "Acasă" -> showHomeScreen()
                        "Scanare" -> showScanScreen()
                        "AI Agronom" -> AiAgronomScreen.show(this@MainActivity, { showScanScreen() }, { showHomeScreen() })
                        "Jurnal" -> JournalScreen.show(this@MainActivity) { showHomeScreen() }
                        "Mai multe" -> MoreScreen.show(
                            this@MainActivity,
                            { JournalScreen.show(this@MainActivity) { showHomeScreen() } },
                            { showHomeScreen() }
                        )
                    }
                }
            }, LinearLayout.LayoutParams(0, dp(48), 1f))
        }
        return nav
    }

    private fun showScanScreen() {
        val root = page()
        root.addView(text("Scanare plantă", 22f, GREEN, true), spacing(8))
        root.addView(text("Adaugă până la 4 fotografii: planta întreagă, zona afectată, detaliu și context.", 14f), spacing(14))

        counterText = text("", 14f, BLUE, true)
        root.addView(counterText, spacing(8))
        photoStrip = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        root.addView(HorizontalScrollView(this).apply {
            addView(photoStrip)
        }, LinearLayout.LayoutParams(-1, dp(190)).apply { bottomMargin = dp(12) })

        root.addView(button("📷  Fotografiază") { openCameraFullResolution() }, spacing(8))
        root.addView(button("🖼  Alege din galerie") { openGallery() }, spacing(8))

        analyzeButton = button("✨  ANALIZEAZĂ PLANTA", true) { prepareAnalysis() }
        root.addView(analyzeButton, spacing(8))
        root.addView(button("Șterge toate fotografiile") {
            selectedImages.clear()
            refreshPreview()
        }, spacing(8))
        root.addView(button("←  Înapoi la Acasă") { showHomeScreen() }, spacing(8))

        showPage(root)
        refreshPreview()
    }

    private fun openCameraFullResolution() {
        if (selectedImages.size >= MAX_PHOTOS) {
            Toast.makeText(this, "Ai selectat deja 4 fotografii.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val file = File.createTempFile("fitoscan_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
            val uri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider", file)
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
            Toast.makeText(this, "Ai selectat deja 4 fotografii.", Toast.LENGTH_SHORT).show()
            return
        }
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" },
            GALLERY_REQUEST
        )
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
                pendingCameraUri?.let { if (selectedImages.size < MAX_PHOTOS) selectedImages.add(it) }
                pendingCameraUri = null
                refreshPreview()
            }
            GALLERY_REQUEST -> {
                val uri = data?.data ?: return
                if (selectedImages.contains(uri)) {
                    Toast.makeText(this, "Această fotografie este deja adăugată.", Toast.LENGTH_SHORT).show()
                } else if (selectedImages.size < MAX_PHOTOS) {
                    selectedImages.add(uri)
                    refreshPreview()
                }
            }
        }
    }

    private fun refreshPreview() {
        if (!::photoStrip.isInitialized || !::counterText.isInitialized || !::analyzeButton.isInitialized) return
        photoStrip.removeAllViews()
        counterText.text = "Fotografii selectate: ${selectedImages.size}/$MAX_PHOTOS"
        selectedImages.forEachIndexed { index, uri ->
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(dp(4), dp(4), dp(4), dp(4))
            }
            card.addView(ImageView(this).apply {
                setImageURI(uri)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }, LinearLayout.LayoutParams(dp(145), dp(125)))
            card.addView(button("✕  Elimină") {
                if (index < selectedImages.size) {
                    selectedImages.removeAt(index)
                    refreshPreview()
                }
            }, LinearLayout.LayoutParams(dp(145), dp(48)))
            photoStrip.addView(card)
        }
        analyzeButton.isEnabled = selectedImages.isNotEmpty()
        analyzeButton.alpha = if (selectedImages.isNotEmpty()) 1f else 0.55f
    }

    private fun prepareAnalysis() {
        if (selectedImages.isEmpty()) return
        analyzeButton.isEnabled = false
        analyzeButton.text = "Se pregătesc fotografiile…"
        val snapshot = selectedImages.toList()
        Thread {
            val result = runCatching {
                snapshot.map { ImagePreprocessor.prepare(contentResolver, it) }
            }
            runOnUiThread {
                result.onSuccess { sendAnalysis(it) }.onFailure { error ->
                    analyzeButton.isEnabled = true
                    analyzeButton.text = "✨  ANALIZEAZĂ PLANTA"
                    Toast.makeText(this, "Pregătirea fotografiilor a eșuat: ${error.message ?: "eroare necunoscută"}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun sendAnalysis(prepared: List<PreparedImage>) {
        val json = runCatching {
            AnalysisContract.buildRequest(prepared, AnalysisContext())
        }.getOrElse {
            analyzeButton.isEnabled = true
            analyzeButton.text = "✨  ANALIZEAZĂ PLANTA"
            Toast.makeText(this, it.message ?: "Cerere invalidă.", Toast.LENGTH_LONG).show()
            return
        }
        analyzeButton.text = "Analiză AI în curs…"
        ApiTransport().postJson(json) { networkResult ->
            runOnUiThread {
                analyzeButton.isEnabled = true
                analyzeButton.text = "✨  ANALIZEAZĂ PLANTA"
                networkResult.onSuccess { raw ->
                    runCatching { AnalysisResponseParser.parse(raw) }
                        .onSuccess { result ->
                            ResultScreen.show(this@MainActivity, result) { showScanScreen() }
                        }
                        .onFailure { error ->
                            Toast.makeText(this, "Răspuns AI invalid: ${error.message ?: "eroare necunoscută"}", Toast.LENGTH_LONG).show()
                        }
                }.onFailure { error ->
                    Toast.makeText(this, "Analiza nu a reușit. Fotografiile sunt păstrate. ${error.message ?: ""}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
