package com.example.domain.usecase

import com.example.data.repository.EmergencyContactRepository
import kotlinx.coroutines.flow.Flow

data class SafetyChecklistItem(
    val id: String,
    val title: String,
    val description: String
)

object DefaultSafetyChecklist {
    val items = listOf(
        SafetyChecklistItem(
            id = "water",
            title = "Carry drinking water",
            description = "Stay hydrated, especially during long hikes, waterfall treks, and temple visits."
        ),
        SafetyChecklistItem(
            id = "contacts",
            title = "Keep emergency contacts accessible",
            description = "Save local emergency numbers (112, 108) and personal emergency contacts in the app."
        ),
        SafetyChecklistItem(
            id = "weather",
            title = "Check weather before waterfall/hill trips",
            description = "Monsoon flash floods can be sudden near Hundru, Jonha, and Dassam falls."
        ),
        SafetyChecklistItem(
            id = "first_aid",
            title = "Carry basic first-aid supplies",
            description = "Include antiseptics, bandages, insect repellent, and essential personal medicines."
        ),
        SafetyChecklistItem(
            id = "phone_charge",
            title = "Keep your phone charged",
            description = "Carry a power bank when exploring forested sanctuaries and rural trails."
        ),
        SafetyChecklistItem(
            id = "share_itinerary",
            title = "Share your itinerary with someone",
            description = "Let friends, family, or your hotel manager know your intended destination for the day."
        ),
        SafetyChecklistItem(
            id = "safety_signs",
            title = "Follow local safety signs",
            description = "Heed tourist advisory boards, forest department guidelines, and danger markers."
        ),
        SafetyChecklistItem(
            id = "waterfall_zones",
            title = "Avoid entering restricted waterfall zones",
            description = "Rocky surfaces near water cascades are extremely slippery and deep currents are deceptive."
        ),
        SafetyChecklistItem(
            id = "wildlife_boundaries",
            title = "Respect wildlife boundaries",
            description = "Maintain a safe distance from wild animals in Betla, Dalma, and Hazaribagh reserves."
        ),
        SafetyChecklistItem(
            id = "location_sharing",
            title = "Keep emergency location sharing available",
            description = "Enable GPS on your device so location links can be generated immediately if help is needed."
        )
    )
}

class UpdateSafetyChecklistUseCase(
    private val repository: EmergencyContactRepository
) {
    fun getCheckedItemIds(): Flow<Set<String>> = repository.getCheckedSafetyItemIds()

    suspend fun toggleItem(itemId: String, isChecked: Boolean) {
        repository.toggleSafetyItem(itemId, isChecked)
    }

    suspend fun resetAll() {
        repository.resetSafetyChecklist()
    }
}
