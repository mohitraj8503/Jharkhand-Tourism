package com.example.ui.health

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.EmergencyContactRepository
import com.example.domain.model.EmergencyContact
import com.example.domain.model.EmergencyServiceItem
import com.example.domain.model.JharkhandEmergencyContacts
import com.example.domain.usecase.AddEmergencyContactUseCase
import com.example.domain.usecase.DeleteEmergencyContactUseCase
import com.example.domain.usecase.DialEmergencyServiceUseCase
import com.example.domain.usecase.FindNearbyHospitalUseCase
import com.example.domain.usecase.GetEmergencyContactsUseCase
import com.example.domain.usecase.ShareCurrentLocationUseCase
import com.example.domain.usecase.UpdateEmergencyContactUseCase
import com.example.domain.usecase.UpdateSafetyChecklistUseCase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class PendingLocationAction {
    SHARE_GENERAL,
    SHARE_CONTACT,
    FIND_HOSPITAL
}

data class HealthSafetyUiState(
    val contacts: List<EmergencyContact> = emptyList(),
    val checkedChecklistIds: Set<String> = emptySet(),
    val isSafetyReady: Boolean = false,
    val showEmergencyActionSheet: Boolean = false,
    val pendingCallNumber: String? = null,
    val pendingCallTitle: String? = null,
    val showCallConfirmation: Boolean = false,
    val showLocationRationale: Boolean = false,
    val showLocationDenied: Boolean = false,
    val pendingAction: PendingLocationAction? = null,
    val targetShareContact: EmergencyContact? = null,
    val isLocating: Boolean = false,
    val userMessage: String? = null
)

