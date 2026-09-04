package com.example.domain.usecase

import android.content.Intent
import com.example.domain.model.EmergencyContact
import com.example.domain.model.JharkhandEmergencyContacts
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class HealthSafetyUseCasesTest {

    private lateinit var dialUseCase: DialEmergencyServiceUseCase
    private lateinit var shareUseCase: ShareCurrentLocationUseCase
    private lateinit var hospitalUseCase: FindNearbyHospitalUseCase

    @Before
    fun setUp() {
        dialUseCase = DialEmergencyServiceUseCase()
        shareUseCase = ShareCurrentLocationUseCase()
        hospitalUseCase = FindNearbyHospitalUseCase()
    }

    @Test
    fun dialEmergencyService_createsActionDialIntentWithTelUri() {
        val intent = dialUseCase.createDialIntent("112")
        assertNotNull(intent)
        assertEquals(Intent.ACTION_DIAL, intent.action)
        assertEquals("tel:112", intent.dataString)
    }

    @Test
    fun dialEmergencyService_cleansNonNumericCharacters() {
        val intent = dialUseCase.createDialIntent("+91 108")
        assertNotNull(intent)
        assertEquals("tel:+91108", intent.dataString)
    }

    @Test
    fun shareCurrentLocation_generatesCorrectMapsUrl() {
        val url = shareUseCase.generateMapsLink(23.3441, 85.3096)
        assertEquals("https://www.google.com/maps/search/?api=1&query=23.3441,85.3096", url)
    }

    @Test
    fun shareCurrentLocation_formatsGeneralShareMessage() {
        val message = shareUseCase.formatGeneralShareMessage(23.3441, 85.3096)
        assertTrue(message.contains("23.3441,85.3096"))
        assertTrue(message.contains("JharVista"))
    }

    @Test
    fun shareCurrentLocation_formatsContactShareMessage() {
        val message = shareUseCase.formatContactShareMessage("Mom", 23.3441, 85.3096)
        assertTrue(message.contains("23.3441,85.3096"))
        assertTrue(message.contains("Please check on me"))
    }

    @Test
    fun shareCurrentLocation_createsActionSendChooser() {
        val intent = shareUseCase.createGeneralShareIntent(23.3441, 85.3096)
        assertEquals(Intent.ACTION_CHOOSER, intent.action)
    }

    @Test
    fun shareCurrentLocation_createsContactSmsIntent() {
        val intent = shareUseCase.createContactSmsIntent("+919876543210", "Mom", 23.3441, 85.3096)
        assertEquals(Intent.ACTION_SENDTO, intent.action)
        assertEquals("smsto:+919876543210", intent.dataString)
        assertTrue(intent.getStringExtra("sms_body")?.contains("23.3441,85.3096") == true)
    }

    @Test
    fun findNearbyHospital_createsSearchIntentWithCoordinates() {
        val intent = hospitalUseCase.createFindHospitalsIntent(23.3441, 85.3096)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals("geo:23.3441,85.3096?q=hospital+emergency", intent.dataString)
        assertEquals("com.google.android.apps.maps", intent.`package`)
    }

    @Test
    fun findNearbyHospital_createsSearchIntentWithoutCoordinates() {
        val intent = hospitalUseCase.createFindHospitalsIntent(null, null)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals("geo:0,0?q=hospital+emergency", intent.dataString)
    }

    @Test
    fun findNearbyHospital_createsFallbackSearchIntent() {
        val intent = hospitalUseCase.createFallbackFindHospitalsIntent(23.3441, 85.3096)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertTrue(intent.dataString?.contains("google.com/maps") == true)
    }

    @Test
    fun jharkhandEmergencyContacts_containsUnified112AndKeyServices() {
        val services = JharkhandEmergencyContacts.allServices
        val unified112 = services.find { it.number == "112" }
        assertNotNull(unified112)
        assertEquals("112 — National Emergency", unified112?.name)

        val police = services.find { it.number == "100" }
        assertNotNull(police)

        val ambulance = services.find { it.number == "108" }
        assertNotNull(ambulance)

        val fire = services.find { it.number == "101" }
        assertNotNull(fire)
    }

    @Test
    fun emergencyContactModel_instantiatesCorrectly() {
        val contact = EmergencyContact(
            id = 1L,
            name = "Ramesh Kumar",
            phoneNumber = "+91 9876543210",
            relationship = "Family"
        )
        assertEquals(1L, contact.id)
        assertEquals("Ramesh Kumar", contact.name)
        assertEquals("+91 9876543210", contact.phoneNumber)
        assertEquals("Family", contact.relationship)
    }
}
