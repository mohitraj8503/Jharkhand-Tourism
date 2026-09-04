package com.example.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onNavigateToSignup: () -> Unit,
    onContinueAsGuest: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F7)) // Apple Bg
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Official Brand Logo
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.brand_logo),
            contentDescription = "JharVista Brand Logo",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(18.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Logo text
        Text("JharVista", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0B3D2E))
        Text("Discover the soul of Jharkhand", fontSize = 14.sp, color = Color(0xFF6B7280))

        Spacer(modifier = Modifier.height(32.dp))

        // Form card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color(0xFFFF3B30),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        errorMessage = null 
                    },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0B3D2E),
                        unfocusedBorderColor = Color(0xFFE5E5EA),
                        unfocusedContainerColor = Color(0xFFF2F2F7),
                        focusedContainerColor = Color(0xFFF2F2F7)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = null 
                    },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0B3D2E),
                        unfocusedBorderColor = Color(0xFFE5E5EA),
                        unfocusedContainerColor = Color(0xFFF2F2F7),
                        focusedContainerColor = Color(0xFFF2F2F7)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Forgot password
                Text(
                    "Forgot password?",
                    fontSize = 13.sp,
                    color = Color(0xFF0B3D2E),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth().clickable { }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Login button
                Button(
                    onClick = { 
                        if (email.contains("@") && email.contains(".") && password.length >= 6) {
                            onLoginSuccess(email)
                        } else {
                            errorMessage = "Invalid email or password (min 6 chars)"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B3D2E))
                ) {
                    Text("Log In", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
                Spacer(modifier = Modifier.height(20.dp))

                // Divider
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                    Text("  or  ", fontSize = 13.sp, color = Color(0xFF9CA3AF))
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE5E5EA), thickness = 0.5.dp)
                }
                Spacer(modifier = Modifier.height(20.dp))

                // Google Sign-In
                OutlinedButton(
                    onClick = { /* Google Sign-In */ },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E5EA))
                ) {
                    Text("G   Continue with Google", fontSize = 16.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Signup link
                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(SpanStyle(color = Color(0xFF0B3D2E), fontWeight = FontWeight.Bold)) {
                            append("Sign Up")
                        }
                    },
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().clickable { onNavigateToSignup() }
                )
                Spacer(modifier = Modifier.height(24.dp))
                
                // Continue as Guest link
                Text(
                    text = "Continue as Guest",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().clickable { onContinueAsGuest() }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Terms
        Text(
            "By continuing, you agree to JharVista's Terms of Service and Privacy Policy",
            fontSize = 11.sp,
            color = Color(0xFF9CA3AF),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
