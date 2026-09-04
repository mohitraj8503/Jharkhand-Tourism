package com.example.domain.usecase

import com.example.domain.model.CarbonSavingsEstimate
import com.example.domain.model.EVRental
import java.util.Locale

/**
 * Calculates estimated carbon footprint avoided and fuel saved by using an EV instead of an ICE vehicle.
 */
class CalculateCarbonSavingsUseCase {

    operator fun invoke(
        vehicle: EVRental,
        durationDays: Int = 1,
        estimatedDistanceKm: Double? = null
    ): CarbonSavingsEstimate {
        val safeDuration = durationDays.coerceAtLeast(1)

        val co2AvoidedKg = if (estimatedDistanceKm != null && estimatedDistanceKm > 0) {
            // ICE vehicle average emissions ≈ 0.12 kg CO2 / km for cars, 0.04 kg/km for scooters
            val emissionFactor = if (vehicle.category.contains("Scooter", ignoreCase = true) ||
                vehicle.category.contains("Bike", ignoreCase = true)
            ) {
                0.045
            } else {
                0.14
            }
            estimatedDistanceKm * emissionFactor
        } else {
            vehicle.co2SavedKgPerDay * safeDuration
        }

        // 1 Litre of petrol produces approximately 2.31 kg of CO2
        val fuelSavedLitres = co2AvoidedKg / 2.31

        val roundedCo2 = String.format(Locale.US, "%.1f", co2AvoidedKg).toDouble()
        val roundedFuel = String.format(Locale.US, "%.1f", fuelSavedLitres).toDouble()

        return CarbonSavingsEstimate(
            co2AvoidedKg = roundedCo2,
            fuelSavedLitres = roundedFuel,
            ecoScore = vehicle.ecoScore,
            explanation = "Estimated compared with a similar petrol vehicle for the selected duration."
        )
    }
}
