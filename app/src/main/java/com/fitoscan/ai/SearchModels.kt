package com.fitoscan.ai

enum class SearchCategory(val label: String) {
    CROPS("Culturi"),
    PROBLEMS("Boli și dăunători"),
    TREATMENTS("Tratamente"),
    GUIDE("Ghid"),
    EQUIPMENT("Echipamente"),
    IRRIGATION("Irigații"),
    JOURNAL("Jurnalul meu"),
    SAFETY("Ajutor / Siguranță")
}

data class SearchResultItem(
    val title: String,
    val subtitle: String,
    val category: SearchCategory,
    val action: String = ""
)
