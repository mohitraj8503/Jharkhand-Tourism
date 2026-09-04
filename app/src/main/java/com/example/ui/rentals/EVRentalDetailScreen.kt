package com.example.ui.rentals

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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.EVRental
import com.example.ui.theme.AppleBg
import com.example.ui.theme.EcoBadgeGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LimeAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private data class RouteCheckOption(
    val name: String,
    val distanceKm: Double
)

@Composable
fun EVRentalDetailScreen(
    rentalId: Int,
    viewModel: EVRentalViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToTimeline: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val rental = remember(rentalId, uiState.filteredRentals) {
        uiState.filteredRentals.firstOrNull { it.id == rentalId }
            ?: com.example.data.seed.JharkhandData.evRentals.firstOrNull { it.id == rentalId }
            ?: com.example.data.seed.JharkhandData.evRentals.first()
    }

    val context = LocalContext.current
    var showBookingSheet by remember { mutableStateOf(false) }

    val sampleRoutes = remember(rental.city) {
        when (rental.city) {
            "Jamshedpur" -> listOf(
                RouteCheckOption("Jamshedpur → Dalma Hills", 28.0),
                RouteCheckOption("Jamshedpur → Dimna Lake", 16.0),
                RouteCheckOption("Jamshedpur → Ranchi Hub", 130.0)
            )
            "Deoghar" -> listOf(
                RouteCheckOption("Deoghar → Baidyanath Dham", 6.0),
                RouteCheckOption("Deoghar → Trikut Pahar", 22.0),
                RouteCheckOption("Deoghar → Parasnath Shikharji", 112.0)
            )
            else -> listOf(
                RouteCheckOption("Ranchi → Patratu Valley", 42.0),
                RouteCheckOption("Ranchi → Hundru Falls", 45.0),
                RouteCheckOption("Ranchi → Netarhat Hills", 156.0),
                RouteCheckOption("Ranchi → Betla National Park", 175.0)
            )
        }
    }

    var selectedRoute by remember { mutableStateOf<RouteCheckOption?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppleBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AppleBg)
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = rental.vehicleName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = rental.providerName,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            // Scrollable Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
            ) {
                // Hero Image Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .background(Color(0xFFE5E5EA))
                ) {
                    if (!rental.imageUrl.isNullOrBlank()) {
                        val request = remember(rental.imageUrl) {
                            ImageRequest.Builder(context)
                                .data(rental.imageUrl)
                                .addHeader("User-Agent", "JharVista/1.0 (https://jharkhandtourism.gov.in) Mozilla/5.0")
                                .crossfade(true)
                                .build()
                        }
                        AsyncImage(
                            model = request,
                            contentDescription = rental.vehicleName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Eco badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ForestGreen)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Eco, contentDescription = null, tint = LimeAccent, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Eco Score ${rental.ecoScore}/100", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LimeAccent)
                        }
                    }
                }

                // Title & Price Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = rental.vehicleName,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${rental.category} • ${rental.city}",
                                fontSize = 13.sp,
                                color = ForestGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹${rental.pricePerDay}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                            Text(
                                text = "₹${rental.pricePerHour}/hour",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4-Card Spec Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SpecCard(icon = Icons.Default.Speed, title = "Range", value = "${rental.rangeKm} km", modifier = Modifier.weight(1f))
                        SpecCard(icon = Icons.Default.ElectricBolt, title = "Fast Charge", value = "${rental.chargingTimeHours}h to 80%", modifier = Modifier.weight(1f))
                        SpecCard(icon = Icons.Default.AirlineSeatReclineNormal, title = "Seats", value = "${rental.seats} Seats", modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Environmental Impact Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Eco, contentDescription = null, tint = EcoBadgeGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Estimated Carbon Benefit",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ImpactMetric(title = "CO₂ avoided", value = "≈ ${rental.co2SavedKgPerDay} kg/day")
                        ImpactMetric(title = "Petrol saved", value = "≈ ${(rental.co2SavedKgPerDay / 2.31 * 10).toInt() / 10.0} L/day")
                        ImpactMetric(title = "Eco Score", value = "${rental.ecoScore}/100")
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Estimated compared with a similar petrol vehicle for the selected distance.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Interactive Route Range Estimator Tool
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Route Range Check",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Select a popular destination to verify if ${rental.vehicleName} has adequate range:",
                        fontSize = 12.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Route options
                    sampleRoutes.forEach { route ->
                        val isSelected = selectedRoute == route
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) AppleBg else Color.Transparent)
                                .border(0.5.dp, if (isSelected) ForestGreen else Color(0xFFE5E5EA), RoundedCornerShape(10.dp))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    selectedRoute = route
                                    viewModel.checkRouteRange(rental, route.name, route.distanceKm)
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = route.name,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = TextPrimary
                            )
                            Text(
                                text = "${route.distanceKm.toInt()} km",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }

                    // Range check evaluation result
                    uiState.rangeCheckResult?.let { result ->
                        Spacer(modifier = Modifier.height(14.dp))
                        if (result.isSufficient) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(EcoBadgeGreen.copy(alpha = 0.12f))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EcoBadgeGreen, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Range is sufficient! This ${result.distanceKm.toInt()} km trip is comfortably within this vehicle's ${result.vehicleRangeKm} km range.",
                                    fontSize = 12.sp,
                                    color = ForestGreen,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFFF3CD))
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFF856404), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Range may be insufficient",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF856404)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = result.warningMessage ?: "This vehicle's practical range may fall short.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF856404),
                                    lineHeight = 16.sp
                                )
                                if (result.suggestedChargingStop != null) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "🔌 Suggested Charging Stop: ${result.suggestedChargingStop}",
                                        fontSize = 11.sp,
                                        color = ForestGreen,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pickup Hubs
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(0.5.dp, Color(0xFFE5E5EA), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Pickup Locations",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    rental.pickupLocations.forEach { loc ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = loc, fontSize = 13.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }

        // Fixed Bottom CTA Bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .border(0.5.dp, Color(0xFFE5E5EA))
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Button(
                onClick = { showBookingSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen,
                    contentColor = LimeAccent
                )
            ) {
                Text(
                    text = "Rent Now • ₹${rental.pricePerDay}/day",
                    color = LimeAccent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Booking Bottom Sheet
        if (showBookingSheet) {
            EVRentalBookingSheet(
                vehicle = rental,
                onDismiss = { showBookingSheet = false },
                onConfirmBooking = { request ->
                    viewModel.bookRental(request)
                    showBookingSheet = false
                },
                isLoading = uiState.isBookingInProgress,
                errorMessage = uiState.bookingErrorMessage
            )
        }

        // Booking Confirmation Dialog
        uiState.bookingConfirmation?.let { confirmation ->
            EVBookingConfirmationDialog(
                confirmation = confirmation,
                onViewTrip = {
                    viewModel.dismissBookingConfirmation()
                    onNavigateToTimeline(1L)
                },
                onDone = { viewModel.dismissBookingConfirmation() }
            )
        }
    }
}

@Composable
private fun SpecCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AppleBg)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(text = title, fontSize = 10.sp, color = TextMuted)
    }
}

@Composable
private fun ImpactMetric(title: String, value: String) {
    Column {
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
        Text(text = title, fontSize = 10.sp, color = TextMuted)
    }
}
