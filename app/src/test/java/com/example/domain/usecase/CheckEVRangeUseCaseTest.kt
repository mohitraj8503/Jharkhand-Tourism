package com.example.domain.usecase

import com.example.domain.model.EVRental
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CheckEVRangeUseCaseTest {

    private lateinit var useCase: CheckEVRangeUseCase

    private val baseCar = EVRental(
        id = 1,
        providerName = "GreenWheels Jharkhand",
        vehicleName = "Tata Nexon EV",
        category = "Electric SUV",
        city = "Ranchi",
        pricePerHour = 299,
        pricePerDay = 2499,
        rangeKm = 325,
        chargingTimeHours = 1.0,
        seats = 5,
        ecoScore = 92,
        co2SavedKgPerDay = 18.5,
        imageUrl = "https://example.com/nexon.jpg",
        latitude = 23.3441,
        longitude = 85.3096,
        pickupLocations = listOf("Ranchi Station Hub"),
        available = true
    )

    private val longRangeCar = EVRental(
        id = 2,
        providerName = "EcoRide Jamshedpur",
        vehicleName = "MG ZS EV Long Range",
        category = "Electric SUV",
        city = "Ranchi",
        pricePerHour = 399,
        pricePerDay = 3299,
        rangeKm = 461,
        chargingTimeHours = 1.2,
        seats = 5,
        ecoScore = 95,
        co2SavedKgPerDay = 22.0,
        imageUrl = "https://example.com/mg.jpg",
        latitude = 23.3441,
        longitude = 85.3096,
        pickupLocations = listOf("Birsa Munda Airport Hub"),
        available = true
    )

    @Before
    fun setUp() {
        useCase = CheckEVRangeUseCase()
    }

    @Test
    fun `sufficient range returns isSufficient true without warning`() {
        // Nexon EV rated at 325 km -> practical range is 325 * 0.85 = 276.25 km
        // Route is 150 km (Ranchi to Netarhat)
        val result = useCase(
            vehicle = baseCar,
            routeName = "Ranchi to Netarhat",
            distanceKm = 150.0,
            availableVehicles = listOf(baseCar, longRangeCar)
        )

        assertTrue(result.isSufficient)
        assertNull(result.warningMessage)
        assertNull(result.suggestedAlternativeVehicleName)
        assertNull(result.suggestedChargingStop)
        assertEquals(150.0, result.distanceKm, 0.01)
        assertEquals(325, result.vehicleRangeKm)
    }

    @Test
    fun `insufficient range returns isSufficient false with alternative and charging stop`() {
        // Nexon EV rated at 325 km -> practical range = 276.25 km
        // Route is 300 km (e.g. Ranchi to Deoghar Circuit)
        val result = useCase(
            vehicle = baseCar,
            routeName = "Ranchi to Deoghar Circuit",
            distanceKm = 300.0,
            availableVehicles = listOf(baseCar, longRangeCar)
        )

        assertFalse(result.isSufficient)
        assertNotNull(result.warningMessage)
        assertTrue(result.warningMessage!!.contains("approximately 300 km"))
        assertNotNull(result.suggestedAlternativeVehicleName)
        assertTrue(result.suggestedAlternativeVehicleName!!.contains("MG ZS EV"))
        assertNotNull(result.suggestedChargingStop)
        assertTrue(result.suggestedChargingStop!!.contains("Deoghar"))
    }

    @Test
    fun `patratu route suggests patratu or ormanjhi charging stop`() {
        val result = useCase(
            vehicle = baseCar.copy(rangeKm = 50),
            routeName = "Ranchi to Patratu Valley",
            distanceKm = 80.0,
            availableVehicles = listOf(baseCar)
        )

        assertFalse(result.isSufficient)
        assertNotNull(result.suggestedChargingStop)
        assertTrue(result.suggestedChargingStop!!.contains("Ormanjhi Hub") || result.suggestedChargingStop!!.contains("Patratu"))
    }
}
