package com.example.ui.health

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.JharkhandEmergencyContacts
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyActionSheet(
    onDismiss: () -> Unit,
    onCallAmbulance: () -> Unit,
    onCallPolice: () -> Unit,
    onCallFire: () -> Unit,
    onCall112: () -> Unit,
    onShareLocation: () -> Unit,
    onFindNearbyHospital: () -> Unit,
    onOpenEmergencyContacts: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp,
        dragHandle = {
            // Apple-style Grabber
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(36.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD1D1D6))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .testTag("emergency_action_sheet")
        ) {
            // Sheet Header
            Text(
                text = "Emergency Assistance",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Choose what you need. Actions are verified and safe.",
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Apple-style Grouped Container for 6 Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF9F9FB))
            ) {
                // 0. National Emergency 112
                EmergencyActionRow(
                    iconEmoji = "🆘",
                    title = "112 — National Emergency",
                    subtitle = "Unified Police, Fire & Medical helpline",
                    badge = "112",
                    badgeColor = Color(0xFFFF3B30),
                    onClick = onCall112
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 1. Ambulance
                EmergencyActionRow(
                    iconEmoji = "🚑",
                    title = "Ambulance",
                    subtitle = "Dial 108 emergency medical transport",
                    badge = "108",
                    badgeColor = Color(0xFFFF9500),
                    onClick = onCallAmbulance
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 2. Police
                EmergencyActionRow(
                    iconEmoji = "👮",
                    title = "Police",
                    subtitle = "Dial 100 police control & security patrol",
                    badge = "100",
                    badgeColor = Color(0xFF007AFF),
                    onClick = onCallPolice
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 3. Fire
                EmergencyActionRow(
                    iconEmoji = "🔥",
                    title = "Fire & Rescue",
                    subtitle = "Dial 101 fire control & rescue brigade",
                    badge = "101",
                    badgeColor = Color(0xFFFF3B30),
                    onClick = onCallFire
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 4. Share My Location
                EmergencyActionRow(
                    iconEmoji = "📍",
                    title = "Share My Location",
                    subtitle = "Generate GPS map link with coordinates",
                    badge = "GPS",
                    badgeColor = ForestGreen,
                    onClick = onShareLocation
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 5. Find Nearby Hospital
                EmergencyActionRow(
                    iconEmoji = "🏥",
                    title = "Find Nearby Hospital",
                    subtitle = "Search verified medical facilities on Google Maps",
                    badge = "Maps",
                    badgeColor = ForestGreen,
                    onClick = onFindNearbyHospital
                )
                HorizontalDivider(color = Color(0xFFE5E5EA), thickness = 0.5.dp, modifier = Modifier.padding(start = 56.dp))

                // 6. Emergency Contacts
                EmergencyActionRow(
                    iconEmoji = "👥",
                    title = "Emergency Contacts",
                    subtitle = "View or add personal family & friends contacts",
                    badge = "Local",
                    badgeColor = Color(0xFF5856D6),
                    onClick = onOpenEmergencyContacts
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun EmergencyActionRow(
    iconEmoji: String,
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Emoji Icon Box
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(badgeColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = iconEmoji, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeColor.copy(alpha = 0.12f))
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 15.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = Color(0xFFC7C7CC),
            modifier = Modifier.size(14.dp)
        )
    }
}
