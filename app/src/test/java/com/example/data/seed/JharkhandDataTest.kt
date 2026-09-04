package com.example.data.seed

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JharkhandDataTest {

    @Test
    fun `destinations list is populated with authentic Jharkhand places`() {
        val destinations = JharkhandData.destinations
        assertTrue("Destinations should have at least 10 verified spots", destinations.size >= 10)

        // Check key landmarks exist
        val names = destinations.map { it.name }
        assertTrue("Should include Betla National Park", names.any { it.contains("Betla", ignoreCase = true) })
        assertTrue("Should include Netarhat", names.any { it.contains("Netarhat", ignoreCase = true) })
        assertTrue("Should include Patratu Valley", names.any { it.contains("Patratu", ignoreCase = true) })
        assertTrue("Should include Hundru Falls", names.any { it.contains("Hundru", ignoreCase = true) })

        // Check each destination has valid coordinates, non-empty description, and working image URL
        destinations.forEach { dest ->
            assertTrue("ID must be positive for ${dest.name}", dest.id > 0)
            assertTrue("Name must not be blank", dest.name.isNotBlank())
            assertTrue("City must not be blank for ${dest.name}", dest.city.isNotBlank())
            assertTrue("Description must be detailed for ${dest.name}", dest.description.length > 20)
            assertTrue("ImageUrl must start with http for ${dest.name}", dest.imageUrl.startsWith("http"))
            // Jharkhand approximate latitude range 20.0 to 26.0, longitude range 82.0 to 89.0
            assertTrue("Latitude must be within Jharkhand region for ${dest.name}", dest.lat in 20.0..26.0)
            assertTrue("Longitude must be within Jharkhand region for ${dest.name}", dest.lng in 82.0..89.0)
        }
    }

    @Test
    fun `ev rentals list is populated with valid vehicles and realistic specs`() {
        val fleet = JharkhandData.evRentals
        assertTrue("EV fleet should have at least 5 vehicles", fleet.size >= 5)

        fleet.forEach { vehicle ->
            assertTrue("Vehicle name must not be blank", vehicle.vehicleName.isNotBlank())
            assertTrue("Range must be positive for ${vehicle.vehicleName}", vehicle.rangeKm > 0)
            assertTrue("Price per day must be positive for ${vehicle.vehicleName}", vehicle.pricePerDay > 0)
            assertTrue("Price per hour must be positive for ${vehicle.vehicleName}", vehicle.pricePerHour > 0)
            assertTrue("Seats must be at least 1 for ${vehicle.vehicleName}", vehicle.seats >= 1)
            assertNotNull("ImageUrl must not be null for ${vehicle.vehicleName}", vehicle.imageUrl)
            assertTrue("ImageUrl must start with http for ${vehicle.vehicleName}", vehicle.imageUrl!!.startsWith("http"))
            assertTrue("CO2 saved must be positive for ${vehicle.vehicleName}", vehicle.co2SavedKgPerDay > 0)
            assertTrue("Pickup locations must not be empty for ${vehicle.vehicleName}", vehicle.pickupLocations.isNotEmpty())
        }
    }

    @Test
    fun `charging stations list has valid stations in Jharkhand`() {
        val stations = JharkhandData.chargingStations
        assertTrue("Charging stations should not be empty", stations.isNotEmpty())

        stations.forEach { station ->
            assertTrue("Station name should not be blank", station.name.isNotBlank())
            assertTrue("Operator should not be blank", station.operator.isNotBlank())
            assertTrue("Power KW must be positive", station.powerKw > 0)
            assertTrue("Connector types must not be empty", station.connectorTypes.isNotEmpty())
        }
    }

    @Test
    fun `souvenirs and events are populated`() {
        assertTrue("Souvenirs list should not be empty", JharkhandData.souvenirs.isNotEmpty())
        assertTrue("Events list should not be empty", JharkhandData.events.isNotEmpty())
    }
}
