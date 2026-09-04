package com.example.ui.booking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FlightItem
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

@Composable
fun FlightSearchScreen(
    repository: TruRepository,
    from: String = "Delhi (DEL)",
    to: String = "Ranchi (IXR)",
    date: String = "12 Aug",
    passengers: Int = 2,
    travelClass: String = "Economy",
    tripType: String = "One Way",
    onNavigateBack: () -> Unit,
    onFlightBooked: (FlightItem) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Best") }
    var selectedFlightForBreakdown by remember { mutableStateOf<FlightItem?>(null) }
    var bookedFlightSuccess by remember { mutableStateOf<FlightItem?>(null) }

    val flights = remember(from, to, date, passengers, travelClass) {
        repository.getMockFlights(from, to, date, passengers, travelClass, tripType)
    }

    val sortedFlights = when (selectedFilter) {
        "Cheapest" -> flights.sortedBy { it.priceUsd }
        "Eco-Certified" -> flights.sortedBy { it.co2Kg }
        "Fastest" -> flights.sortedBy { it.duration }
        else -> flights
    }

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
                    .testTag("flight_search_back")
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
                    text = "$from → $to",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "$date • $passengers Travelers • $travelClass",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }
        }

        // Filter Pills: Best | Cheapest | Fastest | Eco-Certified
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("Best", "Cheapest", "Fastest", "Eco-Certified")) { filter ->
                val isSel = selectedFilter == filter
                val bg by animateColorAsState(if (isSel) LimeAccent else SurfaceCard, label = "f_bg")
                val txtColor by animateColorAsState(if (isSel) ForestGreenDark else TextSecondary, label = "f_txt")

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(bg)
                        .clickable { selectedFilter = filter }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("filter_$filter")
                ) {
                    Text(
                        text = filter,
                        color = txtColor,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Flight Results List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(sortedFlights) { flight ->
                FlightCard(
                    flight = flight,
                    onViewBreakdown = { selectedFlightForBreakdown = flight },
                    onBook = {
                        bookedFlightSuccess = flight
                        onFlightBooked(flight)
                    }
                )
            }
        }
    }

    // Transparent Price Breakdown Dialog / Bottom Card
    selectedFlightForBreakdown?.let { flight ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { selectedFlightForBreakdown = null },
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = false) {}
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Fair-Trade Price Breakdown",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${flight.airline} (${flight.flightNumber})",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }

                        IconButton(onClick = { selectedFlightForBreakdown = null }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Local Provider Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Direct to Airline & Local Operators (${flight.localProviderPercent}%)",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "$${String.format("%.2f", flight.priceUsd * flight.localProviderPercent / 100)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = ForestGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Eco Fund Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Regenerative Carbon Offset Fund (${flight.ecoFundPercent}%)",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "$${String.format("%.2f", flight.priceUsd * flight.ecoFundPercent / 100)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = EcoBadgeGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Taxes & Network
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Aviation Taxes & Zero-Fee Staking (${flight.taxesPercent}%)",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "$${String.format("%.2f", flight.priceUsd * flight.taxesPercent / 100)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Guaranteed Price",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "$${String.format("%.2f", flight.priceUsd)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = ForestGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            selectedFlightForBreakdown = null
                            bookedFlightSuccess = flight
                            onFlightBooked(flight)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LimeAccent,
                            contentColor = ForestGreenDark
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Confirm & Pay with JharVista Card",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }

    // Success Confirmation Modal
    bookedFlightSuccess?.let { booked ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { bookedFlightSuccess = null },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .clip(RoundedCornerShape(26.dp)),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(LimeAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = ForestGreenDark,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Flight Confirmed!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${booked.airline} (${booked.flightNumber})\n$from → $to for $${String.format("%.2f", booked.priceUsd)}",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(EcoBadgeBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🌱 Carbon offset certificate issued automatically",
                            color = EcoBadgeGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { bookedFlightSuccess = null },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ForestGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Done", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun FlightCard(
    flight: FlightItem,
    onViewBreakdown: () -> Unit,
    onBook: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .testTag("flight_card_${flight.id}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Airline & Flight Number + Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ForestGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flight,
                            contentDescription = null,
                            tint = LimeAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = flight.airline,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = flight.flightNumber,
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                Text(
                    text = "$${String.format("%.0f", flight.priceUsd)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = ForestGreen
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Times & Codes Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = flight.departureTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = flight.fromCode,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                // Flight Duration & Arrow
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = flight.duration,
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(1.dp)
                                .background(TextMuted.copy(alpha = 0.5f))
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = ForestGreen,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = "Direct",
                        fontSize = 10.sp,
                        color = EcoBadgeGreen,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = flight.arrivalTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = flight.toCode,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Eco Tag & Price Breakdown Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(EcoBadgeBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = EcoBadgeGreen,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${flight.co2Kg} kg CO₂",
                        fontSize = 11.sp,
                        color = EcoBadgeGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .clickable { onViewBreakdown() }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = ForestGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Fair-Trade Breakdown",
                        fontSize = 12.sp,
                        color = ForestGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onBook,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("book_flight_${flight.id}"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LimeAccent,
                    contentColor = ForestGreenDark
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Select & Reserve",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
