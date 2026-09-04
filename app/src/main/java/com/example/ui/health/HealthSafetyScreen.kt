package com.example.ui.health

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.FirstAidGuideData
import com.example.domain.model.FirstAidTopic
import com.example.domain.model.JharkhandEmergencyContacts
import com.example.domain.usecase.DefaultSafetyChecklist
import com.example.domain.usecase.SafetyChecklistItem
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import kotlinx.coroutines.launch

@Composable
fun HealthSafetyScreen(
    viewModel: HealthSafetyViewModel,
    onNavigateToContacts: () -> Unit,
    onNavigateToAddContact: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // User Message Snackbar
    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        viewModel.onLocationPermissionResult(granted, context)
    }

    // Call Confirmation Dialog
    if (uiState.showCallConfirmation && uiState.pendingCallNumber != null) {
        CallConfirmationDialog(
            title = uiState.pendingCallTitle ?: "Emergency Service",
            phoneNumber = uiState.pendingCallNumber ?: "",
            onConfirm = { viewModel.confirmCall(context) },
            onDismiss = { viewModel.dismissCallDialog() }
        )
    }

    // Location Permission Rationale Dialog
    if (uiState.showLocationRationale) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissRationale() },
            title = {
                Text(
                    text = "Location Access",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "JharVista uses your location only to help you share your position or find nearby emergency facilities.",
                    fontSize = 14.sp,
                    color = TextMuted,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onRationaleProceed()
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Continue", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissRationale() }) {
                    Text("Not Now", color = TextMuted)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }

    // Location Denied Dialog
    if (uiState.showLocationDenied) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissLocationDenied() },
            title = {
                Text(
                    text = "Location Access is Off",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "Location permission is required to share your precise coordinates with emergency contacts or search local hospitals.",
                    fontSize = 14.sp,
                    color = TextMuted,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.openMapsFallback(context) },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Use Google Maps", fontSize = 12.sp)
                    }
                    Button(
                        onClick = { viewModel.openAppSettings(context) },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Open Settings", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissLocationDenied() }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }

    // Emergency Action Bottom Sheet
    if (uiState.showEmergencyActionSheet) {
        EmergencyActionSheet(
            onDismiss = { viewModel.dismissActionSheet() },
            onCall112 = { viewModel.requestCallService(JharkhandEmergencyContacts.nationalEmergency) },
            onCallAmbulance = { viewModel.requestCallService(JharkhandEmergencyContacts.ambulance) },
            onCallPolice = { viewModel.requestCallService(JharkhandEmergencyContacts.police) },
            onCallFire = { viewModel.requestCallService(JharkhandEmergencyContacts.fire) },
            onShareLocation = { viewModel.onShareLocationClicked(context) },
            onFindNearbyHospital = { viewModel.onFindNearbyHospitalClicked(context) },
            onOpenEmergencyContacts = {
                viewModel.dismissActionSheet()
                onNavigateToContacts()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F7))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .testTag("health_safety_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                // Custom Apple-style Header
                Text(
                    text = "Health & Safety",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Stay prepared while exploring Jharkhand.",
                    fontSize = 14.sp,
                    color = TextMuted
                )
            }

            // 1. Safety Status Card
            item {
                SafetyStatusCard(
                    isReady = uiState.isSafetyReady,
                    contactsCount = uiState.contacts.size,
                    onAddContact = onNavigateToAddContact,
                    onManageContacts = onNavigateToContacts
                )
            }

            // 2. Quick Emergency Contact Action Card (if user has contacts)
            if (uiState.contacts.isNotEmpty()) {
                item {
                    val primaryContact = uiState.contacts.first()
                    QuickEmergencyContactCard(
                        contact = primaryContact,
                        onCall = { viewModel.requestCallContact(primaryContact) },
                        onShareLocation = { viewModel.onShareWithContactClicked(context, primaryContact) },
                        onViewAll = onNavigateToContacts
                    )
                }
            }

            // 3. Emergency SOS Hero Card (Press & Hold for 2 seconds)
            item {
                EmergencySosHeroCard(
                    onSosActivated = { viewModel.onSosTriggered() }
                )
            }

            // 4. Quick Emergency Services Grid (112, 108, 100, 101, Hospital, Share)
            item {
                EmergencyServicesGrid(
                    onDial112 = { viewModel.requestCallService(JharkhandEmergencyContacts.nationalEmergency) },
                    onDialAmbulance = { viewModel.requestCallService(JharkhandEmergencyContacts.ambulance) },
                    onDialPolice = { viewModel.requestCallService(JharkhandEmergencyContacts.police) },
                    onDialFire = { viewModel.requestCallService(JharkhandEmergencyContacts.fire) },
                    onFindHospital = { viewModel.onFindNearbyHospitalClicked(context) },
                    onShareGps = { viewModel.onShareLocationClicked(context) }
                )
            }

            // 5. Basic First Aid Section (Expandable cards)
            item {
                FirstAidSection(
                    onCall112 = { viewModel.requestCallService(JharkhandEmergencyContacts.nationalEmergency) }
                )
            }

            // 6. Before You Explore (Travel Safety Checklist)
            item {
                TravelSafetyChecklistSection(
                    items = DefaultSafetyChecklist.items,
                    checkedIds = uiState.checkedChecklistIds,
                    onToggle = { viewModel.toggleChecklistItem(it) },
                    onReset = { viewModel.resetChecklist() }
                )
            }

            // 7. General Safety Guidelines for Jharkhand
            item {
                JharkhandTerrainSafetyCard()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }
}

// -------------------------------------------------------------------------
// 1. SAFETY STATUS CARD
// -------------------------------------------------------------------------
@Composable
private fun SafetyStatusCard(
    isReady: Boolean,
    contactsCount: Int,
    onAddContact: () -> Unit,
    onManageContacts: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(if (isReady) Color(0xFF34C759).copy(alpha = 0.15f) else Color(0xFFFF9500).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isReady) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (isReady) Color(0xFF34C759) else Color(0xFFFF9500),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (isReady) "Safety Ready" else "Add an Emergency Contact",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isReady) "$contactsCount trusted emergency contact(s) saved" else "Tap below to add family or hotel contact",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isReady) {
                TextButton(onClick = onManageContacts) {
                    Text("Manage", color = ForestGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onAddContact,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 2. QUICK EMERGENCY CONTACT CARD
// -------------------------------------------------------------------------
@Composable
private fun QuickEmergencyContactCard(
    contact: com.example.domain.model.EmergencyContact,
    onCall: () -> Unit,
    onShareLocation: () -> Unit,
    onViewAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "YOUR EMERGENCY CONTACT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "View All →",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ForestGreen,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onViewAll() }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(ForestGreen.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👤", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = contact.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFF2F2F7))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = contact.relationship,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = ForestGreen
                            )
                        }
                    }
                    Text(
                        text = contact.phoneNumber,
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onCall,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onShareLocation,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, ForestGreen),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ForestGreen)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share GPS", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 3. EMERGENCY SOS HERO CARD (PRESS & HOLD ~2 SECONDS)
// -------------------------------------------------------------------------
@Composable
private fun EmergencySosHeroCard(
    onSosActivated: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Need Help?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "Emergency assistance is always one tap away.",
                fontSize = 13.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Large SOS Circular Button with 2s Press-and-Hold Progress
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {
                // Background Track
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(118.dp),
                    color = Color(0xFFFF3B30).copy(alpha = 0.15f),
                    strokeWidth = 6.dp,
                    strokeCap = StrokeCap.Round
                )

                // Active Press Progress Indicator
                CircularProgressIndicator(
                    progress = { progress.value },
                    modifier = Modifier.size(118.dp),
                    color = Color(0xFFFF3B30),
                    strokeWidth = 6.dp,
                    strokeCap = StrokeCap.Round
                )

                // Center SOS Button
                Box(
                    modifier = Modifier
                        .size(98.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFFF453A),
                                    Color(0xFFD70015)
                                )
                            )
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    val job = coroutineScope.launch {
                                        progress.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                                        )
                                        if (progress.value >= 1f) {
                                            onSosActivated()
                                        }
                                    }
                                    tryAwaitRelease()
                                    isPressed = false
                                    job.cancel()
                                    coroutineScope.launch {
                                        progress.snapTo(0f)
                                    }
                                }
                            )
                        }
                        .testTag("sos_emergency_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "SOS",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = if (isPressed) "HOLD..." else "HOLD 2s",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isPressed) "Keep holding for emergency assistance..." else "Press and hold for emergency options",
                fontSize = 12.sp,
                fontWeight = if (isPressed) FontWeight.Bold else FontWeight.Medium,
                color = if (isPressed) Color(0xFFFF3B30) else TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

// -------------------------------------------------------------------------
// 4. EMERGENCY SERVICES GRID
// -------------------------------------------------------------------------
@Composable
private fun EmergencyServicesGrid(
    onDial112: () -> Unit,
    onDialAmbulance: () -> Unit,
    onDialPolice: () -> Unit,
    onDialFire: () -> Unit,
    onFindHospital: () -> Unit,
    onShareGps: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "DIRECT HELPLINES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "🆘",
                title = "112 Emergency",
                subtitle = "Unified helpline",
                badge = "112",
                badgeColor = Color(0xFFFF3B30),
                onClick = onDial112
            )
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "🚑",
                title = "Ambulance",
                subtitle = "Medical response",
                badge = "108",
                badgeColor = Color(0xFFFF9500),
                onClick = onDialAmbulance
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "👮",
                title = "Police Control",
                subtitle = "Security & patrol",
                badge = "100",
                badgeColor = Color(0xFF007AFF),
                onClick = onDialPolice
            )
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "🔥",
                title = "Fire Service",
                subtitle = "Fire & rescue",
                badge = "101",
                badgeColor = Color(0xFFFF3B30),
                onClick = onDialFire
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "🏥",
                title = "Find Hospital",
                subtitle = "Google Maps radar",
                badge = "Radar",
                badgeColor = ForestGreen,
                onClick = onFindHospital
            )
            EmergencyQuickCard(
                modifier = Modifier.weight(1f),
                emoji = "📍",
                title = "Share GPS",
                subtitle = "Send coordinates",
                badge = "Share",
                badgeColor = ForestGreen,
                onClick = onShareGps
            )
        }
    }
}

