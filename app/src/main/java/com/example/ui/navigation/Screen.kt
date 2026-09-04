package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Default.Explore)
    data object Plan : Screen("plan", "Plan", Icons.Default.AutoAwesome)
    data object Trips : Screen("trips", "Trips", Icons.Default.Luggage)
    data object Wallet : Screen("wallet", "Wallet", Icons.Default.CreditCard)

    // Sub-screens
    data object BookingConfig : Screen("booking_config/{destination}", "Book", Icons.Default.Flight) {
        fun createRoute(destination: String) = "booking_config/$destination"
    }
    data object FlightSearch : Screen("flight_search", "Flights", Icons.Default.Flight)
    data object MapDiscovery : Screen("map_discovery", "Map", Icons.Default.Map)
    data object TripTimeline : Screen("trip_timeline/{tripId}", "Timeline", Icons.Default.Luggage) {
        fun createRoute(tripId: Long) = "trip_timeline/$tripId"
    }
    
    // Where to Go & Destination Detail
    data object WhereToGo : Screen("where_to_go", "Where to Go", Icons.Default.Explore)
    data object DestinationDetail : Screen("destination_detail/{destinationId}", "Destination Detail", Icons.Default.Explore) {
        fun createRoute(destinationId: Long) = "destination_detail/$destinationId"
    }

    // Rentals (EV Mobility)
    data object EVRental : Screen("ev_rental", "Rentals", Icons.Default.DirectionsCar)
    data object EVRentalDetail : Screen("ev_rental_detail/{rentalId}", "Rental Detail", Icons.Default.DirectionsCar) {
        fun createRoute(rentalId: Int) = "ev_rental_detail/$rentalId"
    }
    
    // Sidebar screens
    data object JharkhandGlance : Screen("jharkhand_glance", "At a Glance", Icons.Default.Explore)
    data object Events : Screen("events", "Events", Icons.Default.Explore)
    data object Hospitality : Screen("hospitality", "Hospitality", Icons.Default.Explore)
    data object Souvenirs : Screen("souvenirs", "Souvenirs", Icons.Default.Explore)
    data object HeliTourism : Screen("heli_tourism", "HeliTourism", Icons.Default.Explore)
    data object AudioGuide : Screen("audio_guide", "Audio Guide", Icons.Default.Explore)
    data object Share : Screen("share", "Share", Icons.Default.Explore)
}
