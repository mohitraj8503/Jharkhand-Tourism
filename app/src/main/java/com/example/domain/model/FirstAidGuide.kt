package com.example.domain.model

data class FirstAidTopic(
    val id: String,
    val title: String,
    val iconEmoji: String,
    val summary: String,
    val steps: List<String>,
    val warning: String? = null
)

object FirstAidGuideData {
    val topics = listOf(
        FirstAidTopic(
            id = "cuts",
            title = "Minor Cuts & Scrapes",
            iconEmoji = "🩹",
            summary = "Common during forest treks and rocky terrain walks.",
            steps = listOf(
                "Wash hands with clean water or use sanitizer.",
                "Rinse the wound gently with clean drinking water.",
                "Apply gentle direct pressure with a clean cloth to stop bleeding.",
                "Apply an antibacterial ointment if available.",
                "Cover with a sterile bandage to prevent dirt and infection."
            ),
            warning = "If bleeding doesn't stop after 10 minutes or the wound is deep, seek immediate medical care."
        ),
        FirstAidTopic(
            id = "burns",
            title = "Burns & Scalds",
            iconEmoji = "🔥",
            summary = "Campfires, hot cooking gear, or sun exposure.",
            steps = listOf(
                "Cool the burn under cold running clean water for at least 10–15 minutes.",
                "Do NOT apply ice directly, butter, oil, or toothpastes.",
                "Cover loosely with a clean, non-stick sterile dressing.",
                "Take simple pain relief if suitable and stay hydrated."
            ),
            warning = "For blistering, large burns, or facial burns, dial 108 or 112 immediately."
        ),
        FirstAidTopic(
            id = "dehydration",
            title = "Dehydration",
            iconEmoji = "💧",
            summary = "Dry mouth, dark urine, dizziness during outdoor excursions.",
            steps = listOf(
                "Move to a shaded or cool indoor area immediately.",
                "Sip water slowly or drink Oral Rehydration Salts (ORS) / coconut water.",
                "Rest and avoid strenuous exertion until fully recovered.",
                "Loosen tight clothing to encourage airflow."
            ),
            warning = "If vomiting prevents fluid intake or confusion occurs, seek emergency medical care."
        ),
        FirstAidTopic(
            id = "heat_exhaustion",
            title = "Heat Exhaustion",
            iconEmoji = "☀️",
            summary = "Heavy sweating, weakness, clammy skin, nausea under hot sun.",
            steps = listOf(
                "Move the person to a cool, shaded, or air-conditioned area.",
                "Lie down and slightly elevate the feet.",
                "Loosen or remove excess layers of clothing.",
                "Apply cool, wet cloths to the neck, forehead, and armpits.",
                "Provide slow sips of cool water or electrolyte solution."
            ),
            warning = "If body temperature exceeds 103°F (39.4°C), sweating stops, or fainting occurs (Heat Stroke), dial 112 immediately."
        ),
        FirstAidTopic(
            id = "insect_bites",
            title = "Insect & Leech Bites",
            iconEmoji = "🦟",
            summary = "Frequent near wetlands, dense forest reserves, and waterfalls.",
            steps = listOf(
                "Wash the bite area thoroughly with mild soap and water.",
                "For bee stings, gently scrape the stinger off (avoid pinching the venom sac).",
                "Apply a cold pack or ice wrapped in cloth for 10 minutes to reduce swelling.",
                "Apply calamine lotion or mild hydrocortisone to soothe itching.",
                "Avoid scratching the bite area to prevent secondary bacterial infection."
            ),
            warning = "Watch for signs of severe allergic reaction (difficulty breathing, facial swelling)."
        ),
        FirstAidTopic(
            id = "sprains",
            title = "Sprains & Strains",
            iconEmoji = "🦶",
            summary = "Twisted ankle on uneven trails or waterfall rocks.",
            steps = listOf(
                "Rest: Stop walking and avoid bearing weight on the injured limb.",
                "Ice: Apply a cold pack wrapped in cloth for 15–20 minutes at a time.",
                "Compress: Support the joint with an elastic bandage (not too tight).",
                "Elevate: Keep the injured limb raised above heart level when resting."
            ),
            warning = "If the joint is visibly deformed or completely unable to bear weight, seek X-ray evaluation."
        ),
        FirstAidTopic(
            id = "allergies",
            title = "Allergic Reactions",
            iconEmoji = "⚠️",
            summary = "Reactions to pollen, forest plants, new foods, or insect venom.",
            steps = listOf(
                "Identify and safely remove the source of the allergen if possible.",
                "Take an oral antihistamine if prescribed or standard.",
                "Apply cold compress to skin rashes or hives.",
                "Stay calm and sit comfortably in an upright position."
            ),
            warning = "If there is lip/throat swelling, wheezing, or difficulty breathing (Anaphylaxis), call 112 immediately."
        )
    )
}
