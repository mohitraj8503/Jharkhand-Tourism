package com.example.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(private val context: Context) {
    private val prefs by lazy { context.getSharedPreferences("jharvista", Context.MODE_PRIVATE) }

    private val _userPhotoUrlFlow = MutableStateFlow(prefs.getString("user_photoUrl", null))
    val userPhotoUrlFlow: StateFlow<String?> = _userPhotoUrlFlow.asStateFlow()

    private val _userNameFlow = MutableStateFlow(prefs.getString("user_name", "Guest User") ?: "Guest User")
    val userNameFlow: StateFlow<String> = _userNameFlow.asStateFlow()

    fun saveUser(email: String, name: String, photoUrl: String?) {
        prefs.edit().apply {
            putString("user_email", email)
            putString("user_name", name)
            if (photoUrl != null) putString("user_photoUrl", photoUrl)
            else remove("user_photoUrl")
            apply()
        }
        _userNameFlow.value = name
        _userPhotoUrlFlow.value = photoUrl
    }

    fun updateUserProfile(name: String, email: String, phone: String, city: String) {
        prefs.edit().apply {
            putString("user_name", name)
            putString("user_email", email)
            putString("user_phone", phone)
            putString("user_city", city)
            apply()
        }
        _userNameFlow.value = name
    }

    fun setUserPhotoUrl(url: String?) {
        prefs.edit().apply {
            if (url != null) putString("user_photoUrl", url)
            else remove("user_photoUrl")
            apply()
        }
        _userPhotoUrlFlow.value = url
    }

    fun saveProfileImage(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val profileDir = File(context.filesDir, "profile_photos")
            if (!profileDir.exists()) profileDir.mkdirs()
            val fileName = "profile_avatar_${System.currentTimeMillis()}.jpg"
            val destFile = File(profileDir, fileName)
            destFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            profileDir.listFiles()?.forEach { file ->
                if (file.name != fileName && file.name.startsWith("profile_avatar_")) {
                    file.delete()
                }
            }
            destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun isLoggedIn(): Boolean {
        return prefs.contains("user_email")
    }
    
    fun logout() {
        prefs.edit().clear().apply()
        _userNameFlow.value = "Guest User"
        _userPhotoUrlFlow.value = null
    }
    
    fun getUserName(): String {
        return prefs.getString("user_name", "Guest User") ?: "Guest User"
    }
    
    fun getUserEmail(): String {
        return prefs.getString("user_email", "guest@jharvista.local") ?: "guest@jharvista.local"
    }
    
    fun getUserPhotoUrl(): String? {
        return prefs.getString("user_photoUrl", null)
    }

    fun getUserPhone(): String {
        return prefs.getString("user_phone", "+91 98765 43210") ?: "+91 98765 43210"
    }

    fun getUserCity(): String {
        return prefs.getString("user_city", "Ranchi, Jharkhand") ?: "Ranchi, Jharkhand"
    }

    fun getLanguage(): String {
        return prefs.getString("user_language", "English") ?: "English"
    }

    fun setLanguage(lang: String) {
        prefs.edit().putString("user_language", lang).apply()
    }

    fun isNotificationsEnabled(): Boolean {
        return prefs.getBoolean("notifications_enabled", true)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun isEcoModeEnabled(): Boolean {
        return prefs.getBoolean("eco_mode_enabled", true)
    }

    fun setEcoModeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("eco_mode_enabled", enabled).apply()
    }

    fun setSavedPlacesCount(count: Int) {
        prefs.edit().putInt("saved_places_count", count).apply()
    }

    fun getSavedPlacesCount(): Int {
        return prefs.getInt("saved_places_count", 8)
    }

    fun getTripsPlannedCount(): Int {
        return prefs.getInt("trips_planned_count", 12)
    }

    fun getStatesExploredCount(): Int {
        return prefs.getInt("states_explored_count", 1)
    }

    fun setCustomPassword(pwd: String) {
        prefs.edit().putString("custom_password", pwd).apply()
    }

    fun getCustomPassword(): String {
        return prefs.getString("custom_password", "jharvista123") ?: "jharvista123"
    }
}

