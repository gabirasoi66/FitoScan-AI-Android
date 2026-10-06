package com.fitoscan.ai

import org.json.JSONArray
import org.json.JSONObject

data class AnalysisContext(
    val crop: String = "",
    val stage: String = ""
)

object AnalysisContract {
    fun buildRequest(
        images: List<PreparedImage>,
        context: AnalysisContext = AnalysisContext()
    ): String {
        require(images.isNotEmpty()) { "Este necesară cel puțin o fotografie." }
        require(images.size <= 4) { "Sunt acceptate maximum 4 fotografii." }

        val imageArray = JSONArray()
        images.forEach {
            imageArray.put("data:${it.mimeType};base64,${it.base64}")
        }

        return JSONObject().apply {
            put("images", imageArray)
            put("context", JSONObject().apply {
                put("crop", context.crop)
                put("stage", context.stage)
            })
        }.toString()
    }
}
