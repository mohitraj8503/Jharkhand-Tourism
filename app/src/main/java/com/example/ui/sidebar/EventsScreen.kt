package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun EventsScreen() {
    var selectedMonth by remember { mutableStateOf("All") }
    val months = listOf("All", "January", "March", "August", "October", "November")
    
    val allEvents = remember {
        com.example.data.seed.JharkhandData.events.map {
            EventData(
                name = it.name,
                month = it.month,
                location = it.location,
                description = it.description,
                imageUrl = it.imageUrl
            )
        }
    }

    val filteredEvents = remember(selectedMonth) {
        if (selectedMonth == "All") allEvents else allEvents.filter { it.month.contains(selectedMonth, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Events & Festivals", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.4).sp)
            Text("Experience the rich cultural heritage of Jharkhand.", fontSize = 14.sp, color = TextSecondary)
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(months) { month ->
                FilterChip(
                    selected = selectedMonth == month,
                    onClick = { selectedMonth = month },
                    label = { Text(month) },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ForestGreen,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = TextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedMonth == month,
                        borderColor = if (selectedMonth == month) Color.Transparent else Color(0xFFE5E5EA)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredEvents) { event ->
                EventCard(event)
            }
        }
    }
}

data class EventData(val name: String, val month: String, val location: String, val description: String, val imageUrl: String)

@Composable
fun EventCard(event: EventData) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageRequest = remember(event.imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(event.imageUrl)
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
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFE5E5EA))
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = event.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(event.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.2).sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text("${event.month} • ${event.location}", fontSize = 12.sp, color = ForestGreen, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(event.description, fontSize = 13.sp, color = TextSecondary, maxLines = 2, lineHeight = 17.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                ) {
                    Text("Add to Trip", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}
