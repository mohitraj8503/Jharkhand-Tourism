package com.example.domain.model

/**
 * Core domain model for Electric Vehicle rentals in JharVista.
 * Represents verified electric vehicles available for low-carbon travel across Jharkhand.
 */
data class EVRental(
    val id: Int,
    val providerName: String,
    val vehicleName: String,
    val category: String, // "Electric Scooter", "Electric Bike", "Electric Car", "Electric SUV"
    val city: String,
    val pricePerHour: Int,
    val pricePerDay: Int,
    val rangeKm: Int,
    val chargingTimeHours: Double,
    val seats: Int,
    val ecoScore: Int,
    val co2SavedKgPerDay: Double,
    val imageUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val pickupLocations: List<String>,
    val available: Boolean
)

/**
 * Real-world EV charging station in Jharkhand.
 * Status is kept as "Availability unknown" to avoid fabricating live data.
 */
data class ChargingStation(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val operator: String,
    val latitude: Double,
    val longitude: Double,
    val status: String = "Availability unknown",
    val connectorTypes: List<String>,
    val isFastCharger: Boolean,
    val powerKw: Int = 30
)

/**
 * User request to book an EV rental.
 */
data class EVBookingRequest(
    val vehicleId: Int,
    val pickupLocation: String,
    val dropLocation: String,
    val startDate: String,
    val endDate: String,
    val durationDays: Int,
    val quantity: Int = 1,
    val includeHelmet: Boolean = true,
    val includePortableCharger: Boolean = false,
    val includeChildSeat: Boolean = false
)

/**
 * Breakdown of rental costs (functional calculation).
 */
data class RentalCostBreakdown(
    val baseRental: Double,
    val taxes: Double,
    val securityDeposit: Double,
    val addOnsCost: Double,
    val total: Double,
    val durationDays: Int
)

/**
 * Environmental impact estimation compared to ICE vehicles.
 */
data class CarbonSavingsEstimate(
    val co2AvoidedKg: Double,
    val fuelSavedLitres: Double,
    val ecoScore: Int,
    val explanation: String = "Estimated compared with a similar petrol vehicle for the selected distance."
)

/**
 * Result of evaluating route distance against practical EV range.
 */
data class RangeCheckResult(
    val routeName: String,
    val distanceKm: Double,
    val vehicleRangeKm: Int,
    val isSufficient: Boolean,
    val warningMessage: String? = null,
    val suggestedAlternativeVehicleName: String? = null,
    val suggestedChargingStop: String? = null
)

/**
 * Confirmation details after booking an EV.
 */
data class EVBookingConfirmation(
    val bookingId: String,
    val vehicle: EVRental,
    val pickupLocation: String,
    val dropLocation: String,
    val startDate: String,
    val endDate: String,
    val durationDays: Int,
    val costBreakdown: RentalCostBreakdown,
    val carbonSavings: CarbonSavingsEstimate,
    val timestamp: Long = System.currentTimeMillis()
)