class HealthSafetyViewModel(
    private val repository: EmergencyContactRepository,
    private val getContactsUseCase: GetEmergencyContactsUseCase = GetEmergencyContactsUseCase(repository),
    private val addContactUseCase: AddEmergencyContactUseCase = AddEmergencyContactUseCase(repository),
    private val updateContactUseCase: UpdateEmergencyContactUseCase = UpdateEmergencyContactUseCase(repository),
    private val deleteContactUseCase: DeleteEmergencyContactUseCase = DeleteEmergencyContactUseCase(repository),
    private val checklistUseCase: UpdateSafetyChecklistUseCase = UpdateSafetyChecklistUseCase(repository),
    private val dialEmergencyUseCase: DialEmergencyServiceUseCase = DialEmergencyServiceUseCase(),
    private val shareLocationUseCase: ShareCurrentLocationUseCase = ShareCurrentLocationUseCase(),
    private val findNearbyHospitalUseCase: FindNearbyHospitalUseCase = FindNearbyHospitalUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HealthSafetyUiState())
    val uiState: StateFlow<HealthSafetyUiState> = combine(
        _uiState,
        getContactsUseCase(),
        checklistUseCase.getCheckedItemIds()
    ) { currentUiState, contacts, checkedIds ->
        currentUiState.copy(
            contacts = contacts,
            checkedChecklistIds = checkedIds,
            isSafetyReady = contacts.isNotEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HealthSafetyUiState()
    )

    fun onSosTriggered() {
        _uiState.update { it.copy(showEmergencyActionSheet = true) }
    }

    fun dismissActionSheet() {
        _uiState.update { it.copy(showEmergencyActionSheet = false) }
    }

    // Call Confirmation Handling
    fun requestCallService(service: EmergencyServiceItem) {
        _uiState.update {
            it.copy(
                showEmergencyActionSheet = false,
                pendingCallNumber = service.number,
                pendingCallTitle = service.name,
                showCallConfirmation = true
            )
        }
    }

    fun requestCallContact(contact: EmergencyContact) {
        _uiState.update {
            it.copy(
                showEmergencyActionSheet = false,
                pendingCallNumber = contact.phoneNumber,
                pendingCallTitle = "${contact.name} (${contact.relationship})",
                showCallConfirmation = true
            )
        }
    }

    fun dismissCallDialog() {
        _uiState.update {
            it.copy(
                showCallConfirmation = false,
                pendingCallNumber = null,
                pendingCallTitle = null
            )
        }
    }

    fun confirmCall(context: Context) {
        val number = _uiState.value.pendingCallNumber ?: return
        dismissCallDialog()
        val intent = dialEmergencyUseCase.createDialIntent(number)
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            _uiState.update { it.copy(userMessage = "Calling is unavailable on this device.") }
        }
    }

    // Checklist toggling
    fun toggleChecklistItem(itemId: String) {
        val isCurrentlyChecked = uiState.value.checkedChecklistIds.contains(itemId)
        viewModelScope.launch {
            checklistUseCase.toggleItem(itemId, !isCurrentlyChecked)
        }
    }

    fun resetChecklist() {
        viewModelScope.launch {
            checklistUseCase.resetAll()
        }
    }

    // Location features
    fun onShareLocationClicked(context: Context) {
        _uiState.update { it.copy(showEmergencyActionSheet = false) }
        checkAndExecuteLocationAction(context, PendingLocationAction.SHARE_GENERAL)
    }

    fun onShareWithContactClicked(context: Context, contact: EmergencyContact) {
        _uiState.update { it.copy(showEmergencyActionSheet = false, targetShareContact = contact) }
        checkAndExecuteLocationAction(context, PendingLocationAction.SHARE_CONTACT)
    }

    fun onFindNearbyHospitalClicked(context: Context) {
        _uiState.update { it.copy(showEmergencyActionSheet = false) }
        checkAndExecuteLocationAction(context, PendingLocationAction.FIND_HOSPITAL)
    }

    private fun checkAndExecuteLocationAction(context: Context, action: PendingLocationAction) {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            executeLocationAction(context, action)
        } else {
            _uiState.update {
                it.copy(
                    pendingAction = action,
                    showLocationRationale = true
                )
            }
        }
    }

    fun dismissRationale() {
        _uiState.update { it.copy(showLocationRationale = false, pendingAction = null) }
    }

    fun onRationaleProceed() {
        _uiState.update { it.copy(showLocationRationale = false) }
    }

    fun onLocationPermissionResult(isGranted: Boolean, context: Context) {
        val action = _uiState.value.pendingAction
        if (isGranted && action != null) {
            _uiState.update { it.copy(pendingAction = null) }
            executeLocationAction(context, action)
        } else {
            _uiState.update {
                it.copy(
                    showLocationDenied = true
                )
            }
        }
    }

    fun dismissLocationDenied() {
        _uiState.update { it.copy(showLocationDenied = false, pendingAction = null) }
    }

    fun openAppSettings(context: Context) {
        dismissLocationDenied()
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            _uiState.update { it.copy(userMessage = "Unable to open settings") }
        }
    }

    fun openMapsFallback(context: Context) {
        dismissLocationDenied()
        try {
            val intent = findNearbyHospitalUseCase.createFallbackFindHospitalsIntent(null, null).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            _uiState.update { it.copy(userMessage = "No map application found") }
        }
    }

    @SuppressLint("MissingPermission")
    private fun executeLocationAction(context: Context, action: PendingLocationAction) {
        _uiState.update { it.copy(isLocating = true) }
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)

        fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                _uiState.update { it.copy(isLocating = false) }
                if (location != null) {
                    dispatchLocationResult(context, action, location.latitude, location.longitude)
                } else {
                    fusedClient.lastLocation.addOnSuccessListener { lastLoc: Location? ->
                        if (lastLoc != null) {
                            dispatchLocationResult(context, action, lastLoc.latitude, lastLoc.longitude)
                        } else {
                            handleLocationFallback(context, action)
                        }
                    }.addOnFailureListener {
                        handleLocationFallback(context, action)
                    }
                }
            }
            .addOnFailureListener {
                _uiState.update { it.copy(isLocating = false) }
                handleLocationFallback(context, action)
            }
    }

    private fun dispatchLocationResult(context: Context, action: PendingLocationAction, lat: Double, lng: Double) {
        when (action) {
            PendingLocationAction.SHARE_GENERAL -> {
                val intent = shareLocationUseCase.createGeneralShareIntent(lat, lng).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    _uiState.update { it.copy(userMessage = "No compatible sharing app was found.") }
                }
            }
            PendingLocationAction.SHARE_CONTACT -> {
                val contact = _uiState.value.targetShareContact
                if (contact != null) {
                    val intent = shareLocationUseCase.createContactSmsIntent(contact.phoneNumber, contact.name, lat, lng).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // Fallback to general share
                        val fallback = shareLocationUseCase.createGeneralShareIntent(lat, lng).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(fallback)
                    }
                } else {
                    val intent = shareLocationUseCase.createGeneralShareIntent(lat, lng).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                }
            }
            PendingLocationAction.FIND_HOSPITAL -> {
                val mapsIntent = findNearbyHospitalUseCase.createFindHospitalsIntent(lat, lng).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    context.startActivity(mapsIntent)
                } catch (e: Exception) {
                    val fallback = findNearbyHospitalUseCase.createFallbackFindHospitalsIntent(lat, lng).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(fallback)
                }
            }
        }
    }

    private fun handleLocationFallback(context: Context, action: PendingLocationAction) {
        when (action) {
            PendingLocationAction.FIND_HOSPITAL -> {
                val fallback = findNearbyHospitalUseCase.createFallbackFindHospitalsIntent(null, null).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    context.startActivity(fallback)
                } catch (e: Exception) {
                    _uiState.update { it.copy(userMessage = "No map application found") }
                }
            }
            else -> {
                _uiState.update { it.copy(userMessage = "Unable to determine your location. Please ensure GPS is enabled.") }
            }
        }
    }

    // Emergency Contact Operations
    fun saveEmergencyContact(
        id: Long = 0L,
        name: String,
        phone: String,
        relationship: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val result = if (id == 0L) {
                addContactUseCase(name, phone, relationship)
            } else {
                updateContactUseCase(id, name, phone, relationship)
            }

            if (result.isSuccess) {
                _uiState.update { it.copy(userMessage = if (id == 0L) "Emergency contact added." else "Emergency contact updated.") }
                onSuccess()
            } else {
                _uiState.update { it.copy(userMessage = result.exceptionOrNull()?.message ?: "Failed to save contact") }
            }
        }
    }

    fun deleteEmergencyContact(id: Long) {
        viewModelScope.launch {
            val result = deleteContactUseCase(id)
            if (result.isSuccess) {
                _uiState.update { it.copy(userMessage = "Emergency contact removed.") }
            }
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}
