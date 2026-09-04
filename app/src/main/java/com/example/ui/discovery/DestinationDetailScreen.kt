package com.example.ui.discovery

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.Destination
import com.example.data.repository.TruRepository
import com.example.ui.theme.AppleBg
import com.example.ui.theme.EcoBadgeGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DestinationDetailScreen(
    destinationId: Long,
    repository: TruRepository,
    onNavigateBack: () -> Unit,
    onNavigateToBooking: (String) -> Unit,
    onNavigateToAudioGuide: () -> Unit,
    onNavigateToDestination: (Long) -> Unit,
    onNavigateToRentals: () -> Unit = {}
) {
    val destination = remember(destinationId) {
        repository.getDestinationById(destinationId) ?: repository.getDestinations().first()
    }
    val allDestinations = remember { repository.getDestinations() }
    val nearbyPlaces = remember(destination.id) {
        allDestinations.filter { it.id != destination.id }.take(5)
    }

    val context = LocalContext.current
    val heroRequest = remember(destination.imageUrl) {
        ImageRequest.Builder(context)
            .data(destination.imageUrl)
            .addHeader(
                "User-Agent",
                "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
            )
            .crossfade(true)
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppleBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image Banner with Overlaid Back Button & Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            AsyncImage(
                model = heroRequest,
                contentDescription = destination.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Black.copy(alpha = 0.45f),
                            0.3f to Color.Transparent,
                            0.6f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = 0.85f)
                        )
                    )
            )

            // Floating Circular Back Button (Apple style, no ripple)
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 8.dp)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onNavigateBack() }
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Title & Location overlaid on hero
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = destination.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = (-0.5).sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = LimeAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${destination.city} • ${destination.type}",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Content Details Card (Apple Style, 0dp elevation, hairline border)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Badges Row: Rating, Eco Choice, Crowd Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rating Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppleBg)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${destination.rating}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = " / 5.0",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (destination.ecoCertified) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(EcoBadgeGreen)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Eco,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Eco Choice",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Crowd Level
                        val crowdText = when (destination.crowdLevel.lowercase()) {
                            "high" -> "Busy"
                            "moderate", "medium" -> "Moderate footfall"
                            else -> "Low footfall"
                        }
                        val crowdBg = when (destination.crowdLevel.lowercase()) {
                            "high" -> Color(0xFFFFF3E0)
                            "moderate", "medium" -> Color(0xFFE8F5E9)
                            else -> Color(0xFFE3F2FD)
                        }
                        val crowdColor = when (destination.crowdLevel.lowercase()) {
                            "high" -> Color(0xFFE65100)
                            "moderate", "medium" -> ForestGreen
                            else -> Color(0xFF1565C0)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(crowdBg)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = crowdText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = crowdColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text(
                    text = "About",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = destination.description,
                    fontSize = 15.sp,
                    color = TextSecondary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Quick Info Grid (Best Time, Entry Fee, Timings)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickInfoBox(
                        icon = Icons.Default.CalendarMonth,
                        title = "Best Time",
                        value = destination.bestTime,
                        modifier = Modifier.weight(1f)
                    )
                    QuickInfoBox(
                        icon = Icons.Default.ConfirmationNumber,
                        title = "Entry Fee",
                        value = destination.entryFee,
                        modifier = Modifier.weight(1f)
                    )
                    QuickInfoBox(
                        icon = Icons.Default.AccessTime,
                        title = "Timings",
                        value = destination.timings,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Responsible Tourism Callout if High Footfall
                if (destination.crowdLevel.equals("high", ignoreCase = true) && destination.alternativeSuggestion != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF8E1))
                            .border(0.5.dp, Color(0xFFFFE082), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFE65100),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "High Footfall Alert",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFFE65100)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Heavy visitor traffic anticipated during weekends. ${destination.predictedFootfallAlert ?: ""}",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 17.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Alternative: Try ${destination.alternativeSuggestion} — Low footfall",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Destination Safety Tips Card (Requirement 18)
        val isHighCrowd = destination.crowdLevel.equals("high", ignoreCase = true)
        val isWaterfall = destination.name.contains("fall", ignoreCase = true) || destination.type.contains("fall", ignoreCase = true) || destination.description.contains("waterfall", ignoreCase = true)
        val isWildlife = destination.name.contains("national park", ignoreCase = true) || destination.type.contains("wildlife", ignoreCase = true) || destination.name.contains("sanctuary", ignoreCase = true)
        val isHills = destination.name.contains("hill", ignoreCase = true) || destination.name.contains("ghat", ignoreCase = true) || destination.type.contains("hill", ignoreCase = true) || destination.name.contains("netarhat", ignoreCase = true)

        if (isHighCrowd || isWaterfall || isWildlife || isHills) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.HealthAndSafety,
                        contentDescription = null,
                        tint = ForestGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Safety Tips",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (isHighCrowd) {
                    Text(
                        text = "• High visitor volume expected. Stay within marked viewing areas and keep belongings secure.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                if (isWaterfall) {
                    Text(
                        text = "• Rocks are slippery near water cascades. Never step beyond designated safety railings or swim in restricted plunge pools.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                if (isWildlife) {
                    Text(
                        text = "• Maintain a safe distance from animals. Stay inside your safari vehicle at all times and follow official guide instructions.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                if (isHills) {
                    Text(
                        text = "• Stay on marked trails. Hill slopes become misty and slippery during morning and monsoon conditions.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Travel Sustainably (Section 16: Destination Detail Integration)
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ForestGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = LimeAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Travel Sustainably",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Rent an EV for this journey",
                            fontSize = 12.sp,
                            color = ForestGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(EcoBadgeGreen.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Best low-carbon option",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoBadgeGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recommended EV Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppleBg)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tata Nexon EV Max",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "312 km range • Eco Score 92/100",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                    Text(
                        text = "₹2,500/day",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen
                    )
                }

                Button(
                    onClick = onNavigateToRentals,
                    modifier = Modifier.height(38.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ForestGreen,
                        contentColor = LimeAccent
                    )
                ) {
                    Text(
                        text = "View EVs",
                        color = LimeAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons Row (Get Directions, Add to Trip, Play Audio Guide)
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Get Directions Button (Google Maps Intent)
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:${destination.lat},${destination.lng}?q=${destination.lat},${destination.lng}(${Uri.encode(destination.name)})")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                        setPackage("com.google.android.apps.maps")
                    }
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        // Fallback to general geo intent or browser
                        context.startActivity(Intent(Intent.ACTION_VIEW, gmmIntentUri))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Icon(Icons.Default.Directions, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Get Directions", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Add to Trip Button
                Button(
                    onClick = { onNavigateToBooking(destination.name) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LimeAccent,
                        contentColor = Color(0xFF06241B)
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Icon(Icons.Default.Luggage, contentDescription = null, tint = Color(0xFF06241B), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add to Trip", color = Color(0xFF06241B), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                // Play Audio Guide Button
                OutlinedButton(
                    onClick = onNavigateToAudioGuide,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ForestGreen)
                ) {
                    Icon(Icons.Default.Headphones, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Audio Guide", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = ForestGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Nearby Places Carousel
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nearby Places",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(nearbyPlaces) { nearby ->
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(14.dp))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onNavigateToDestination(nearby.id) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column {
                            val nearbyReq = remember(nearby.imageUrl) {
                                ImageRequest.Builder(context)
                                    .data(nearby.imageUrl)
                                    .addHeader(
                                        "User-Agent",
                                        "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                                    )
                                    .crossfade(true)
                                    .build()
                            }
                            AsyncImage(
                                model = nearbyReq,
                                contentDescription = nearby.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                            )
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = nearby.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    maxLines = 1
                                )
                                Text(
                                    text = nearby.city,
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun QuickInfoBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AppleBg)
            .padding(10.dp)
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                color = TextMuted,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1
            )
        }
    }
}
