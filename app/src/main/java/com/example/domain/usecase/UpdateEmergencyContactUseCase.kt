package com.example.domain.usecase

import com.example.data.repository.EmergencyContactRepository
import com.example.domain.model.EmergencyContact

class UpdateEmergencyContactUseCase(
    private val repository: EmergencyContactRepository
) {
    suspend operator fun invoke(id: Long, name: String, phoneNumber: String, relationship: String): Result<Unit> {
        val trimmedName = name.trim()
        val trimmedPhone = phoneNumber.trim()

        if (trimmedName.isEmpty()) {
            return Result.failure(IllegalArgumentException("Name cannot be empty"))
        }

        if (trimmedPhone.isEmpty()) {
            return Result.failure(IllegalArgumentException("Phone number cannot be empty"))
        }

        val digitsOnly = trimmedPhone.filter { it.isDigit() }
        if (digitsOnly.length < 7) {
            return Result.failure(IllegalArgumentException("Please enter a valid phone number"))
        }

        val contact = EmergencyContact(
            id = id,
            name = trimmedName,
            phoneNumber = trimmedPhone,
            relationship = relationship.trim().ifEmpty { "Emergency Contact" }
        )

        return try {
            repository.updateContact(contact)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
