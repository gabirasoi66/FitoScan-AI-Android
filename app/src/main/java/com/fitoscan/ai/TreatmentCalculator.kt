package com.fitoscan.ai

object TreatmentCalculator {
    val standardTankVolumes = listOf(5.0, 10.0, 15.0, 20.0, 50.0, 100.0)

    fun calculateForWaterVolume(dose: VerifiedDose, tankLiters: Double): RecipeResult {
        require(dose.basis == DoseBasis.PER_WATER_VOLUME) {
            "Doza este exprimată pe suprafață și nu poate fi convertită doar din volumul rezervorului."
        }
        val reference = dose.referenceVolumeLiters
            ?: error("Volumul de apă de referință lipsește din doza verificată.")
        require(reference > 0 && tankLiters > 0)
        val amount = dose.amount * tankLiters / reference
        return RecipeResult(
            tankLiters, amount, dose.unit,
            "Calcul proporțional din doza verificată: ${dose.amount} ${dose.unit} / ${reference} L apă."
        )
    }

    fun calculatePerArea(
        dose: VerifiedDose,
        areaHa: Double,
        waterLitersPerHa: Double,
        tankLiters: Double
    ): Pair<RecipeResult, Int> {
        require(dose.basis == DoseBasis.PER_AREA)
        require(areaHa > 0 && waterLitersPerHa > 0 && tankLiters > 0)
        val refArea = dose.referenceAreaHa ?: 1.0
        val totalProduct = dose.amount * areaHa / refArea
        val totalWater = waterLitersPerHa * areaHa
        val tanks = kotlin.math.ceil(totalWater / tankLiters).toInt()
        return RecipeResult(
            totalWater, totalProduct, dose.unit,
            "Necesar total pentru ${areaHa} ha. Distribuția pe rezervoare trebuie adaptată volumului real de apă/ha."
        ) to tanks
    }
}
