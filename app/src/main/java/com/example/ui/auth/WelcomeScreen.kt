package com.example.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500) // 2.5 second splash
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B3D2E), // dark forest green
                        Color(0xFF132B22), // deep pine
                        Color(0xFF0A1813)  // rich dark
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            // Brand Logo
            Image(
                painter = painterResource(id = R.drawable.brand_logo),
                contentDescription = "JharVista Brand Logo",
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(26.dp))
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Welcoming "Johar," in bold center
            Text(
                text = "Johar,",
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline: Discover the soul of Jharkhand
            Text(
                text = "Discover the soul of Jharkhand",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFA3E635), // Vibrant Fresh Lime/Green
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tourism in Jharkhand",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.65f),
                textAlign = TextAlign.Center
            )
        }

        // Bottom text
        Text(
            text = "Powered by JharVista AI",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

