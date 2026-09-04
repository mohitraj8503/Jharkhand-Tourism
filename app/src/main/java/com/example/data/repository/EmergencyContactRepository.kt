package com.example.data.repository

import com.example.domain.model.EmergencyContact
import kotlinx.coroutines.flow.Flow

interface EmergencyContactRepository {
    fun getAllContacts(): Flow<List<EmergencyContact>>
    suspend fun getContactById(id: Long): EmergencyContact?
    suspend fun insertContact(contact: EmergencyContact): Long
    suspend fun updateContact(contact: EmergencyContact)
    suspend fun deleteContact(id: Long)

    fun getCheckedSafetyItemIds(): Flow<Set<String>>
    suspend fun toggleSafetyItem(itemId: String, isChecked: Boolean)
    suspend fun resetSafetyChecklist()
}
