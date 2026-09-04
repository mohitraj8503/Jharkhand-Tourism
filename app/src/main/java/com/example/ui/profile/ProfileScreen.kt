package com.example.ui.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.local.SessionManager
import com.example.data.seed.JharkhandData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onBack: () -> Unit,
    onNavigateToTrips: () -> Unit = {},
    onNavigateToDestination: (Long) -> Unit = {},
    onNavigateToWallet: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // User Profile State
    var userName by remember { mutableStateOf(sessionManager.getUserName()) }
    var userEmail by remember { mutableStateOf(sessionManager.getUserEmail()) }
    var userPhone by remember { mutableStateOf(sessionManager.getUserPhone()) }
    var userCity by remember { mutableStateOf(sessionManager.getUserCity()) }
    val userPhotoUrl = sessionManager.getUserPhotoUrl()

    // Preferences State
    var selectedLanguage by remember { mutableStateOf(sessionManager.getLanguage()) }
    var notificationsEnabled by remember { mutableStateOf(sessionManager.isNotificationsEnabled()) }
    var ecoModeEnabled by remember { mutableStateOf(sessionManager.isEcoModeEnabled()) }

    // Stats State
    val tripsPlannedCount = remember { sessionManager.getTripsPlannedCount() }
    var savedPlacesCount by remember { mutableIntStateOf(sessionManager.getSavedPlacesCount()) }
    val statesExploredCount = remember { sessionManager.getStatesExploredCount() }

    // Wallet State
    var walletBalance by remember { mutableIntStateOf(2500) }

    // Saved Destinations state (Initialized with 8 authentic Jharkhand locations)
    val savedDestinations = remember {
        mutableStateListOf(
            *JharkhandData.destinations.take(8).toTypedArray()
        )
    }

    // Modal & Sheet visibility states
    var showEditProfileSheet by remember { mutableStateOf(false) }
    var showPersonalInfoSheet by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showLinkedAccountsSheet by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var showNotificationsSheet by remember { mutableStateOf(false) }
    var showEcoModeSheet by remember { mutableStateOf(false) }
    var showSavedPlacesSheet by remember { mutableStateOf(false) }
    var showPaymentMethodsSheet by remember { mutableStateOf(false) }
    var showDownloadedGuidesSheet by remember { mutableStateOf(false) }
    var showStateExploredDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showRateDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showAddPaymentDialog by remember { mutableStateOf(false) }

    val initials = remember(userName) {
        val parts = userName.trim().split("\\s+".toRegex())
        if (parts.size >= 2) {
            "${parts[0].first()}${parts[1].first()}".uppercase()
        } else if (userName.isNotEmpty()) {
            userName.take(2).uppercase()
        } else {
            "GU"
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF2F2F7) // Apple Grouped Background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF0B3D2E), Color(0xFF143F31))
                            )
                        )
                        .statusBarsPadding()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        IconButton(onClick = onBack, modifier = Modifier.offset(x = (-12).dp)) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar (Clickable to edit profile)
                            Box(
                                modifier = Modifier
                                    .size(88.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFC6F432)) // LimeAccent
                                    .clickable { showEditProfileSheet = true },
                                contentAlignment = Alignment.Center
                            ) {
                                if (userPhotoUrl != null) {
                                    AsyncImage(
                                        model = userPhotoUrl,
                                        contentDescription = "Profile Photo",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text(
                                        text = initials,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0B3D2E)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = userName,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = userEmail,
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.75f)
                                )
                                Text(
                                    text = userCity,
                                    fontSize = 11.sp,
                                    color = Color(0xFFA3E635),
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            // Edit Profile Button
                            OutlinedButton(
                                onClick = { showEditProfileSheet = true },
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
                                shape = RoundedCornerShape(16.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text("Edit Profile", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Stats Row (All 3 Cards are Clickable)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                value = tripsPlannedCount.toString(),
                                label = "Trips Planned",
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToTrips() }
                            )
                            StatCard(
                                value = savedPlacesCount.toString(),
                                label = "Saved Places",
                                modifier = Modifier.weight(1f),
                                onClick = { showSavedPlacesSheet = true }
                            )
                            StatCard(
                                value = statesExploredCount.toString(),
                                label = "State Explored",
                                modifier = Modifier.weight(1f),
                                onClick = { showStateExploredDialog = true }
                            )
                        }
                    }
                }
            }

            // 1. ACCOUNT Section
            item {
                SectionHeader("ACCOUNT")
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Default.Person,
                        iconColor = Color(0xFF007AFF),
                        label = "Personal Info",
                        onClick = { showPersonalInfoSheet = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                    SettingsRow(
                        icon = Icons.Default.Lock,
                        iconColor = Color(0xFFFF9500),
                        label = "Change Password",
                        onClick = { showChangePasswordDialog = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                    SettingsRow(
                        icon = Icons.Default.Link,
                        iconColor = Color(0xFF34C759),
                        label = "Linked Accounts",
                        onClick = { showLinkedAccountsSheet = true }
                    )
                }
            }

            // 2. PREFERENCES Section
            item {
                SectionHeader("PREFERENCES")
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Default.Language,
                        iconColor = Color(0xFF007AFF),
                        label = "Language",
                        trailing = {
                            Text(selectedLanguage, fontSize = 15.sp, color = Color(0xFF8E8E93), fontWeight = FontWeight.Medium)
                        },
                        onClick = { showLanguageSheet = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Notifications,
                        iconColor = Color(0xFFFF3B30),
                        label = "Notifications",
                        showChevron = false,
                        trailing = {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = {
                                    notificationsEnabled = it
                                    sessionManager.setNotificationsEnabled(it)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (it) "Notifications turned ON" else "Notifications turned OFF"
                                        )
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF34C759),
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color(0xFFE5E5EA)
                                )
                            )
                        },
                        onClick = { showNotificationsSheet = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Eco,
                        iconColor = Color(0xFF34C759),
                        label = "Eco-Mode",
                        showChevron = false,
                        trailing = {
                            Switch(
                                checked = ecoModeEnabled,
                                onCheckedChange = {
                                    ecoModeEnabled = it
                                    sessionManager.setEcoModeEnabled(it)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (it) "Eco-Mode activated: Carbon footprint optimized" else "Eco-Mode deactivated"
                                        )
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF34C759),
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color(0xFFE5E5EA)
                                )
                            )
                        },
                        onClick = { showEcoModeSheet = true }
                    )
                }
            }

            // 3. TRIPS & DATA Section
            item {
                SectionHeader("TRIPS & DATA")
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Default.Luggage,
                        iconColor = Color(0xFF5856D6),
                        label = "My Trips",
                        trailing = {
                            Text("${tripsPlannedCount} Trips", fontSize = 14.sp, color = Color(0xFF8E8E93))
                        },
                        onClick = { onNavigateToTrips() }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Bookmark,
                        iconColor = Color(0xFFFF9500),
                        label = "Saved Places",
                        trailing = {
                            Text("${savedPlacesCount} Places", fontSize = 14.sp, color = Color(0xFF8E8E93))
                        },
                        onClick = { showSavedPlacesSheet = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.CreditCard,
                        iconColor = Color(0xFF007AFF),
                        label = "Payment Methods",
                        trailing = {
                            Text("₹${walletBalance} Wallet", fontSize = 14.sp, color = Color(0xFF34C759), fontWeight = FontWeight.SemiBold)
                        },
                        onClick = { showPaymentMethodsSheet = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Download,
                        iconColor = Color(0xFF34C759),
                        label = "Downloaded Guides",
                        trailing = {
                            Text("3 Offline", fontSize = 14.sp, color = Color(0xFF8E8E93))
                        },
                        onClick = { showDownloadedGuidesSheet = true }
                    )
                }
            }

            // 4. ABOUT Section
            item {
                SectionHeader("ABOUT")
                SettingsGroup {
                    SettingsRow(
                        icon = Icons.Default.Info,
                        iconColor = Color(0xFF8E8E93),
                        label = "About JharVista",
                        onClick = { showAboutDialog = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.PrivacyTip,
                        iconColor = Color(0xFF8E8E93),
                        label = "Privacy Policy",
                        onClick = { showPrivacyDialog = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Description,
                        iconColor = Color(0xFF8E8E93),
                        label = "Terms of Service",
                        onClick = { showTermsDialog = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)

                    SettingsRow(
                        icon = Icons.Default.Star,
                        iconColor = Color(0xFFFFCC00),
                        label = "Rate JharVista",
                        onClick = { showRateDialog = true }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Project Team & Credits Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "JharVista",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF0B3D2E)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFE8F5E9))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "v1.1",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                        Text(
                            text = "Official Tourism Companion • Govt. of Jharkhand",
                            fontSize = 12.sp,
                            color = Color(0xFF8E8E93),
                            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                        )

                        HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Author: Yash Kumar Binha (OTT)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("YB", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E392A))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Author & Concept",
                                    fontSize = 11.sp,
                                    color = Color(0xFF8E8E93),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Yash Kumar Binha (OTT)",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1C1E)
                                )
                                Text(
                                    text = "yashbinha@gmail.com",
                                    fontSize = 12.sp,
                                    color = Color(0xFF007AFF)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Lead Developer: Mohit Raj
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE3F2FD)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("MR", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1565C0))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Lead Developer",
                                    fontSize = 11.sp,
                                    color = Color(0xFF8E8E93),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Mohit Raj",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1C1E)
                                )
                                Text(
                                    text = "mohitraj8503@gmail.com",
                                    fontSize = 12.sp,
                                    color = Color(0xFF007AFF)
                                )
                            }
                        }
                    }
                }
            }

            // 5. LOG OUT Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable { showLogoutDialog = true }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Log Out",
                            color = Color(0xFFFF3B30),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

    // ==========================================
    // ALL INTERACTIVE BOTTOM SHEETS & DIALOGS
    // ==========================================

    // 1. EDIT PROFILE BOTTOM SHEET
    if (showEditProfileSheet) {
        var editName by remember { mutableStateOf(userName) }
        var editEmail by remember { mutableStateOf(userEmail) }
        var editPhone by remember { mutableStateOf(userPhone) }
        var editCity by remember { mutableStateOf(userCity) }

        ModalBottomSheet(
            onDismissRequest = { showEditProfileSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showEditProfileSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = editName,
                    onValueChange = { editName = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF0B3D2E)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = editEmail,
                    onValueChange = { editEmail = it },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFF0B3D2E)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = editPhone,
                    onValueChange = { editPhone = it },
                    label = { Text("Mobile Phone") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF0B3D2E)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = editCity,
                    onValueChange = { editCity = it },
                    label = { Text("City / Region") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF0B3D2E)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (editName.isNotBlank() && editEmail.isNotBlank()) {
                            userName = editName.trim()
                            userEmail = editEmail.trim()
                            userPhone = editPhone.trim()
                            userCity = editCity.trim()
                            sessionManager.updateUserProfile(userName, userEmail, userPhone, userCity)
                            showEditProfileSheet = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Profile updated successfully!")
                            }
                        } else {
                            Toast.makeText(context, "Name and Email cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Save Profile Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }

    // 2. PERSONAL INFO SHEET
    if (showPersonalInfoSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPersonalInfoSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Personal Information", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showPersonalInfoSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                Text("Verified traveler identity with Govt. of Jharkhand Tourism", fontSize = 12.sp, color = Color(0xFF8E8E93))

                Spacer(modifier = Modifier.height(20.dp))

                InfoItem("Full Name", userName)
                InfoItem("Email Address", userEmail)
                InfoItem("Mobile Phone", userPhone)
                InfoItem("Base District", userCity)
                InfoItem("Tourist Pass ID", "JV-2026-8503")
                InfoItem("KYC Status", "✅ Verified Citizen / Tourist Profile")
                InfoItem("Eco-Explorer Rank", "🌲 Sal Forest Pioneer")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showPersonalInfoSheet = false
                        showEditProfileSheet = true
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Update Information", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    // 3. CHANGE PASSWORD DIALOG
    if (showChangePasswordDialog) {
        var currentPwd by remember { mutableStateOf("") }
        var newPwd by remember { mutableStateOf("") }
        var confirmPwd by remember { mutableStateOf("") }
        var showPwd by remember { mutableStateOf(false) }
        var errorMsg by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = {
                Text("Change Password", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0B3D2E))
            },
            text = {
                Column {
                    if (errorMsg != null) {
                        Text(errorMsg!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                    }
                    OutlinedTextField(
                        value = currentPwd,
                        onValueChange = { currentPwd = it },
                        label = { Text("Current Password") },
                        visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPwd,
                        onValueChange = { newPwd = it },
                        label = { Text("New Password (min 6 chars)") },
                        visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPwd,
                        onValueChange = { confirmPwd = it },
                        label = { Text("Confirm New Password") },
                        visualTransformation = if (showPwd) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showPwd = !showPwd }
                    ) {
                        Icon(
                            if (showPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFF0B3D2E)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (showPwd) "Hide Passwords" else "Show Passwords", fontSize = 12.sp, color = Color(0xFF0B3D2E))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (currentPwd.isBlank() || newPwd.isBlank() || confirmPwd.isBlank()) {
                            errorMsg = "Please fill all fields"
                        } else if (newPwd.length < 6) {
                            errorMsg = "New password must be at least 6 characters"
                        } else if (newPwd != confirmPwd) {
                            errorMsg = "New passwords do not match"
                        } else {
                            sessionManager.setCustomPassword(newPwd)
                            showChangePasswordDialog = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Password updated successfully!")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Update Password", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel", color = Color(0xFF8E8E93))
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 4. LINKED ACCOUNTS SHEET
    if (showLinkedAccountsSheet) {
        var googleLinked by remember { mutableStateOf(true) }
        var digiLockerLinked by remember { mutableStateOf(true) }
        var appleLinked by remember { mutableStateOf(false) }
        var whatsAppLinked by remember { mutableStateOf(true) }

        ModalBottomSheet(
            onDismissRequest = { showLinkedAccountsSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Linked Accounts", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showLinkedAccountsSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                Text("Manage authenticated identity providers for fast check-in and passes", fontSize = 12.sp, color = Color(0xFF8E8E93))

                Spacer(modifier = Modifier.height(20.dp))

                LinkedAccountItem(
                    title = "Google Account",
                    subtitle = userEmail,
                    isLinked = googleLinked,
                    onToggle = { googleLinked = !googleLinked }
                )
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                LinkedAccountItem(
                    title = "DigiLocker / Aadhaar ID",
                    subtitle = "Instant entry pass at Betla & Netarhat",
                    isLinked = digiLockerLinked,
                    onToggle = { digiLockerLinked = !digiLockerLinked }
                )
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                LinkedAccountItem(
                    title = "Apple ID",
                    subtitle = "Sync itineraries on iOS devices",
                    isLinked = appleLinked,
                    onToggle = { appleLinked = !appleLinked }
                )
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                LinkedAccountItem(
                    title = "WhatsApp Concierge",
                    subtitle = userPhone,
                    isLinked = whatsAppLinked,
                    onToggle = { whatsAppLinked = !whatsAppLinked }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        showLinkedAccountsSheet = false
                        coroutineScope.launch { snackbarHostState.showSnackbar("Linked accounts preferences saved") }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Done", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    // 5. LANGUAGE SELECTION SHEET
    if (showLanguageSheet) {
        val languages = listOf(
            Triple("English", "English", "Default language"),
            Triple("हिंदी", "Hindi", "राजभाषा • Tourism Audio"),
            Triple("संथाली (Ol Chiki)", "Santhali", "संथाली भाषा • Tribal Heritage"),
            Triple("मुंडारी (Mundari)", "Mundari", "छोटानागपुर मुंडारी बोली"),
            Triple("हो (Ho)", "Ho", "कोल्हान प्रमंडल बोली"),
            Triple("कुडुख (Kurukh)", "Kurukh", "उरांव समुदाय भाषा")
        )

        ModalBottomSheet(
            onDismissRequest = { showLanguageSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Select App Language", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showLanguageSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                languages.forEach { (name, id, desc) ->
                    val isSelected = selectedLanguage.startsWith(id) || selectedLanguage.startsWith(name)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color(0xFFE8F5E9) else Color.Transparent)
                            .clickable {
                                selectedLanguage = name
                                sessionManager.setLanguage(name)
                                showLanguageSheet = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Language switched to $name")
                                }
                            }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1C1C1E))
                            Text(desc, fontSize = 12.sp, color = Color(0xFF8E8E93))
                        }
                        if (isSelected) {
                            Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color(0xFF2E7D32))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

    // 6. NOTIFICATIONS PREFERENCES SHEET
    if (showNotificationsSheet) {
        var tripAlerts by remember { mutableStateOf(true) }
        var evChargingAlerts by remember { mutableStateOf(true) }
        var ecoWeatherAlerts by remember { mutableStateOf(true) }
        var festivalDrops by remember { mutableStateOf(false) }

        ModalBottomSheet(
            onDismissRequest = { showNotificationsSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notification Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showNotificationsSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                NotificationOptionRow("Trip Itinerary & Flight Reminders", "Live status, gate changes and EV pickup alerts", tripAlerts) {
                    tripAlerts = it
                }
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                NotificationOptionRow("EV & Highway Charging Alerts", "Fast charging station availability along NH 33 & Patratu", evChargingAlerts) {
                    evChargingAlerts = it
                }
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                NotificationOptionRow("Forest Trail & Weather Advisories", "Betla safaris, waterfall monsoons & sunset timings", ecoWeatherAlerts) {
                    ecoWeatherAlerts = it
                }
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                NotificationOptionRow("Cultural Festivals & Tribal Souvenirs", "Sarhul, Karma Puja & Paitkar painting exhibitions", festivalDrops) {
                    festivalDrops = it
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        showNotificationsSheet = false
                        coroutineScope.launch { snackbarHostState.showSnackbar("Notification preferences saved") }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Save Preferences", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    // 7. ECO-MODE & REGENERATIVE TOURISM SHEET
    if (showEcoModeSheet) {
        ModalBottomSheet(
            onDismissRequest = { showEcoModeSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Eco-Mode & Regenerative Travel", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showEcoModeSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Eco, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Lifetime Eco Impact", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), fontSize = 16.sp)
                                Text("Your contribution to preserving Jharkhand's forests", fontSize = 12.sp, color = Color(0xFF388E3C))
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("64.2 kg", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF1B5E20))
                                Text("CO₂ Avoided", fontSize = 11.sp, color = Color(0xFF4CAF50))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("28.4 L", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF1B5E20))
                                Text("Petrol Saved", fontSize = 11.sp, color = Color(0xFF4CAF50))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("420 km", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF1B5E20))
                                Text("EV Clean Ride", fontSize = 11.sp, color = Color(0xFF4CAF50))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("How Eco-Mode Works:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1C1C1E))
                Spacer(modifier = Modifier.height(8.dp))
                BulletPoint("Optimizes navigation routes for maximum EV charging efficiency")
                BulletPoint("Prioritizes certified green hotels & tribal community homestays")
                BulletPoint("Alerts travelers of plastic-free sacred groves and waterfall zones")

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF2F2F7))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Active Eco-Mode", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1C1C1E))
                        Text("Enable zero-carbon travel perks", fontSize = 12.sp, color = Color(0xFF8E8E93))
                    }
                    Switch(
                        checked = ecoModeEnabled,
                        onCheckedChange = {
                            ecoModeEnabled = it
                            sessionManager.setEcoModeEnabled(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF34C759)
                        )
                    )
                }
            }
        }
    }

    // 8. SAVED PLACES SHEET
    if (showSavedPlacesSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSavedPlacesSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Saved Places", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                        Text("${savedDestinations.size} bookmarked Jharkhand wonders", fontSize = 12.sp, color = Color(0xFF8E8E93))
                    }
                    IconButton(onClick = { showSavedPlacesSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (savedDestinations.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No saved places yet. Bookmark destinations from Where To Go!", color = Color(0xFF8E8E93), fontSize = 14.sp)
                    }
                } else {
                    LazyColumn(modifier = Modifier.heightIn(max = 420.dp)) {
                        items(savedDestinations) { dest ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9FB)),
                                border = BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = dest.imageUrl,
                                        contentDescription = dest.name,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(dest.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1C1C1E))
                                        Text(dest.city, fontSize = 12.sp, color = Color(0xFF8E8E93))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("⭐ ${dest.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                                            Text(" • ${dest.type}", fontSize = 11.sp, color = Color(0xFF6B7280))
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            savedDestinations.remove(dest)
                                            savedPlacesCount = savedDestinations.size
                                            sessionManager.setSavedPlacesCount(savedDestinations.size)
                                        }
                                    ) {
                                        Icon(Icons.Default.BookmarkRemove, contentDescription = "Remove", tint = Color(0xFFFF3B30))
                                    }
                                    Button(
                                        onClick = {
                                            showSavedPlacesSheet = false
                                            onNavigateToDestination(dest.id)
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E)),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text("View", fontSize = 11.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 9. PAYMENT METHODS & WALLET SHEET
    if (showPaymentMethodsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPaymentMethodsSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Payment Methods", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showPaymentMethodsSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Green Wallet Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0B3D2E)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("JharVista Green Wallet", color = Color(0xFFA3E635), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Fast Checkout", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("₹${walletBalance}", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    walletBalance += 500
                                    coroutineScope.launch { snackbarHostState.showSnackbar("Added ₹500 to JharVista Wallet!") }
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6F432)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("+₹500", color = Color(0xFF0B3D2E), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Button(
                                onClick = {
                                    walletBalance += 1000
                                    coroutineScope.launch { snackbarHostState.showSnackbar("Added ₹1,000 to JharVista Wallet!") }
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6F432)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("+₹1,000", color = Color(0xFF0B3D2E), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text("Saved Options", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1C1C1E))
                Spacer(modifier = Modifier.height(8.dp))

                PaymentRow("UPI: guest@okhdfcbank", "Default instant refund UPI", Icons.Default.AccountBalance)
                HorizontalDivider(color = Color(0xFFF2F2F7), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                PaymentRow("Visa Card •••• 4242", "Expires 08/28", Icons.Default.CreditCard)

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { showAddPaymentDialog = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF0B3D2E))
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF0B3D2E))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+ Add New UPI / Card", color = Color(0xFF0B3D2E), fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    // 10. ADD PAYMENT METHOD DIALOG
    if (showAddPaymentDialog) {
        var upiId by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddPaymentDialog = false },
            title = { Text("Add UPI ID", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0B3D2E)) },
            text = {
                Column {
                    Text("Enter your Google Pay, PhonePe, or BHIM UPI ID:", fontSize = 13.sp, color = Color(0xFF6B7280))
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = upiId,
                        onValueChange = { upiId = it },
                        placeholder = { Text("example@okaxis") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (upiId.contains("@")) {
                            showAddPaymentDialog = false
                            coroutineScope.launch { snackbarHostState.showSnackbar("UPI ID $upiId verified and added!") }
                        } else {
                            Toast.makeText(context, "Enter a valid UPI ID with '@'", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Verify & Save", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddPaymentDialog = false }) { Text("Cancel", color = Color(0xFF8E8E93)) }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // 11. DOWNLOADED GUIDES SHEET
    if (showDownloadedGuidesSheet) {
        var guidesDownloaded by remember { mutableStateOf(listOf("Netarhat Sunrise & Valley Guide", "Betla Wildlife Safari Map", "Baidyanath Dham Pilgrim Companion")) }
        var fourthGuideDownloaded by remember { mutableStateOf(false) }

        ModalBottomSheet(
            onDismissRequest = { showDownloadedGuidesSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 36.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Offline Travel Guides", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
                    IconButton(onClick = { showDownloadedGuidesSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                Text("Access audio guides, trail maps & tourist spots without mobile network", fontSize = 12.sp, color = Color(0xFF8E8E93))

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F7)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Offline Storage Used", fontSize = 12.sp, color = Color(0xFF8E8E93))
                            Text(if (fourthGuideDownloaded) "119 MB / 1.2 GB" else "87 MB / 1.2 GB", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1C1C1E))
                        }
                        TextButton(
                            onClick = {
                                fourthGuideDownloaded = false
                                coroutineScope.launch { snackbarHostState.showSnackbar("Offline guide cache cleared") }
                            }
                        ) {
                            Text("Clear Cache", color = Color(0xFFFF3B30), fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                DownloadedGuideItem("Netarhat Sunrise & Valley Guide", "24 MB • High Fidelity Audio", true) {}
                DownloadedGuideItem("Betla Wildlife Safari Map", "45 MB • Offline GPS Trail", true) {}
                DownloadedGuideItem("Baidyanath Dham Pilgrim Companion", "18 MB • Temple Audio Guide", true) {}
                DownloadedGuideItem(
                    title = "Hundru & Jonha Waterfalls Audio Guide",
                    size = "32 MB • Bilingual Audio",
                    isDownloaded = fourthGuideDownloaded,
                    onDownload = {
                        fourthGuideDownloaded = true
                        coroutineScope.launch { snackbarHostState.showSnackbar("Downloaded Hundru Waterfall Guide!") }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        fourthGuideDownloaded = true
                        showDownloadedGuidesSheet = false
                        coroutineScope.launch { snackbarHostState.showSnackbar("All offline guides synced successfully!") }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Sync All Guides Offline", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    // 12. STATE EXPLORED DIALOG
    if (showStateExploredDialog) {
        AlertDialog(
            onDismissRequest = { showStateExploredDialog = false },
            title = {
                Text("Jharkhand Explorer Status", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0B3D2E))
            },
            text = {
                Column {
                    Text(
                        "You are currently exploring Jharkhand — The Land of Forests and Waterfalls.",
                        fontSize = 14.sp,
                        color = Color(0xFF3C3C43)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("🏆 Sal Forest Eco-Explorer Badge", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• 12 / 24 Districts Visited", fontSize = 12.sp, color = Color(0xFF2E7D32))
                            Text("• 420 km EV Clean Travelled", fontSize = 12.sp, color = Color(0xFF2E7D32))
                            Text("• 3 Tribal Heritage Sites Supported", fontSize = 12.sp, color = Color(0xFF2E7D32))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showStateExploredDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Keep Exploring", color = Color.White)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 13. PRIVACY POLICY DIALOG
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0B3D2E)) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "JharVista is committed to safeguarding your privacy under the Digital Personal Data Protection Act (DPDPA 2023).\n\n" +
                                "1. Data Collection: We collect account profile information, location coordinates (strictly during active navigation), and EV booking preferences.\n\n" +
                                "2. Location Privacy: Precise GPS data is only accessed with your permission to calculate battery range and recommend nearest charging hubs.\n\n" +
                                "3. No Third-Party Selling: Your personal data, itinerary records, and identity proofs are never sold or rented to commercial third parties.\n\n" +
                                "4. Security: All stored user credentials and payment tokens are securely encrypted using standard AES-256 protocols.",
                        fontSize = 13.sp,
                        color = Color(0xFF3C3C43),
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("I Understand", fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 14. TERMS OF SERVICE DIALOG
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text("Terms of Service", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0B3D2E)) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "By accessing and using JharVista, you agree to the following terms:\n\n" +
                                "1. Regenerative Tourism: Travelers must respect sacred tribal sarnas, avoid single-use plastics in national parks, and follow Forest Department safety guidelines.\n\n" +
                                "2. EV Rentals: Drivers must hold a valid Indian driving licence, adhere to highway speed limits, and return electric vehicles with at least 15% state-of-charge.\n\n" +
                                "3. Bookings & Cancellations: Booking cancellations requested 24 hours prior to travel are eligible for a 100% refund into JharVista Wallet.\n\n" +
                                "4. Audio Guides: Audio recordings, tribal music, and guide commentaries are copyrighted by the Department of Tourism, Govt. of Jharkhand.",
                        fontSize = 13.sp,
                        color = Color(0xFF3C3C43),
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Accept & Close", fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 15. RATE JHARVISTA DIALOG
    if (showRateDialog) {
        var ratingStars by remember { mutableIntStateOf(5) }
        var reviewText by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showRateDialog = false },
            title = {
                Text("Rate JharVista", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0B3D2E))
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("How has your experience exploring Jharkhand been?", fontSize = 13.sp, color = Color(0xFF6B7280), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= ratingStars) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Star $i",
                                tint = if (i <= ratingStars) Color(0xFFFFCC00) else Color(0xFFC7C7CC),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { ratingStars = i }
                                    .padding(horizontal = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        placeholder = { Text("Write your feedback or review (optional)...") },
                        modifier = Modifier.fillMaxWidth().height(90.dp),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRateDialog = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Thank you for rating JharVista $ratingStars stars! ⭐")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Submit Review", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRateDialog = false }) { Text("Later", color = Color(0xFF8E8E93)) }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 16. LOGOUT CONFIRMATION DIALOG
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log Out of JharVista?", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1C1C1E)) },
            text = {
                Text(
                    "You will need to sign in again to access your saved trips, offline guides, and wallet balance.",
                    fontSize = 14.sp,
                    color = Color(0xFF3C3C43)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30))
                ) {
                    Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color(0xFF8E8E93))
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(18.dp)
        )
    }

    // 17. ABOUT JHARVISTA DIALOG
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = {
                Text(
                    text = "About JharVista",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF0B3D2E)
                )
            },
            text = {
                Column {
                    Text(
                        text = "JharVista is the official AI-powered travel companion for the State of Jharkhand, promoting sustainable discovery, EV mobility, and tribal cultural preservation.",
                        fontSize = 14.sp,
                        color = Color(0xFF3C3C43),
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Author & Concept:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8E8E93)
                    )
                    Text(
                        text = "Yash Kumar Binha (OTT)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1C1E)
                    )
                    Text(
                        text = "yashbinha@gmail.com",
                        fontSize = 13.sp,
                        color = Color(0xFF007AFF)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Lead Developer:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8E8E93)
                    )
                    Text(
                        text = "Mohit Raj",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1C1E)
                    )
                    Text(
                        text = "mohitraj8503@gmail.com",
                        fontSize = 13.sp,
                        color = Color(0xFF007AFF)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Done", fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

// ==========================================
// REUSABLE HELPER COMPOSABLES
// ==========================================

@Composable
fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
            Text(label, fontSize = 11.sp, color = Color(0xFF6B7280))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF6B7280),
        modifier = Modifier.padding(start = 36.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    showChevron: Boolean = true,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(28.dp).clip(RoundedCornerShape(6.dp)).background(iconColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, fontSize = 16.sp, color = Color(0xFF1A1A1A), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        if (trailing != null) {
            trailing()
        } else if (showChevron) {
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF9CA3AF)
            )
        }
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(label, fontSize = 11.sp, color = Color(0xFF8E8E93), fontWeight = FontWeight.Medium)
        Text(value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1C1E))
    }
}

@Composable
private fun LinkedAccountItem(
    title: String,
    subtitle: String,
    isLinked: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1C1C1E))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF8E8E93))
        }
        Button(
            onClick = onToggle,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLinked) Color(0xFFE8F5E9) else Color(0xFF0B3D2E)
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (isLinked) "Connected" else "Connect",
                color = if (isLinked) Color(0xFF2E7D32) else Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NotificationOptionRow(
    title: String,
    description: String,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1C1C1E))
            Text(description, fontSize = 11.sp, color = Color(0xFF8E8E93))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Switch(
            checked = enabled,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF34C759)
            )
        )
    }
}

@Composable
private fun PaymentRow(title: String, subtitle: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF9F9FB))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF0B3D2E), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1C1C1E))
            Text(subtitle, fontSize = 11.sp, color = Color(0xFF8E8E93))
        }
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34C759), modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun DownloadedGuideItem(
    title: String,
    size: String,
    isDownloaded: Boolean,
    onDownload: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF1C1C1E))
            Text(size, fontSize = 11.sp, color = Color(0xFF8E8E93))
        }
        if (isDownloaded) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF34C759), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Saved", fontSize = 12.sp, color = Color(0xFF34C759), fontWeight = FontWeight.Bold)
            }
        } else {
            Button(
                onClick = onDownload,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E)),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Download", fontSize = 11.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
        Text("• ", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
        Text(text, fontSize = 13.sp, color = Color(0xFF3C3C43), lineHeight = 18.sp)
    }
}
