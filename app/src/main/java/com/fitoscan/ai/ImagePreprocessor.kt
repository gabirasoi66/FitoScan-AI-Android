package com.fitoscan.ai

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

data class PreparedImage(val mimeType: String, val base64: String, val width: Int, val height: Int)

object ImagePreprocessor {
    fun prepare(contentResolver: ContentResolver, uri: Uri, maxDimension: Int = 1600): PreparedImage {
        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: error("Fotografia nu poate fi citită.")
        val original = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?: error("Format de imagine neacceptat.")
        val scale = minOf(1f, maxDimension.toFloat() / maxOf(original.width, original.height))
        val bitmap = if (scale < 1f) Bitmap.createScaledBitmap(
            original,
            (original.width * scale).toInt().coerceAtLeast(1),
            (original.height * scale).toInt().coerceAtLeast(1),
            true
        ) else original
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 86, out)
        val width = bitmap.width
        val height = bitmap.height
        val encoded = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
        if (bitmap !== original) bitmap.recycle()
        original.recycle()
        return PreparedImage("image/jpeg", encoded, width, height)
    }
}
