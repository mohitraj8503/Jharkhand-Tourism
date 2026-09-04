package com.example.data.model

data class Destination(
    val id: Long,
    val name: String,
    val city: String,
    val type: String,
    val lat: Double,
    val lng: Double,
    val entryFee: String,
    val bestTime: String,
    val imageUrl: String,
    val description: String,
    val rating: Double,
    val ecoCertified: Boolean = true,
    val crowdLevel: String = "Low", // "Low", "Moderate", "Busy"
    val predictedFootfallAlert: String? = null,
    val alternativeSuggestion: String? = null,
    val timings: String = "06:00 AM – 06:00 PM"
)
