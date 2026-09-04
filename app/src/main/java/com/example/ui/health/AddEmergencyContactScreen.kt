package com.example.ui.health

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEmergencyContactScreen(
    contactId: Long? = null,
    viewModel: HealthSafetyViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val existingContact = remember(contactId, uiState.contacts) {
        if (contactId != null && contactId > 0) {
            uiState.contacts.firstOrNull { it.id == contactId }
        } else null
    }

    var name by remember(existingContact) { mutableStateOf(existingContact?.name ?: "") }
    var phoneNumber by remember(existingContact) { mutableStateOf(existingContact?.phoneNumber ?: "") }
    var relationship by remember(existingContact) { mutableStateOf(existingContact?.relationship ?: "Family") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    val presetRelationships = listOf("Family", "Friend", "Hotel", "Tour Guide", "Doctor", "Work")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F7))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .testTag("add_emergency_contact_screen")
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Custom Apple-style Navigation Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onNavigateBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = if (existingContact != null) "Edit Emergency Contact" else "Add Emergency Contact",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Form Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            // Full Name
            Text(
                text = "FULL NAME",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (nameError != null) nameError = null
                },
                placeholder = { Text("e.g. Ramesh Kumar", color = TextMuted, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(20.dp))
                },
                isError = nameError != null,
                supportingText = nameError?.let { { Text(it, color = Color(0xFFFF3B30), fontSize = 11.sp) } },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("contact_name_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ForestGreen,
                    unfocusedBorderColor = Color(0xFFE5E5EA),
                    focusedContainerColor = Color(0xFFF9F9FB),
                    unfocusedContainerColor = Color(0xFFF9F9FB)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number
            Text(
                text = "PHONE NUMBER",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    if (phoneError != null) phoneError = null
                },
                placeholder = { Text("e.g. +91 98765 43210", color = TextMuted, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(20.dp))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneError != null,
                supportingText = phoneError?.let { { Text(it, color = Color(0xFFFF3B30), fontSize = 11.sp) } },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("contact_phone_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ForestGreen,
                    unfocusedBorderColor = Color(0xFFE5E5EA),
                    focusedContainerColor = Color(0xFFF9F9FB),
                    unfocusedContainerColor = Color(0xFFF9F9FB)
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Relationship
            Text(
                text = "RELATIONSHIP",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presetRelationships.forEach { option ->
                    val isSelected = relationship.equals(option, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ForestGreen else Color(0xFFF2F2F7))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { relationship = option }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = option,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button (52dp, 14dp radius)
        Button(
            onClick = {
                var hasError = false
                if (name.trim().isEmpty()) {
                    nameError = "Name cannot be empty"
                    hasError = true
                }
                if (phoneNumber.trim().isEmpty()) {
                    phoneError = "Phone number cannot be empty"
                    hasError = true
                } else if (phoneNumber.filter { it.isDigit() }.length < 7) {
                    phoneError = "Enter a valid phone number (at least 7 digits)"
                    hasError = true
                }

                if (!hasError) {
                    viewModel.saveEmergencyContact(
                        id = existingContact?.id ?: 0L,
                        name = name.trim(),
                        phone = phoneNumber.trim(),
                        relationship = relationship.trim(),
                        onSuccess = onNavigateBack
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("save_emergency_contact_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ForestGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = if (existingContact != null) "Update Contact" else "Save Contact",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
