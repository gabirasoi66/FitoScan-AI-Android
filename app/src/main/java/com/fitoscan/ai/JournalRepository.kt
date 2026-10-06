package com.fitoscan.ai

import android.content.Context
import org.json.JSONArray

class JournalRepository(context: Context) {
    private val prefs = context.getSharedPreferences("fitoscan_journal", Context.MODE_PRIVATE)

    fun saveCase(case: JournalCase) {
        val all = loadCases().toMutableList()
        if (all.none { it.id == case.id }) all.add(0, case)
        val array = JSONArray()
        all.forEach { array.put(JournalJson.caseToJson(it)) }
        prefs.edit().putString("cases", array.toString()).apply()
    }

    fun loadCases(): List<JournalCase> = runCatching {
        val array = JSONArray(prefs.getString("cases", "[]"))
        (0 until array.length()).map { JournalJson.caseFromJson(array.getJSONObject(it)) }
    }.getOrDefault(emptyList())
}
