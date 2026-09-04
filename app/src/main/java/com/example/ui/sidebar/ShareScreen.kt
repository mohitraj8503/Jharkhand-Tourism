package com.example.ui.sidebar

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ShareScreen() {
    val context = LocalContext.current
    var shareTarget by remember { mutableStateOf<String?>(null) }
    
    val shareOptions = listOf(
        Triple("WhatsApp", Color(0xFF25D366), Icons.Default.Chat),
        Triple("Instagram", Color(0xFFE1306C), Icons.Default.CameraAlt),
        Triple("Facebook", Color(0xFF1877F2), Icons.Default.ThumbUp),
        Triple("Email", Color(0xFFD44638), Icons.Default.Email),
        Triple("Telegram", Color(0xFF0088CC), Icons.Default.Send),
        Triple("Copy Link", Color.Gray, Icons.Default.Link),
        Triple("More", Color.DarkGray, Icons.Default.MoreHoriz)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
            .padding(16.dp)
    ) {
        Text("Share", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Spread the word about Jharkhand.", fontSize = 14.sp, color = TextSecondary)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("What would you like to share?", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        
        ShareItemCard("Share My Trip Itinerary", Icons.Default.Luggage) { shareTarget = "Itinerary" }
        Spacer(modifier = Modifier.height(12.dp))
        ShareItemCard("Share a Destination", Icons.Default.Map) { shareTarget = "Destination" }
        Spacer(modifier = Modifier.height(12.dp))
        ShareItemCard("Share JharVista App", Icons.Default.Android) { shareTarget = "App" }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        if (shareTarget != null) {
            Text("Share via", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(shareOptions) { (name, color, icon) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                        val text = when (shareTarget) {
                            "Itinerary" -> "🌍 My JharVista Trip: Ranchi Waterfall Circuit\n\nDates: Oct 12 – Oct 14\nPlanned with JharVista AI — Jharkhand Tourism 🌿"
                            "Destination" -> "📍 Dassam Falls — Ranchi, Jharkhand\n\nDiscovered via JharVista AI — Jharkhand Tourism 🌿"
                            else -> "🌿 Check out JharVista — the AI-powered Jharkhand Tourism app!\nDownload: [Play Store link]"
                        }
                        shareContent(context, text)
                    }) {
                        Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(color), contentAlignment = Alignment.Center) {
                            Icon(icon, contentDescription = name, tint = Color.White, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(name, fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun ShareItemCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(SurfaceWarm), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = ForestGreen)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
        }
    }
}

fun shareContent(context: Context, text: String, subject: String? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
        if (subject != null) putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}
