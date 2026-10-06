package com.fitoscan.ai

import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

enum class EvolutionStatus(val label: String) {
    IMPROVING("Îmbunătățire"),
    STABLE("Stabil"),
    WORSENING("Agravare"),
    NEW_PROBLEM("Problemă nouă"),
    INSUFFICIENT_DATA("Date insuficiente")
}

data class JournalCase(
    val id: String = UUID.randomUUID().toString(),
    val createdAt: Long = System.currentTimeMillis(),
    val crop: String,
    val scientificName: String = "",
    val assessment: String,
    val certainty: String,
    val actionLevel: String,
    val recommendation: List<String> = emptyList(),
    val photoUris: List<String> = emptyList()
)

data class MonitoringEntry(
    val id: String = UUID.randomUUID().toString(),
    val caseId: String,
    val createdAt: Long = System.currentTimeMillis(),
    val status: EvolutionStatus = EvolutionStatus.INSUFFICIENT_DATA,
    val note: String = "",
    val photoUris: List<String> = emptyList()
)

object JournalJson {
    fun caseToJson(c: JournalCase) = JSONObject().apply {
        put("id", c.id); put("createdAt", c.createdAt); put("crop", c.crop)
        put("scientificName", c.scientificName); put("assessment", c.assessment)
        put("certainty", c.certainty); put("actionLevel", c.actionLevel)
        put("recommendation", JSONArray(c.recommendation))
        put("photoUris", JSONArray(c.photoUris))
    }
    fun caseFromJson(o: JSONObject) = JournalCase(
        id=o.getString("id"), createdAt=o.getLong("createdAt"),
        crop=o.optString("crop"), scientificName=o.optString("scientificName"),
        assessment=o.optString("assessment"), certainty=o.optString("certainty"),
        actionLevel=o.optString("actionLevel"),
        recommendation=(0 until o.optJSONArray("recommendation")?.length().orZero()).map {
            o.getJSONArray("recommendation").getString(it)
        },
        photoUris=(0 until o.optJSONArray("photoUris")?.length().orZero()).map {
            o.getJSONArray("photoUris").getString(it)
        }
    )
    private fun Int?.orZero() = this ?: 0
}
