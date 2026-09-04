package com.example.domain.model

data class EmergencyContact(
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val relationship: String
)