@Composable
private fun EmergencyQuickCard(
    modifier: Modifier = Modifier,
    emoji: String,
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = emoji, fontSize = 24.sp)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(alpha = 0.12f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}

// -------------------------------------------------------------------------
// 5. BASIC FIRST AID SECTION
// -------------------------------------------------------------------------
@Composable
private fun FirstAidSection(
    onCall112: () -> Unit
) {
    var expandedTopicId by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF34C759).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🩹", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Basic First Aid",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Travel emergency guidance for outdoor safety",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Topics list
            FirstAidGuideData.topics.forEachIndexed { index, topic ->
                val isExpanded = expandedTopicId == topic.id
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isExpanded) Color(0xFFF9F9FB) else Color.Transparent)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            expandedTopicId = if (isExpanded) null else topic.id
                        }
                        .padding(vertical = 10.dp, horizontal = if (isExpanded) 10.dp else 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = topic.iconEmoji, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = topic.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(modifier = Modifier.padding(top = 10.dp, start = 28.dp)) {
                            Text(
                                text = topic.summary,
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            topic.steps.forEachIndexed { sIdx, step ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "${sIdx + 1}. ",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ForestGreen
                                    )
                                    Text(
                                        text = step,
                                        fontSize = 12.sp,
                                        color = TextPrimary,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            topic.warning?.let { warn ->
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFFF3B30).copy(alpha = 0.08f))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = "⚠️ $warn",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFFF3B30),
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                if (index < FirstAidGuideData.topics.size - 1) {
                    HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Medical Disclaimer Box + Call 112
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF2F2F7))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "This section is general travel safety guidance only. JharVista does not provide medical diagnostic services. For serious symptoms, seek professional medical help immediately.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onCall112,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30))
                    ) {
                        Text("Call 112 Emergency", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 6. BEFORE YOU EXPLORE (TRAVEL SAFETY CHECKLIST)
// -------------------------------------------------------------------------
@Composable
private fun TravelSafetyChecklistSection(
    items: List<SafetyChecklistItem>,
    checkedIds: Set<String>,
    onToggle: (String) -> Unit,
    onReset: () -> Unit
) {
    val completedCount = items.count { checkedIds.contains(it.id) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Before You Explore",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "$completedCount of ${items.size} completed",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (completedCount == items.size) Color(0xFF34C759) else ForestGreen
                    )
                }

                if (completedCount > 0) {
                    TextButton(onClick = onReset) {
                        Text("Reset", color = TextMuted, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEachIndexed { index, item ->
                val isChecked = checkedIds.contains(item.id)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onToggle(item.id) }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Checkbox
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isChecked) ForestGreen else Color(0xFFF2F2F7))
                            .border(1.dp, if (isChecked) ForestGreen else Color(0xFFD1D1D6), RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isChecked) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            fontSize = 13.sp,
                            fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isChecked) ForestGreen else TextPrimary
                        )
                        Text(
                            text = item.description,
                            fontSize = 11.sp,
                            color = TextMuted,
                            lineHeight = 15.sp
                        )
                    }
                }

                if (index < items.size - 1) {
                    HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 0.5.dp, modifier = Modifier.padding(start = 36.dp))
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// 7. JHARKHAND TERRAIN SAFETY ADVISORY
// -------------------------------------------------------------------------
@Composable
private fun JharkhandTerrainSafetyCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Jharkhand Terrain Safety",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))

            TerrainSafetyRow(
                emoji = "🌊",
                title = "Waterfalls (Hundru, Jonha, Dassam)",
                advice = "River rocks are mossy and treacherous. Never bypass safety railings or wade into cascading plunge pools."
            )
            Spacer(modifier = Modifier.height(8.dp))
            TerrainSafetyRow(
                emoji = "🐅",
                title = "Wildlife Sanctuaries (Betla, Dalma)",
                advice = "Remain inside authorized safari vehicles. Avoid feeding wild animals, playing loud music, or hiking unguided."
            )
            Spacer(modifier = Modifier.height(8.dp))
            TerrainSafetyRow(
                emoji = "⛰️",
                title = "Hills & Valleys (Netarhat, Parasnath)",
                advice = "Stay on marked pilgrimage & forest paths. Carry water, begin descents before dusk, and check forecast."
            )
        }
    }
}

@Composable
private fun TerrainSafetyRow(
    emoji: String,
    title: String,
    advice: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF9F9FB))
            .padding(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = advice,
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 15.sp
            )
        }
    }
}
