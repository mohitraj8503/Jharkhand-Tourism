package com.example.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri

class ShareCurrentLocationUseCase {

    fun generateMapsLink(lat: Double, lng: Double): String {
        return "https://www.google.com/maps/search/?api=1&query=$lat,$lng"
    }

    fun formatGeneralShareMessage(lat: Double, lng: Double): String {
        val mapsLink = generateMapsLink(lat, lng)
        return "I'm currently at this location and may need assistance.\n\n" +
                "My current location:\n" +
                "$mapsLink\n\n" +
                "Shared using JharVista."
    }

    fun formatContactShareMessage(contactName: String, lat: Double, lng: Double): String {
        val mapsLink = generateMapsLink(lat, lng)
        return "Hi $contactName, I may need assistance.\n\n" +
                "My current location:\n" +
                "$mapsLink\n\n" +
                "Please check on me.\n" +
                "Shared using JharVista."
    }

    fun createGeneralShareIntent(lat: Double, lng: Double): Intent {
        val message = formatGeneralShareMessage(lat, lng)
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Emergency Location - JharVista")
            putExtra(Intent.EXTRA_TEXT, message)
        }
        return Intent.createChooser(sendIntent, "Share Location via")
    }

    fun createContactSmsIntent(phoneNumber: String, contactName: String, lat: Double, lng: Double): Intent {
        val message = formatContactShareMessage(contactName, lat, lng)
        val uri = Uri.parse("smsto:$phoneNumber")
        return Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", message)
        }
    }
}
