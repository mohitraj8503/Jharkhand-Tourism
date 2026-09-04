package com.example.domain.usecase

import com.example.data.repository.EmergencyContactRepository
import com.example.domain.model.EmergencyContact
import kotlinx.coroutines.flow.Flow

class GetEmergencyContactsUseCase(
    private val repository: EmergencyContactRepository
) {
    operator fun invoke(): Flow<List<EmergencyContact>> = repository.getAllContacts()
}
