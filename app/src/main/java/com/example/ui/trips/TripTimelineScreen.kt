package com.example.ui.trips

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ItineraryEventEntity
import com.example.data.repository.TruRepository
import com.example.ui.theme.EcoBadgeBg
import com.example.ui.theme.EcoBadgeGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceWarm
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun TripTimelineScreen(
    tripId: Long,
    repository: TruRepository,
    onNavigateBack: () -> Unit,
    onNavigateToRentals: () -> Unit = {},
    onNavigateToHealthSafety: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val itineraryEvents by repository.getItineraryForTrip(tripId).collectAsState(initial = emptyList())
    val filteredEvents = if (selectedCategoryFilter == "All") {
        itineraryEvents
    } else {
        itineraryEvents.filter { it.category.equals(selectedCategoryFilter, ignoreCase = true) }
    }

    val totalCarbon = itineraryEvents.sumOf { it.carbonKg }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard)
                    .testTag("timeline_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "My Trip Timeline",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = "Saved Offline",
                        tint = ForestGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Saved Offline • Day 1 Itinerary",
                        style = MaterialTheme.typography.bodySmall,
                        color = ForestGreen
                    )
                }
            }

            // Email Itinerary Badge
            IconButton(
                onClick = {
                    // In a real app this would call shareItinerary(context, trip)
                },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LimeAccent)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Itinerary",
                    tint = ForestGreenDark
                )
            }
        }

        // Chronological Timeline Events & Bento Header
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 24.dp)
        ) {
            // Carbon Footprint Overview Bento Bar
            item(key = "carbon_footprint_card") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = ForestGreen)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(LimeAccent),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = ForestGreenDark,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Estimated Daily Footprint",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "${String.format("%.1f", totalCarbon)} kg CO₂",
                                    color = LimeAccent,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Net-Zero Goal",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Category Filter Pills: All | Transport | Dining | Stay | Activities
            item(key = "category_filter_pills") {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listOf("All", "Transport", "Dining", "Stay")) { cat ->
                        val isSel = selectedCategoryFilter == cat
                        val bg by animateColorAsState(if (isSel) LimeAccent else SurfaceCard, label = "cat_bg")
                        val txtColor by animateColorAsState(if (isSel) ForestGreenDark else TextSecondary, label = "cat_txt")

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(bg)
                                .clickable { selectedCategoryFilter = cat }
                                .padding(horizontal = 16.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = cat,
                                color = txtColor,
                                fontSize = 12.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Chronological Timeline Events
            items(filteredEvents, key = { it.id }) { event ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    TimelineEventCard(
                        event = event,
                        onEcoSwap = {
                            coroutineScope.launch {
                                repository.swapEventToEco(
                                    id = event.id,
                                    newTitle = "Solar EV Shuttle & Green Bike Transit",
                                    newCarbon = 0.8
                                )
                            }
                        }
                    )
                }
            }

            // Trip Safety Card (Requirement 19)
            item(key = "trip_safety_card") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToHealthSafety() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFF3B30).copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.HealthAndSafety,
                                        contentDescription = null,
                                        tint = Color(0xFFFF3B30),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "TRIP SAFETY",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFF3B30),
                                        letterSpacing = 0.8.sp
                                    )
                                    Text(
                                        text = "Trip Safety",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                            }
                            Text(
                                text = "Open Safety →",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 4 Functional Safety Pills (Requirement 19)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "🆘 SOS" to onNavigateToHealthSafety,
                                "👥 Contacts" to onNavigateToHealthSafety,
                                "📍 Location" to onNavigateToHealthSafety,
                                "🏥 Hospital" to onNavigateToHealthSafety
                            ).forEach { (label, onClick) ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF2F2F7))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { onClick() }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineEventCard(
    event: ItineraryEventEntity,
    onEcoSwap: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("timeline_item_${event.id}"),
        verticalAlignment = Alignment.Top
    ) {
        // Time Column & Connector Dot
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(55.dp)
        ) {
            Text(
                text = event.timeSlot,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(if (event.isEcoFriendly) EcoBadgeGreen else Color(0xFFFF9800))
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(65.dp)
                    .background(TextMuted.copy(alpha = 0.2f))
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Event Card Content
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Category & Icon + Eco Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val icon = when {
                            event.title.contains("EV Pickup", ignoreCase = true) -> Icons.Default.DirectionsCar
                            event.title.contains("EV Return", ignoreCase = true) || event.title.contains("Charging", ignoreCase = true) -> Icons.Default.EvStation
                            event.category == "Transport" -> Icons.Default.Train
                            event.category == "Dining" -> Icons.Default.Restaurant
                            event.category == "Stay" -> Icons.Default.Hotel
                            else -> Icons.Default.AutoAwesome
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = ForestGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.category.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }

                    // Eco tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (event.isEcoFriendly) EcoBadgeBg else Color(0xFFFFF3E0))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (event.isEcoFriendly) "Eco Friendly (${event.carbonKg}kg CO₂)" else "High CO₂ (${event.carbonKg}kg)",
                            color = if (event.isEcoFriendly) EcoBadgeGreen else Color(0xFFE65100),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 17.sp
                )

                // Eco Swap Button (if non-eco alternative available)
                if (!event.isEcoFriendly && event.ecoAlternative != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onEcoSwap,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .testTag("eco_swap_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LimeAccent,
                            contentColor = ForestGreenDark
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsBike,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Eco-Swap (-17.7 kg CO₂)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
