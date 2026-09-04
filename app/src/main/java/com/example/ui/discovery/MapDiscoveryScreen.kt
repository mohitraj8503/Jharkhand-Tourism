package com.example.ui.discovery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SavedPlaceEntity
import com.example.data.repository.TruRepository
import com.example.ui.theme.EcoBadgeBg
import com.example.ui.theme.EcoBadgeGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.ForestGreenLight
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceWarm
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

data class MapPin(
    val id: String,
    val title: String,
    val location: String,
    val xNorm: Float, // 0..1
    val yNorm: Float, // 0..1
    val ecoCertified: Boolean,
    val crowdLevel: String,
    val carbonKg: Double,
    val alert: String? = null,
    val imageUrl: String = ""
)

@Composable
fun MapDiscoveryScreen(
    repository: TruRepository,
    onNavigateBack: () -> Unit,
    onPlanTripForLocation: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val pins = remember {
        listOf(
            MapPin("p1", "Netarhat Sunrise Point", "Latehar, Jharkhand", 0.28f, 0.38f, true, "Low", 4.2, null, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg"),
            MapPin("p2", "Dalma Wildlife Sanctuary", "Jamshedpur, Jharkhand", 0.55f, 0.44f, true, "Moderate", 6.6, null, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Peaceful_Neighbours.jpg/1280px-Peaceful_Neighbours.jpg"),
            MapPin("p3", "Deoghar Baidyanath Temple", "Deoghar, Jharkhand", 0.72f, 0.62f, false, "High", 18.5, "High footfall during Shravan mela! Plan ahead.", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg"),
            MapPin("p4", "Panch Gagh Falls", "Khunti, Jharkhand", 0.64f, 0.48f, true, "Low", 1.8, null, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg"),
            MapPin("p5", "Hundru Falls", "Ranchi, Jharkhand", 0.67f, 0.46f, false, "High", 12.4, "High footfall predicted! Try Panch Gagh.", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg"),
            MapPin("p6", "Betla National Park", "Latehar, Jharkhand", 0.22f, 0.30f, true, "Low", 5.5, null, "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg"),
            MapPin("p7", "Patratu Valley & Dam", "Ramgarh, Jharkhand", 0.50f, 0.40f, true, "Moderate", 5.1, null, "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg")
        )
    }

    var selectedPin by remember { mutableStateOf<MapPin?>(pins[0]) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.8f, 3f)
        offset += offsetChange
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8ECE9))
    ) {
        // Map Canvas with Roads, Water, Parks & Coordinates
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = transformState)
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        // find closest pin
                        val w = size.width
                        val h = size.height
                        val clicked = pins.minByOrNull { pin ->
                            val px = pin.xNorm * w
                            val py = pin.yNorm * h
                            (tapOffset.x - px) * (tapOffset.x - px) + (tapOffset.y - py) * (tapOffset.y - py)
                        }
                        if (clicked != null) {
                            selectedPin = clicked
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Water Bodies (Rivers / Lakes)
                val river = Path().apply {
                    moveTo(0f, h * 0.4f)
                    cubicTo(w * 0.3f, h * 0.35f, w * 0.6f, h * 0.55f, w, h * 0.45f)
                    lineTo(w, h * 0.52f)
                    cubicTo(w * 0.6f, h * 0.62f, w * 0.3f, h * 0.42f, 0f, h * 0.48f)
                    close()
                }
                drawPath(river, Color(0xFFB2DFDB))

                // Green Park Reserve polygons
                drawCircle(Color(0xFFC8E6C9), radius = 100.dp.toPx(), center = Offset(w * 0.3f, h * 0.35f))
                drawCircle(Color(0xFFD7CCC8), radius = 80.dp.toPx(), center = Offset(w * 0.55f, h * 0.45f))
                drawCircle(Color(0xFFA5D6A7), radius = 120.dp.toPx(), center = Offset(w * 0.68f, h * 0.55f))

                // Road Network lines
                drawLine(Color(0xFFCFD8DC), Offset(0f, h * 0.25f), Offset(w, h * 0.75f), strokeWidth = 8f)
                drawLine(Color(0xFFCFD8DC), Offset(w * 0.2f, 0f), Offset(w * 0.8f, h), strokeWidth = 8f)
                drawLine(Color.White, Offset(0f, h * 0.25f), Offset(w, h * 0.75f), strokeWidth = 4f)
                drawLine(Color.White, Offset(w * 0.2f, 0f), Offset(w * 0.8f, h), strokeWidth = 4f)
            }

            // Pins rendering
            pins.forEach { pin ->
                val isSelected = selectedPin?.id == pin.id
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .graphicsLayer {
                            translationX = pin.xNorm * 1000f - 24.dp.toPx()
                            translationY = pin.yNorm * 1800f - 48.dp.toPx()
                        }
                        .clickable { selectedPin = pin }
                        .testTag("map_pin_${pin.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Pin Head
                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 44.dp else 36.dp)
                                .clip(CircleShape)
                                .background(if (pin.crowdLevel == "High") Color(0xFFD32F2F) else ForestGreen)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) LimeAccent else ForestGreenDark),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (pin.crowdLevel == "High") Icons.Default.Warning else Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = if (isSelected) ForestGreenDark else LimeAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        // Pin Stem
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(8.dp)
                                .background(ForestGreenDark)
                        )
                    }
                }
            }
        }

        // Top Search & Controls Overlay
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SurfaceCard)
                        .testTag("map_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Search Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search places or coordinates...", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = ForestGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("map_search_field"),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SurfaceCard,
                        unfocusedContainerColor = SurfaceCard,
                        focusedIndicatorColor = ForestGreen,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf("All", "Eco-Certified", "Low Footfall", "Cultural")) { filter ->
                    val isSel = selectedFilter == filter
                    val bg by animateColorAsState(if (isSel) ForestGreen else SurfaceCard, label = "mf_bg")
                    val txtColor by animateColorAsState(if (isSel) LimeAccent else TextSecondary, label = "mf_txt")

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(bg)
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter,
                            color = txtColor,
                            fontSize = 12.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Floating Info Card for Selected Pin
        selectedPin?.let { pin ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .testTag("pin_info_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (pin.imageUrl.isNotEmpty()) {
                            val context = androidx.compose.ui.platform.LocalContext.current
                            val thumbReq = remember(pin.imageUrl) {
                                coil.request.ImageRequest.Builder(context)
                                    .data(pin.imageUrl)
                                    .addHeader(
                                        "User-Agent",
                                        "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                                    )
                                    .crossfade(true)
                                    .build()
                            }
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFFE5E5EA))
                            ) {
                                AsyncImage(
                                    model = thumbReq,
                                    contentDescription = pin.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = pin.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = (-0.3).sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = pin.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                            if (pin.ecoCertified) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(EcoBadgeBg)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Eco,
                                            contentDescription = null,
                                            tint = EcoBadgeGreen,
                                            modifier = Modifier.size(11.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "${pin.carbonKg} kg CO₂",
                                            color = EcoBadgeGreen,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (pin.alert != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFFF3E0))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "⚠️ ${pin.alert}",
                                fontSize = 11.sp,
                                color = Color(0xFFE65100),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onPlanTripForLocation(pin.location) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("plan_trip_here_cta"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LimeAccent,
                            contentColor = ForestGreenDark
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Navigation,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Plan Trip to ${pin.location.substringBefore(",")}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
