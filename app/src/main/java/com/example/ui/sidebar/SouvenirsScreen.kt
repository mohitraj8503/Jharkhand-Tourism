package com.example.ui.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
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
fun SouvenirsScreen() {
    val allProducts = remember {
        com.example.data.seed.JharkhandData.souvenirs.map {
            ProductData(
                name = it.name,
                category = it.category,
                artisan = it.artisan,
                price = it.price,
                imageUrl = it.imageUrl
            )
        }
    }
    val categories = listOf("All", "Dokra Craft", "Sohrai Art", "Tussar Silk", "Lac Bangles", "Bamboo Craft", "Terracotta")
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredProducts = remember(selectedCategory) {
        if (selectedCategory == "All") allProducts else allProducts.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Jharkhand Souvenirs", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary, letterSpacing = (-0.4).sp)
                Text("Authentic handicrafts by indigenous artisans.", fontSize = 14.sp, color = TextSecondary)
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = ForestGreen)
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat) },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ForestGreen,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White,
                        labelColor = TextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedCategory == cat,
                        borderColor = if (selectedCategory == cat) Color.Transparent else Color(0xFFE5E5EA)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredProducts) { product ->
                ProductCard(product)
            }
        }
    }
}

data class ProductData(val name: String, val category: String, val artisan: String, val price: Int, val imageUrl: String)

@Composable
fun ProductCard(product: ProductData) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val imageRequest = remember(product.imageUrl) {
        coil.request.ImageRequest.Builder(context)
            .data(product.imageUrl)
            .addHeader(
                "User-Agent",
                "JharVista/1.0 (https://jharkhandtourism.gov.in; contact@jharvista.org) Mozilla/5.0 (Linux; Android 14; Mobile)"
            )
            .crossfade(true)
            .build()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color(0xFFE5E5EA))
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(product.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary, maxLines = 1, letterSpacing = (-0.2).sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text("by ${product.artisan}", fontSize = 11.sp, color = TextMuted, maxLines = 1)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("₹${product.price}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ForestGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
