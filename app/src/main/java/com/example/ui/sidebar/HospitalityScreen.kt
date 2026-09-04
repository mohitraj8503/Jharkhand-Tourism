package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*

@Composable
fun HospitalityScreen() {
    var searchQuery by remember { mutableStateOf("") }
    
    val allHotels = remember {
        com.example.data.seed.JharkhandData.hotels.map {
            HotelData(
                name = it.name,
                city = it.city,
                price = it.price,
                rating = it.rating,
                amenities = it.amenities,
                isEco = it.isEco,
                imageUrl = it.imageUrl
            )
        }
    }
    var selectedCity by remember { mutableStateOf("All") }

    val filteredHotels = remember(searchQuery, selectedCity) {
        allHotels.filter { hotel ->
            (selectedCity == "All" || hotel.city.contains(selectedCity, ignoreCase = true)) &&
            (searchQuery.isBlank() || hotel.name.contains(searchQuery, ignoreCase = true) || hotel.city.contains(searchQuery, ignoreCase = true))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Hospitality Services", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.4).sp)
            Text("Stay comfortably across Jharkhand.", fontSize = 14.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search hotels, lodges, homestays...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE5E5EA),
                    focusedBorderColor = ForestGreen
                )
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("All", "Ranchi", "Netarhat", "Betla", "Patratu", "Khunti")) { city ->
                FilterChip(
                    selected = selectedCity == city,
                    onClick = { selectedCity = city },
                    label = { Text(city) },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ForestGreen,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = TextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedCity == city,
                        borderColor = if (selectedCity == city) Color.Transparent else Color(0xFFE5E5EA)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredHotels) { hotel ->
                HotelCard(hotel)
            }
        }
    }
}

data class HotelData(val name: String, val city: String, val price: Int, val rating: Double, val amenities: List<String>, val isEco: Boolean, val imageUrl: String)

@Composable
fun HotelCard(hotel: HotelData) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageRequest = remember(hotel.imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(hotel.imageUrl)
            .addHeader(
                "User-Agent",
                "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
            )
            .crossfade(true)
            .build()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(Color(0xFFE5E5EA))
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = hotel.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (hotel.isEco) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF34C759).copy(alpha = 0.92f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Eco, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Eco-Certified", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(hotel.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.3).sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("${hotel.city}, Jharkhand", fontSize = 13.sp, color = TextMuted)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF9E6))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFF9500), modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(hotel.rating.toString(), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF995D00))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("₹${hotel.price} / night", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    hotel.amenities.take(4).forEach { amenity ->
                        Text(
                            amenity,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary,
                            modifier = Modifier
                                .background(Color(0xFFF2F2F7), RoundedCornerShape(6.dp))
                                .padding(horizontal = 7.dp, vertical = 3.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LimeAccent, contentColor = ForestGreenDark),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Book Now", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
