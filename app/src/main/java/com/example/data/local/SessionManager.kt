package com.example.data.local

import android.content.Context

class SessionManager(private val context: Context) {
    fun saveUser(email: String, name: String, photoUrl: String?) {
        val prefs = context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("user_email", email)
            putString("user_name", name)
            if (photoUrl != null) putString("user_photoUrl", photoUrl)
            apply()
        }
    }
    
    fun isLoggedIn(): Boolean {
        return context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
            .contains("user_email")
    }
    
    fun logout() {
        context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
    
    fun getUserName(): String {
        return context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
            .getString("user_name", "Paul Steven") ?: "Paul Steven"
    }
    
    fun getUserEmail(): String {
        return context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
            .getString("user_email", "paul.steven@email.com") ?: "paul.steven@email.com"
    }
    
    fun getUserPhotoUrl(): String? {
        return context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
            .getString("user_photoUrl", null)
    }
}
