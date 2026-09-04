package com.example.domain.usecase

import com.example.domain.model.EVRental
import com.example.domain.model.RangeCheckResult

/**
 * Validates whether an EV has adequate range for a designated trip or route.
 * Incorporates a 15% safety buffer for highway driving and terrain elevation changes in Jharkhand.
 */
class CheckEVRangeUseCase {

    operator fun invoke(
        vehicle: EVRental,
        routeName: String,
        distanceKm: Double,
        availableVehicles: List<EVRental> = emptyList()
    ): RangeCheckResult {
        // Practical highway & hilly terrain range is ~85% of ARAI rated range
        val practicalRange = vehicle.rangeKm * 0.85
        val isSufficient = distanceKm <= practicalRange

        return if (isSufficient) {
            RangeCheckResult(
                routeName = routeName,
                distanceKm = distanceKm,
                vehicleRangeKm = vehicle.rangeKm,
                isSufficient = true,
                warningMessage = null,
                suggestedAlternativeVehicleName = null,
                suggestedChargingStop = null
            )
        } else {
            // Find a higher-range alternative from available vehicles
            val alternative = availableVehicles
                .filter { it.rangeKm * 0.85 >= distanceKm && it.id != vehicle.id }
                .minByOrNull { it.pricePerDay }

            val chargingStopSuggestion = when {
                routeName.contains("Patratu", ignoreCase = true) || routeName.contains("Ramgarh", ignoreCase = true) ->
                    "BPCL Highway EV Fast Charger on NH 33 (Ormanjhi Hub)"
                routeName.contains("Netarhat", ignoreCase = true) || routeName.contains("Betla", ignoreCase = true) ->
                    "Ranchi Club Fast EV Station before entering Latehar Ghats"
                routeName.contains("Deoghar", ignoreCase = true) ->
                    "IOCL Fast EV Charger on Deoghar Bypass"
                else ->
                    "Tata Power Fast EV Station in Ranchi"
            }

            RangeCheckResult(
                routeName = routeName,
                distanceKm = distanceKm,
                vehicleRangeKm = vehicle.rangeKm,
                isSufficient = false,
                warningMessage = "This trip is approximately ${distanceKm.toInt()} km. This vehicle is rated for ${vehicle.rangeKm} km (practical range ≈ ${practicalRange.toInt()} km). Range may be insufficient without a charging stop.",
                suggestedAlternativeVehicleName = alternative?.let { "${it.vehicleName} (${it.rangeKm} km range)" } ?: "a vehicle with 300+ km range",
                suggestedChargingStop = chargingStopSuggestion
            )
        }
    }
}
