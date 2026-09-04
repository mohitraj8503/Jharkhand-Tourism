package com.example.domain.usecase

import android.content.Intent
import android.net.Uri

class FindNearbyHospitalUseCase {

    fun createFindHospitalsIntent(lat: Double? = null, lng: Double? = null): Intent {
        val uri = if (lat != null && lng != null) {
            Uri.parse("geo:$lat,$lng?q=hospital+emergency")
        } else {
            Uri.parse("geo:0,0?q=hospital+emergency")
        }

        val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }

        return mapIntent
    }

    fun createFallbackFindHospitalsIntent(lat: Double? = null, lng: Double? = null): Intent {
        val url = if (lat != null && lng != null) {
            "https://www.google.com/maps/search/hospitals+near+me/@$lat,$lng,14z"
        } else {
            "https://www.google.com/maps/search/hospitals+near+me"
        }
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
