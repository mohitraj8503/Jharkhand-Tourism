package com.example.domain.usecase

import com.example.data.repository.EmergencyContactRepository
import com.example.domain.model.EmergencyContact

class AddEmergencyContactUseCase(
    private val repository: EmergencyContactRepository
) {
    suspend operator fun invoke(name: String, phoneNumber: String, relationship: String): Result<Long> {
        val trimmedName = name.trim()
        val trimmedPhone = phoneNumber.trim()

        if (trimmedName.isEmpty()) {
            return Result.failure(IllegalArgumentException("Name cannot be empty"))
        }

        if (trimmedPhone.isEmpty()) {
            return Result.failure(IllegalArgumentException("Phone number cannot be empty"))
        }

        // Basic phone number validation: allows digits, spaces, plus, hyphens, min 7 digits
        val digitsOnly = trimmedPhone.filter { it.isDigit() }
        if (digitsOnly.length < 7) {
            return Result.failure(IllegalArgumentException("Please enter a valid phone number"))
        }

        val contact = EmergencyContact(
            id = 0L,
            name = trimmedName,
            phoneNumber = trimmedPhone,
            relationship = relationship.trim().ifEmpty { "Emergency Contact" }
        )

        return try {
            val id = repository.insertContact(contact)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
