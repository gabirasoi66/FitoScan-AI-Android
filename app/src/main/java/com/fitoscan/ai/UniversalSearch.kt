package com.fitoscan.ai

object UniversalSearch {
    fun search(query: String, journalCases: List<JournalCase>): List<SearchResultItem> {
        val q=query.trim().lowercase()
        if(q.isBlank()) return emptyList()
        val staticItems=listOf(
            SearchResultItem("Măr","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Păr","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Prun","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Piersic","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Cireș","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Vișin","Cultură pomicolă",SearchCategory.CROPS),
            SearchResultItem("Grâu","Cultură mare",SearchCategory.CROPS),
            SearchResultItem("Porumb","Cultură mare",SearchCategory.CROPS),
            SearchResultItem("Floarea-soarelui","Cultură mare",SearchCategory.CROPS),
            SearchResultItem("Rapiță","Cultură mare",SearchCategory.CROPS),
            SearchResultItem("Cartof","Cultură",SearchCategory.CROPS),
            SearchResultItem("Sorg","Cultură mare",SearchCategory.CROPS),
            SearchResultItem("Vinete","Legumicultură",SearchCategory.CROPS),
            SearchResultItem("Pete pe frunze","Simptom — cauzele trebuie diferențiate",SearchCategory.PROBLEMS,"SCAN"),
            SearchResultItem("Îngălbenirea frunzelor","Simptom — poate avea cauze multiple",SearchCategory.PROBLEMS,"SCAN"),
            SearchResultItem("Calculator soluție","Calculează numai din doză verificată",SearchCategory.TREATMENTS),
            SearchResultItem("Echipament de aplicare","Ghid tehnic și EIP",SearchCategory.EQUIPMENT),
            SearchResultItem("Irigații & apă","Dimensionare și ghid tehnic",SearchCategory.IRRIGATION),
            SearchResultItem("SOS & Localizare","Ajutor și siguranță",SearchCategory.SAFETY)
        )
        val journal=journalCases.map {
            SearchResultItem(it.crop,it.assessment,SearchCategory.JOURNAL)
        }
        return (staticItems+journal).filter {
            it.title.lowercase().contains(q) || it.subtitle.lowercase().contains(q)
        }
    }
}
