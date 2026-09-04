package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.EmergencyContact

@Entity(tableName = "emergency_contacts")
data class EmergencyContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val relationship: String
) {
    fun toDomain(): EmergencyContact = EmergencyContact(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        relationship = relationship
    )

    companion object {
        fun fromDomain(contact: EmergencyContact): EmergencyContactEntity = EmergencyContactEntity(
            id = contact.id,
            name = contact.name,
            phoneNumber = contact.phoneNumber,
            relationship = contact.relationship
        )
    }
}
