package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val TruLightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = Color.White,
    primaryContainer = ForestGreenLight,
    onPrimaryContainer = Color.White,
    secondary = LimeAccent,
    onSecondary = ForestGreenDark,
    secondaryContainer = LimeAccentMuted,
    onSecondaryContainer = ForestGreenDark,
    tertiary = EcoBadgeGreen,
    onTertiary = Color.White,
    background = SurfaceWarm,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceCardSubtle,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed,
    onError = Color.White
)

private val TruDarkColorScheme = darkColorScheme(
    primary = LimeAccent,
    onPrimary = ForestGreenDark,
    primaryContainer = ForestGreen,
    onPrimaryContainer = Color.White,
    secondary = LimeAccent,
    onSecondary = ForestGreenDark,
    background = ForestGreenDark,
    onBackground = SurfaceWarm,
    surface = ForestGreen,
    onSurface = Color.White,
    surfaceVariant = ForestGreenLight,
    onSurfaceVariant = Color(0xFFD0D0D0),
    error = ErrorRed,
    onError = Color.White
)

val TruShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) TruDarkColorScheme else TruLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = TruShapes,
        content = content
    )
}
