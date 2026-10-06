package com.fitoscan.ai

enum class AuthorizationStatus(val label: String) {
    AUTHORIZED("AUTORIZAT"),
    VERIFY_REQUIRED("VERIFICARE NECESARĂ"),
    EMERGENCY("AUTORIZAȚIE DE URGENȚĂ"),
    UNAVAILABLE("INDISPONIBIL PENTRU RECOMANDARE")
}

enum class DoseBasis { PER_WATER_VOLUME, PER_AREA }

data class VerifiedDose(
    val amount: Double,
    val unit: String,
    val basis: DoseBasis,
    val referenceVolumeLiters: Double? = null,
    val referenceAreaHa: Double? = null,
    val source: String,
    val verifiedAt: String
)

data class TreatmentOption(
    val crop: String,
    val target: String,
    val commercialName: String,
    val activeIngredient: String,
    val formulation: String = "",
    val status: AuthorizationStatus,
    val dose: VerifiedDose? = null,
    val phiDays: Int? = null,
    val maxApplications: Int? = null,
    val restrictions: List<String> = emptyList()
)

data class RecipeResult(
    val waterLiters: Double,
    val productAmount: Double,
    val productUnit: String,
    val note: String
)
