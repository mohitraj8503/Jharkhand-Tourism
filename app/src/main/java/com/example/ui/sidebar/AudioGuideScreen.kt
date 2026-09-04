package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun AudioGuideScreen() {
    var selectedLanguage by remember { mutableStateOf("English") }
    var searchQuery by remember { mutableStateOf("") }
    
    var currentlyPlaying by remember { mutableStateOf<String?>(null) }
    
    val guides = listOf(
        AudioGuideData("Dassam Falls", "Ranchi • 4 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg"),
        AudioGuideData("Hundru Falls", "Ranchi • 4 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg"),
        AudioGuideData("Jonha Falls", "Ranchi • 3 min guide", "https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg"),
        AudioGuideData("Panch Gagh Falls", "Khunti • 3 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg"),
        AudioGuideData("Rock Garden", "Ranchi • 3 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg"),
        AudioGuideData("Sun Temple Bundu", "Ranchi • 4 min guide", "https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg"),
        AudioGuideData("Tagore Hill", "Ranchi • 2 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg"),
        AudioGuideData("Betla National Park", "Latehar • 5 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg"),
        AudioGuideData("Netarhat Sunset", "Latehar • 4 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg"),
        AudioGuideData("Dalma Sanctuary", "Jamshedpur • 4 min guide", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Peaceful_Neighbours.jpg/1280px-Peaceful_Neighbours.jpg")
    )

    val filteredGuides = remember(searchQuery) {
        if (searchQuery.isBlank()) guides else guides.filter {
            it.title.contains(searchQuery, ignoreCase = true) || it.subtitle.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceWarm)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Audio Guide", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.4).sp)
                Text("Listen to authentic stories & history of Jharkhand.", fontSize = 14.sp, color = TextSecondary)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Language Segmented Control (Apple style)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(Color(0xFFE5E5EA), RoundedCornerShape(22.dp))
                        .padding(3.dp)
                ) {
                    listOf("English", "हिंदी", "संथाली").forEach { lang ->
                        val isSel = selectedLanguage == lang
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSel) Color.White else Color.Transparent)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                                ) { selectedLanguage = lang },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                lang,
                                color = if (isSel) ForestGreen else TextSecondary,
                                fontSize = 14.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search audio tours & destinations...") },
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

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredGuides) { guide ->
                    AudioGuideCard(
                        guide = guide,
                        isPlaying = currentlyPlaying == guide.title,
                        onPlayClick = {
                            currentlyPlaying = if (currentlyPlaying == guide.title) null else guide.title
                        }
                    )
                }
            }
        }
        
        // Mini Player
        if (currentlyPlaying != null) {
            val currentGuide = guides.find { it.title == currentlyPlaying }
            if (currentGuide != null) {
                val context = androidx.compose.ui.platform.LocalContext.current
                val miniRequest = remember(currentGuide.imageUrl) {
                    coil.request.ImageRequest.Builder(context)
                        .data(currentGuide.imageUrl)
                        .addHeader(
                            "User-Agent",
                            "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                        )
                        .crossfade(true)
                        .build()
                }

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ForestGreenDark),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = miniRequest,
                            contentDescription = currentGuide.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color.Gray)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(currentGuide.title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { 0.45f },
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                                color = LimeAccent,
                                trackColor = Color.White.copy(alpha = 0.2f)
                            )
                        }
                        IconButton(onClick = { currentlyPlaying = null }) {
                            Icon(Icons.Default.Pause, contentDescription = "Pause", tint = LimeAccent)
                        }
                        IconButton(onClick = { currentlyPlaying = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }
    }
}

data class AudioGuideData(val title: String, val subtitle: String, val imageUrl: String)

@Composable
fun AudioGuideCard(guide: AudioGuideData, isPlaying: Boolean, onPlayClick: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageRequest = remember(guide.imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(guide.imageUrl)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFE5E5EA))
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = guide.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(guide.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.2).sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(guide.subtitle, fontSize = 12.sp, color = TextMuted)
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (isPlaying) ForestGreen else LimeAccent),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onPlayClick) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = if (isPlaying) Color.White else ForestGreenDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
