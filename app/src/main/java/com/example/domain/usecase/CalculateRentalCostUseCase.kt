package com.example.domain.usecase

import com.example.domain.model.EVRental
import com.example.domain.model.RentalCostBreakdown

/**
 * Functional live price calculation for EV rentals.
 * Calculates base rental, GST (12%), refundable security deposit, and optional add-ons.
 */
class CalculateRentalCostUseCase {

    operator fun invoke(
        vehicle: EVRental,
        durationDays: Int,
        quantity: Int = 1,
        includePortableCharger: Boolean = false,
        includeChildSeat: Boolean = false
    ): RentalCostBreakdown {
        val safeDuration = durationDays.coerceAtLeast(1)
        val safeQuantity = quantity.coerceAtLeast(1)

        val baseRental = (vehicle.pricePerDay * safeDuration * safeQuantity).toDouble()
        val taxes = baseRental * 0.12 // 12% standard GST for vehicle rental in India

        // Refundable security deposit based on vehicle category
        val depositPerUnit = if (vehicle.category.contains("Scooter", ignoreCase = true) ||
            vehicle.category.contains("Bike", ignoreCase = true)
        ) {
            1000.0
        } else {
            2500.0
        }
        val securityDeposit = depositPerUnit * safeQuantity

        // Add-ons
        val chargerFee = if (includePortableCharger) 150.0 * safeDuration else 0.0
        val childSeatFee = if (includeChildSeat) 200.0 * safeDuration else 0.0
        val addOnsCost = (chargerFee + childSeatFee) * safeQuantity

        val total = baseRental + taxes + securityDeposit + addOnsCost

        return RentalCostBreakdown(
            baseRental = baseRental,
            taxes = taxes,
            securityDeposit = securityDeposit,
            addOnsCost = addOnsCost,
            total = total,
            durationDays = safeDuration
        )
    }
}
