package com.example.domain.model

data class EmergencyServiceItem(
    val id: String,
    val name: String,
    val number: String,
    val description: String,
    val iconEmoji: String,
    val category: String,
    val isPrimary: Boolean = false
)

object JharkhandEmergencyContacts {
    val nationalEmergency = EmergencyServiceItem(
        id = "112",
        name = "112 — National Emergency",
        number = "112",
        description = "Unified single-number emergency assistance for Police, Fire, and Medical emergencies across India and Jharkhand.",
        iconEmoji = "🆘",
        category = "Unified",
        isPrimary = true
    )

    val ambulance = EmergencyServiceItem(
        id = "108",
        name = "Ambulance Service",
        number = "108",
        description = "Government emergency medical technician & ambulance dispatch service across Jharkhand.",
        iconEmoji = "🚑",
        category = "Medical",
        isPrimary = true
    )

    val police = EmergencyServiceItem(
        id = "100",
        name = "Police Control Room",
        number = "100",
        description = "Jharkhand State Police emergency response, security, and patrol assistance.",
        iconEmoji = "👮",
        category = "Police",
        isPrimary = true
    )

    val fire = EmergencyServiceItem(
        id = "101",
        name = "Fire & Rescue Service",
        number = "101",
        description = "Jharkhand Fire Service brigade for fire outbreaks, rescues, and hazardous situations.",
        iconEmoji = "🔥",
        category = "Fire",
        isPrimary = true
    )

    val womenHelpline = EmergencyServiceItem(
        id = "1091",
        name = "Women Helpline",
        number = "1091",
        description = "24/7 immediate assistance, counselling, and emergency police support for women.",
        iconEmoji = "🛡️",
        category = "Support"
    )

    val disasterManagement = EmergencyServiceItem(
        id = "1070",
        name = "Disaster Management (SDMA)",
        number = "1070",
        description = "Jharkhand State Disaster Management Authority emergency operations room.",
        iconEmoji = "⚠️",
        category = "Disaster"
    )

    val touristHelpline = EmergencyServiceItem(
        id = "1363",
        name = "Tourist Helpline",
        number = "1363",
        description = "Toll-free tourist guidance, safety information, and assistance in multiple languages.",
        iconEmoji = "🧭",
        category = "Tourism"
    )

    val pregnancyAmbulance = EmergencyServiceItem(
        id = "102",
        name = "Maternal & Infant Ambulance",
        number = "102",
        description = "Free maternal health and infant emergency transport service.",
        iconEmoji = "🏥",
        category = "Medical"
    )

    val allServices: List<EmergencyServiceItem> = listOf(
        nationalEmergency,
        ambulance,
        police,
        fire,
        womenHelpline,
        disasterManagement,
        touristHelpline,
        pregnancyAmbulance
    )
}
