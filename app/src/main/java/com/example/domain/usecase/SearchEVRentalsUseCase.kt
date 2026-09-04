package com.example.domain.usecase

import com.example.domain.model.EVRental

/**
 * Filters and searches EV rentals based on user search query and multi-criteria filters.
 */
class SearchEVRentalsUseCase {

    operator fun invoke(
        rentals: List<EVRental>,
        query: String = "",
        category: String = "All",
        city: String = "All",
        priceFilter: String = "All",
        rangeFilter: String = "All",
        ecoScoreFilter: String = "All",
        availableOnly: Boolean = false
    ): List<EVRental> {
        val trimmedQuery = query.trim().lowercase()

        return rentals.filter { rental ->
            // Search query matching
            val matchesQuery = trimmedQuery.isEmpty() ||
                    rental.vehicleName.lowercase().contains(trimmedQuery) ||
                    rental.providerName.lowercase().contains(trimmedQuery) ||
                    rental.city.lowercase().contains(trimmedQuery) ||
                    rental.category.lowercase().contains(trimmedQuery) ||
                    rental.pickupLocations.any { it.lowercase().contains(trimmedQuery) }

            // Category matching
            val matchesCategory = when (category) {
                "All" -> true
                "Scooter", "Electric Scooter" -> rental.category.contains("Scooter", ignoreCase = true)
                "Bike", "Electric Bike" -> rental.category.contains("Bike", ignoreCase = true)
                "Car", "Electric Car" -> rental.category.equals("Electric Car", ignoreCase = true)
                "SUV", "Electric SUV" -> rental.category.contains("SUV", ignoreCase = true)
                else -> rental.category.equals(category, ignoreCase = true)
            }

            // City matching
            val matchesCity = city == "All" || rental.city.equals(city, ignoreCase = true)

            // Price matching
            val matchesPrice = when (priceFilter) {
                "Under ₹1,000/day", "<₹1,000" -> rental.pricePerDay < 1000
                "₹1,000–₹2,000/day", "₹1,000-₹2,000" -> rental.pricePerDay in 1000..2000
                "₹2,000+/day", "₹2,000+" -> rental.pricePerDay >= 2000
                else -> true
            }

            // Range matching
            val matchesRange = when (rangeFilter) {
                "100+ km" -> rental.rangeKm >= 100
                "200+ km" -> rental.rangeKm >= 200
                "300+ km" -> rental.rangeKm >= 300
                else -> true
            }

            // Eco score matching
            val matchesEco = when (ecoScoreFilter) {
                "80+" -> rental.ecoScore >= 80
                "90+" -> rental.ecoScore >= 90
                else -> true
            }

            // Availability matching
            val matchesAvailability = !availableOnly || rental.available

            matchesQuery && matchesCategory && matchesCity && matchesPrice && matchesRange && matchesEco && matchesAvailability
        }
    }
}
