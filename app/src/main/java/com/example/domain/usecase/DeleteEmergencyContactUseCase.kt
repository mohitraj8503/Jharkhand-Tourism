package com.example.domain.usecase

import com.example.data.repository.EmergencyContactRepository

class DeleteEmergencyContactUseCase(
    private val repository: EmergencyContactRepository
) {
    suspend operator fun invoke(id: Long): Result<Unit> {
        return try {
            repository.deleteContact(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
