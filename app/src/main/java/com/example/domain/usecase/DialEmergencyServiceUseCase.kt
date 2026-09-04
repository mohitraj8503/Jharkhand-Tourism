package com.example.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri

class DialEmergencyServiceUseCase {

    fun createDialIntent(phoneNumber: String): Intent {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
        return Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$cleanNumber")
        }
    }

    fun canLaunchDialer(context: Context, intent: Intent): Boolean {
        return intent.resolveActivity(context.packageManager) != null
    }
}
