package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SavedPlaceEntity
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceCardSubtle
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DestinationCard(
    place: SavedPlaceEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            ) { onClick() }
            .testTag("destination_card_${place.id}")
            .border(1.dp, SurfaceCardSubtle, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        val context = androidx.compose.ui.platform.LocalContext.current
        val imageRequest = remember(place.imageUrl) {
            coil.request.ImageRequest.Builder(context)
                .data(place.imageUrl)
                .addHeader(
                    "User-Agent",
                    "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                )
                .crossfade(true)
                .crossfade(300)
                .build()
        }

        Column {
            // Scenic Thumbnail / Artwork Container (175dp height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .background(Color(0xFFE5E5EA))
            ) {
                if (place.imageUrl.isNotEmpty()) {
                    coil.compose.SubcomposeAsyncImage(
                        model = imageRequest,
                        contentDescription = place.title,
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Color(0xFFE5E5EA), Color(0xFFF2F2F7), Color(0xFFE5E5EA))
                                        )
                                    )
                            )
                        },
                        error = {
                            DestinationScenicCanvas(title = place.title, location = place.subtitle)
                        }
                    )
                } else {
                    DestinationScenicCanvas(title = place.title, location = place.subtitle)
                }

                // Apple-style subtle gradient overlay: transparent at top, darkening only at bottom for legible white text
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                0.0f to Color.Transparent,
                                0.45f to Color.Transparent,
                                0.8f to Color.Black.copy(alpha = 0.5f),
                                1.0f to Color.Black.copy(alpha = 0.8f)
                            )
                        )
                )

                // Title and Subtitle at bottom left over image
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = place.title.uppercase(),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = place.subtitle,
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 23.sp
                    )
                }

                // Eco Badge overlay (Apple style pill)
                if (place.ecoCertified) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF34C759).copy(alpha = 0.92f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Eco Choice",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            // Card Content Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header row with dots indicator & price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(8.dp).background(LimeAccent, CircleShape))
                        Box(modifier = Modifier.size(8.dp).background(SurfaceCardSubtle, CircleShape))
                        Box(modifier = Modifier.size(8.dp).background(SurfaceCardSubtle, CircleShape))
                    }
                    Text(
                        text = "₹1,200/person",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description text
                Text(
                    text = place.reasonWhy,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Explore Itinerary Button
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text(
                        text = "Explore Itinerary",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun DestinationScenicCanvas(title: String, location: String) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        when {
            title.contains("Deoghar", ignoreCase = true) || location.contains("Deoghar", ignoreCase = true) -> {
                // Deoghar Temple Vibe (Sunset & Temple Silhouette)
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFB74D), Color(0xFFFFCC80), Color(0xFFFFF3E0))
                    )
                )
                // Sun
                drawCircle(
                    color = Color(0xFFFF7043),
                    radius = 24.dp.toPx(),
                    center = Offset(w * 0.75f, h * 0.35f)
                )
                // Temple Silhouette
                val temple = Path().apply {
                    moveTo(w * 0.3f, h)
                    lineTo(w * 0.3f, h * 0.6f)
                    lineTo(w * 0.5f, h * 0.3f)
                    lineTo(w * 0.7f, h * 0.6f)
                    lineTo(w * 0.7f, h)
                    close()
                }
                drawPath(temple, Color(0xFF5D4037))
            }

            title.contains("Netarhat", ignoreCase = true) -> {
                // Netarhat Sunrise & Pine Trees
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF8A65), Color(0xFFFFCC80), Color(0xFFFFF3E0))
                    )
                )
                // Sun rising over hills
                drawCircle(
                    color = Color(0xFFFF5722),
                    radius = 36.dp.toPx(),
                    center = Offset(w * 0.5f, h * 0.6f)
                )
                // Hills
                val hills = Path().apply {
                    moveTo(0f, h * 0.7f)
                    cubicTo(w * 0.3f, h * 0.5f, w * 0.7f, h * 0.8f, w, h * 0.6f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(hills, Color(0xFF2E7D32))
            }

            title.contains("Falls", ignoreCase = true) -> {
                // Waterfall (Hundru / Panch Gagh)
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF81C784), Color(0xFFA5D6A7), Color(0xFFC8E6C9))
                    )
                )
                // Deep Forest canopy
                drawCircle(
                    color = Color(0xFF1B5E20),
                    radius = w * 0.4f,
                    center = Offset(w * 0.2f, h * 0.9f)
                )
                drawCircle(
                    color = Color(0xFF2E7D32),
                    radius = w * 0.35f,
                    center = Offset(w * 0.8f, h * 0.85f)
                )
                // Cascading Waterfall stream
                drawRect(
                    brush = Brush.verticalGradient(listOf(Color(0xFFE0F7FA), Color(0xFF4DD0E1))),
                    topLeft = Offset(w * 0.45f, h * 0.4f),
                    size = Size(w * 0.12f, h * 0.6f)
                )
            }

            else -> {
                // Dalma / Betla - Wild Forest
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF66BB6A), Color(0xFF81C784), Color(0xFFA5D6A7))
                    )
                )
                // Hilly forest terrain
                val peak1 = Path().apply {
                    moveTo(0f, h)
                    lineTo(w * 0.4f, h * 0.4f)
                    lineTo(w * 0.8f, h)
                    close()
                }
                drawPath(peak1, Color(0xFF388E3C))
                val peak2 = Path().apply {
                    moveTo(w * 0.3f, h)
                    lineTo(w * 0.7f, h * 0.35f)
                    lineTo(w, h)
                    close()
                }
                drawPath(peak2, Color(0xFF2E7D32))
                // Sun/moon
                drawCircle(
                    color = Color(0xFFFFF59D),
                    radius = 20.dp.toPx(),
                    center = Offset(w * 0.2f, h * 0.25f)
                )
            }
        }
    }
}
