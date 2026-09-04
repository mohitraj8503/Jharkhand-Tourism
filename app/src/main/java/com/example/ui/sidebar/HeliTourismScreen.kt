package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.*

@Composable
fun HeliTourismScreen() {
    val packages = listOf(
        HeliPackage("Ranchi Valley Skyview", "Ranchi → Patratu Valley → return", "30 min", 4500, listOf("Patratu Dam", "Red hills", "Forest canopy")),
        HeliPackage("Waterfall Circuit", "Ranchi → Dassam → Hundru → Jonha → return", "45 min", 6500, listOf("Three waterfalls from above", "Subarnarekha river")),
        HeliPackage("Netarhat Sunrise", "Ranchi → Netarhat → return", "60 min", 8000, listOf("Netarhat sunset/sunrise point", "Pine forests", "Hills")),
        HeliPackage("Betla Wildlife Safari Air", "Ranchi → Betla National Park → return", "75 min", 10000, listOf("Betla forest", "Wildlife from air", "Palamu fort ruins"))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                val context = androidx.compose.ui.platform.LocalContext.current
                val heliRequest = remember {
                    coil.request.ImageRequest.Builder(context)
                        .data("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg")
                        .addHeader(
                            "User-Agent",
                            "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                        )
                        .crossfade(true)
                        .build()
                }
                AsyncImage(
                    model = heliRequest,
                    contentDescription = "HeliTourism Aerial View",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.0f to Color.Transparent,
                                0.35f to Color.Transparent,
                                1.0f to Color.Black.copy(alpha = 0.75f)
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text("HeliTourism", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("See Jharkhand's waterfalls & valleys from the sky.", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("How it works", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StepItem(Icons.Default.Route, "Choose route", Modifier.weight(1f))
                    StepItem(Icons.Default.Event, "Select date", Modifier.weight(1f))
                    StepItem(Icons.Default.FlightTakeoff, "Board Helipad", Modifier.weight(1f))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Tour Packages", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        items(packages) { pkg ->
            HeliPackageCard(pkg, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
    }
}

@Composable
fun StepItem(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = ForestGreen)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text, fontSize = 12.sp, color = TextSecondary)
    }
}

data class HeliPackage(val name: String, val route: String, val duration: String, val price: Int, val highlights: List<String>)

@Composable
fun HeliPackageCard(pkg: HeliPackage, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(pkg.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(pkg.route, fontSize = 14.sp, color = TextSecondary)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(SurfaceWarm, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(pkg.duration, fontSize = 12.sp, color = ForestGreen, fontWeight = FontWeight.Medium)
                }
                Text("₹${pkg.price} / person", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = SurfaceWarm)
            Spacer(modifier = Modifier.height(12.dp))
            
            Text("Highlights:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            pkg.highlights.forEach { h ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(h, fontSize = 13.sp, color = TextSecondary)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LimeAccent, contentColor = ForestGreenDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Book Ride", fontWeight = FontWeight.Bold)
            }
        }
    }
}
