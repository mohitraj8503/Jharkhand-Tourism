package com.example.ui.auth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(onNavigateNext: () -> Unit) {
    val scale = remember { Animatable(0.92f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        delay(2500) // 2.5 second splash
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF07271C), // Rich Deep Emerald
                        Color(0xFF0F3325), // Forest Canopy
                        Color(0xFF071811)  // Obsidian Pine
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Ambient glow background circle
        Box(
            modifier = Modifier
                .size(260.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF34D399).copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Center Welcoming Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            // Brand Logo with glassmorphic border & soft elevation
            Box(
                modifier = Modifier
                    .size(116.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
                    .background(Color.White.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.brand_logo),
                    contentDescription = "JharVista Brand Logo",
                    modifier = Modifier
                        .size(108.dp)
                        .clip(RoundedCornerShape(24.dp))
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Welcoming "Johar," in bold center
            Text(
                text = "Johar,",
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline: Discover the soul of Jharkhand
            Text(
                text = "Discover the soul of Jharkhand",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFA3E635), // Vibrant Fresh Lime/Green
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                letterSpacing = 0.2.sp
            )
        }

        // Bottom Powered Branding
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFA3E635))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Powered by JharVista AI",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.65f),
                letterSpacing = 0.5.sp
            )
        }
    }
}
