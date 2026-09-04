package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun JharkhandGlanceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Image Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val heroRequest = remember {
                coil.request.ImageRequest.Builder(context)
                    .data("https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg")
                    .addHeader(
                        "User-Agent",
                        "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
                    )
                    .crossfade(true)
                    .build()
            }
            AsyncImage(
                model = heroRequest,
                contentDescription = "Dassam Falls",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            0.4f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = 0.75f)
                        )
                    )
            )
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.brand_logo),
                contentDescription = "JharVista Brand",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Jharkhand",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "The Land of Forests & Waterfalls",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Facts Grid
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FactCard(icon = Icons.Default.LocationCity, title = "Capital", value = "Ranchi", modifier = Modifier.weight(1f))
                FactCard(icon = Icons.Default.Map, title = "Area", value = "79,716 km²", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FactCard(icon = Icons.Default.WaterDrop, title = "Waterfalls", value = "10+ major", modifier = Modifier.weight(1f))
                FactCard(icon = Icons.Default.Forest, title = "National Parks", value = "1 (Betla)", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FactCard(icon = Icons.Default.Groups, title = "Tribal Comm.", value = "32+", modifier = Modifier.weight(1f))
                FactCard(icon = Icons.Default.WbSunny, title = "Best Time", value = "Oct–Mar", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // About Jharkhand
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "About Jharkhand",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Jharkhand, meaning 'The Land of Forests', was formed in 2000 from Bihar. Known for its rich mineral wealth and dense forests, it is home to vibrant tribal communities including the Santhal, Munda, Oraon, and Ho. The state offers immense tourism potential with its breathtaking waterfalls, scenic hills, wildlife sanctuaries, and ancient temples.",
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Must Visit Destinations Carousel
        Text(
            text = "Must Visit Destinations",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        val destinations = listOf(
            Pair("Dassam Falls", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg"),
            Pair("Hundru Falls", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg"),
            Pair("Patratu Valley", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg"),
            Pair("Netarhat Sunset", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg"),
            Pair("Betla National Park", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg"),
            Pair("Dalma Sanctuary", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Peaceful_Neighbours.jpg/1280px-Peaceful_Neighbours.jpg"),
            Pair("Sun Temple Bundu", "https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg"),
            Pair("Rock Garden", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg"),
            Pair("Jonha Falls", "https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg"),
            Pair("Baidyanath Dham", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg"),
            Pair("Parasnath Hill", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg")
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(destinations) { (name, url) ->
                GlanceDestinationCard(name = name, imageUrl = url)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cultural Heritage
        Text(
            text = "Cultural Heritage",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            HeritageCard(
                title = "Tribal Art: Sohrai & Kohbar",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
            )
            HeritageCard(
                title = "Tribal Festivals: Sarhul & Karma",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"
            )
            HeritageCard(
                title = "Folk Dance: Chhau Extravaganza",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/A_Cultural_Revelation_Chhau_Dance_17.jpg/1280px-A_Cultural_Revelation_Chhau_Dance_17.jpg"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // How to Reach
        Text(
            text = "How to Reach",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("By Air: Birsa Munda Airport, Ranchi (IXR)", fontSize = 14.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("By Rail: Ranchi Junction (RNC), Hatia (HTE)", fontSize = 14.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("By Road: NH-33, NH-31, NH-20", fontSize = 14.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // App & Project Credits
        Text(
            text = "App & Project Credits",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "JharVista Tourism Companion",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = ForestGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Author & Concept: Yash Kumar Binha (OTT) • yashbinha@gmail.com",
                    fontSize = 13.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Lead Developer: Mohit Raj • mohitraj8503@gmail.com",
                    fontSize = 13.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun FactCard(icon: ImageVector, title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
            Text(text = title, fontSize = 12.sp, color = TextMuted)
        }
    }
}

@Composable
fun GlanceDestinationCard(name: String, imageUrl: String) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val request = remember(imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(imageUrl)
            .addHeader(
                "User-Agent",
                "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
            )
            .crossfade(true)
            .build()
    }

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFE5E5EA))
    ) {
        AsyncImage(
            model = request,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Transparent,
                        0.5f to Color.Transparent,
                        1.0f to Color.Black.copy(alpha = 0.75f)
                    )
                )
        )
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        )
    }
}

@Composable
fun HeritageCard(title: String, imageUrl: String) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val request = remember(imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(imageUrl)
            .addHeader(
                "User-Agent",
                "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
            )
            .crossfade(true)
            .build()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = request,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            0.3f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = 0.75f)
                        )
                    )
            )
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}
