package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val destination: String,
    val country: String,
    val dates: String,
    val durationDays: Int,
    val status: String, // "upcoming", "saved", "ended", "past"
    val percentReady: Int,
    val daysLeft: Int,
    val budgetTier: String, // "Budget", "Standard", "Luxury"
    val ecoScore: Int, // 1 to 100
    val carbonKg: Double,
    val coverStyle: String = "emerald"
)

@Entity(tableName = "saved_places")
data class SavedPlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subtitle: String,
    val location: String,
    val category: String, // "Trending", "Top Picks", "Nearby"
    val reasonWhy: String,
    val ecoCertified: Boolean,
    val crowdLevel: String, // "Low", "Moderate", "High"
    val imageUrl: String = "",
    val predictedFootfallAlert: String? = null,
    val alternativeSuggestion: String? = null,
    val carbonFootprintKg: Double = 12.0,
    val isSaved: Boolean = false
)

@Entity(tableName = "itinerary_events")
data class ItineraryEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tripId: Long,
    val dayNumber: Int,
    val timeSlot: String, // "10:00", "12:30", "15:00", "18:00"
    val title: String,
    val description: String,
    val location: String,
    val category: String,
    val costUsd: Double,
    val carbonKg: Double,
    val isEcoFriendly: Boolean,
    val ecoAlternative: String? = null
)

data class FlightItem(
    val id: String,
    val airline: String,
    val flightNumber: String,
    val departureTime: String,
    val arrivalTime: String,
    val duration: String,
    val fromCode: String,
    val toCode: String,
    val travelClass: String,
    val priceUsd: Double,
    val co2Kg: Double,
    val isEcoOption: Boolean,
    val localProviderPercent: Int = 78,
    val ecoFundPercent: Int = 10,
    val taxesPercent: Int = 12
)

data class WalletTransaction(
    val id: String,
    val title: String,
    val category: String,
    val timestamp: String,
    val amountUsd: Double,
    val isDebit: Boolean,
    val localProviderDirect: Boolean,
    val ecoTokenEarned: Int
)

data class ChatMessage(
    val id: String,
    val isUser: Boolean,
    val message: String,
    val timestamp: String,
    val isActionCard: Boolean = false,
    val cardTitle: String? = null,
    val cardSubtext: String? = null,
    val actionText: String? = null
)
