package com.example.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.SessionManager

@Composable
fun ProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    val userName = sessionManager.getUserName()
    val userEmail = sessionManager.getUserEmail()
    val userPhotoUrl = sessionManager.getUserPhotoUrl()
    
    val initials = userName.split(" ").let { parts ->
        if (parts.size >= 2) {
            "${parts[0].first()}${parts[1].first()}".uppercase()
        } else if (userName.isNotEmpty()) {
            userName.take(2).uppercase()
        } else {
            "JV"
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F7)) // Apple Grouped Bg
    ) {
        // Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF0B3D2E), Color(0xFF1A4A3A))
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
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC6F432)), // LimeAccent
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
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0B3D2E)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(20.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(userName, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(userEmail, fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))
                        }
                        
                        TextButton(
                            onClick = { /* Edit Profile */ },
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Edit Profile", color = Color.White, fontSize = 12.sp)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard("12", "Trips Planned", Modifier.weight(1f))
                        StatCard("8", "Saved Places", Modifier.weight(1f))
                        StatCard("1", "State Explored", Modifier.weight(1f))
                    }
                }
            }
        }
        
        // Sections
        item {
            SectionHeader("ACCOUNT")
            SettingsGroup {
                SettingsRow(Icons.Default.Person, Color(0xFF007AFF), "Personal Info") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Lock, Color(0xFFFF9500), "Change Password") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Link, Color(0xFF34C759), "Linked Accounts") {}
            }
        }
        
        item {
            SectionHeader("PREFERENCES")
            SettingsGroup {
                SettingsRow(Icons.Default.Language, Color(0xFF007AFF), "Language") {
                    Text("English", fontSize = 16.sp, color = Color(0xFF9CA3AF))
                }
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                
                var notifications by remember { mutableStateOf(true) }
                SettingsRow(Icons.Default.Notifications, Color(0xFFFF3B30), "Notifications", showChevron = false) {
                    Switch(
                        checked = notifications,
                        onCheckedChange = { notifications = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF34C759),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFFE5E5EA)
                        )
                    )
                }
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                
                var ecoMode by remember { mutableStateOf(true) }
                SettingsRow(Icons.Default.Eco, Color(0xFF34C759), "Eco-Mode", showChevron = false) {
                    Switch(
                        checked = ecoMode,
                        onCheckedChange = { ecoMode = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF34C759),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFFE5E5EA)
                        )
                    )
                }
            }
        }
        
        item {
            SectionHeader("TRIPS & DATA")
            SettingsGroup {
                SettingsRow(Icons.Default.Luggage, Color(0xFF5856D6), "My Trips") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Bookmark, Color(0xFFFF9500), "Saved Places") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.CreditCard, Color(0xFF007AFF), "Payment Methods") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Download, Color(0xFF34C759), "Downloaded Guides") {}
            }
        }
        
        item {
            SectionHeader("ABOUT")
            SettingsGroup {
                SettingsRow(Icons.Default.Info, Color(0xFF8E8E93), "About JharVista") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.PrivacyTip, Color(0xFF8E8E93), "Privacy Policy") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Description, Color(0xFF8E8E93), "Terms of Service") {}
                Divider(modifier = Modifier.padding(start = 56.dp), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                SettingsRow(Icons.Default.Star, Color(0xFFFFCC00), "Rate JharVista") {}
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.brand_logo),
                        contentDescription = "JharVista Brand Logo",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "JharVista",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF0B3D2E)
                        )
                        Text(
                            text = "Official Tourism Companion • Govt. of Jharkhand",
                            fontSize = 12.sp,
                            color = Color(0xFF8E8E93)
                        )
                        Text(
                            text = "Regenerative Tourism Edition v2.4",
                            fontSize = 11.sp,
                            color = Color(0xFF34C759),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { onLogout() }
            ) {
                Text(
                    text = "Log Out",
                    color = Color(0xFFFF3B30),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
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
        color = Color(0xFF6B7280),
        modifier = Modifier.padding(start = 36.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
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
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
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
        Text(label, fontSize = 16.sp, color = Color(0xFF1A1A1A))
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
