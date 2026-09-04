package com.example.data.repository

import android.content.Context
import com.example.data.local.EmergencyContactDao
import com.example.data.local.EmergencyContactEntity
import com.example.domain.model.EmergencyContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EmergencyContactRepositoryImpl(
    private val dao: EmergencyContactDao,
    context: Context
) : EmergencyContactRepository {

    private val prefs = context.applicationContext.getSharedPreferences("jharvista_safety", Context.MODE_PRIVATE)

    private val _checkedItemIdsFlow = MutableStateFlow<Set<String>>(
        prefs.getStringSet(KEY_SAFETY_CHECKLIST, emptySet()) ?: emptySet()
    )

    override fun getAllContacts(): Flow<List<EmergencyContact>> {
        return dao.getAllContacts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getContactById(id: Long): EmergencyContact? {
        return withContext(Dispatchers.IO) {
            dao.getContactById(id)?.toDomain()
        }
    }

    override suspend fun insertContact(contact: EmergencyContact): Long {
        return withContext(Dispatchers.IO) {
            dao.insertContact(EmergencyContactEntity.fromDomain(contact))
        }
    }

    override suspend fun updateContact(contact: EmergencyContact) {
        withContext(Dispatchers.IO) {
            dao.updateContact(EmergencyContactEntity.fromDomain(contact))
        }
    }

    override suspend fun deleteContact(id: Long) {
        withContext(Dispatchers.IO) {
            dao.deleteContactById(id)
        }
    }

    override fun getCheckedSafetyItemIds(): Flow<Set<String>> {
        return _checkedItemIdsFlow.asStateFlow()
    }

    override suspend fun toggleSafetyItem(itemId: String, isChecked: Boolean) {
        withContext(Dispatchers.IO) {
            val current = _checkedItemIdsFlow.value.toMutableSet()
            if (isChecked) {
                current.add(itemId)
            } else {
                current.remove(itemId)
            }
            prefs.edit().putStringSet(KEY_SAFETY_CHECKLIST, current).apply()
            _checkedItemIdsFlow.value = current
        }
    }

    override suspend fun resetSafetyChecklist() {
        withContext(Dispatchers.IO) {
            prefs.edit().remove(KEY_SAFETY_CHECKLIST).apply()
            _checkedItemIdsFlow.value = emptySet()
        }
    }

    companion object {
        private const val KEY_SAFETY_CHECKLIST = "safety_checklist_checked_ids"
    }
}
