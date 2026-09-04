package com.example.ui.plan

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.model.ChatMessage
import com.example.data.repository.TruRepository
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
fun AIPlanningScreen(
    repository: TruRepository,
    onNavigateBack: () -> Unit,
    onNavigateToTimeline: (Long) -> Unit,
    onNavigateToRentals: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "msg-1",
                isUser = false,
                message = "Hello! I am JharVista AI, your personal travel companion for Jharkhand. Tell me about your dream destination — which waterfalls, hills, or wildlife sanctuaries would you like to explore and for how many days?",
                timestamp = "Just now"
            ),
            ChatMessage(
                id = "msg-2",
                isUser = true,
                message = "I want an eco-friendly 4-day trip to Betla National Park and Netarhat hills with sustainable forest stays.",
                timestamp = "Just now"
            ),
            ChatMessage(
                id = "msg-3",
                isUser = false,
                message = """
🌿 **Curated Regenerative Itinerary: Betla & Netarhat Forest Circuit (4 Days)**
• **Carbon Footprint:** 3.8 kg CO₂/day (58% below regional standard)
• **Fair-Trade Direct Benefit:** 85% to local tribal guides & eco-resorts

**Day 1:** Arrival at Ranchi → Scenic drive to Betla National Park & check-in at Forest Rest House.
**Day 2:** Early morning safari at Betla (elephant & tiger tracking) → Palamu Fort historical trek.
**Day 3:** Scenic winding ghats drive to Netarhat (Queen of Chotanagpur) → Sunset Point & Magnolia Point.
**Day 4:** Sunrise at Koel View Point → Pine forest walk → Return to Ranchi via Hundru Falls.
                """.trimIndent(),
                timestamp = "Just now",
                isActionCard = true,
                cardTitle = "Ready to build your complete timeline?",
                cardSubtext = "Sync seamlessly with flights, local guides, and offline maps.",
                actionText = "Generate Itinerary"
            )
        )
    }

    val samplePrompts = listOf(
        "Betla & Netarhat 4-Day Eco Circuit",
        "Ranchi Waterfalls & Patratu Valley 3 days",
        "Baidyanath Dham & Deoghar Spiritual Tour",
        "Dalma Wildlife & Jamshedpur Heritage"
    )

    // When user has asked something or chat is underway, AI expands to 100% FULL SCREEN
    var showIntroCards by remember { mutableStateOf(messages.none { it.isUser }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWarm)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        // Screen Header: Back Arrow, "AI Assistant", Full-screen toggle, Brand Logo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard)
                    .testTag("ai_back_button")
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
                    text = "AI Assistant",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = if (showIntroCards) "Ready to help anytime" else "Full Screen Travel AI",
                    style = MaterialTheme.typography.bodySmall,
                    color = ForestGreen
                )
            }

            // Quick Toggle: Fullscreen Chat <-> Intro Cards
            IconButton(
                onClick = { showIntroCards = !showIntroCards },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (showIntroCards) SurfaceCard else ForestGreen.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = if (showIntroCards) "Full Screen AI" else "Show Info Cards",
                    tint = ForestGreen,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Official Brand Logo Badge
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.brand_logo),
                contentDescription = "JharVista AI",
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(9.dp))
            )
        }

        // Conversation & Content Thread
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 12.dp)
        ) {
            if (showIntroCards) {
                // Hero Card (Dark Green Presentation Card)
                item(key = "ai_hero_card") {
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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Plan Your Perfect Trip In Seconds With JharVista AI",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Instant personalized itineraries powered by Gemini & verified local data.",
                                    color = LimeAccent,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // Prominent Rentals Card inside Plan Your Trip (Section 1 & Section 14)
                item(key = "ai_rentals_card") {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onNavigateToRentals() },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE5E5EA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(ForestGreen),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DirectionsCar,
                                            contentDescription = null,
                                            tint = LimeAccent,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "RENTALS",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = ForestGreen,
                                            letterSpacing = 0.8.sp
                                        )
                                        Text(
                                            text = "Explore Without a Carbon Trail",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    }
                                }
                                Text(
                                    text = "Explore EVs →",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Electric mobility for your Jharkhand journey. Clean rides across Ranchi, Jamshedpur & Deoghar.",
                                fontSize = 12.sp,
                                color = TextSecondary,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // 4 Functional Options
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF2F2F7))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { onNavigateToRentals() }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "🚗 Rent EV", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF2F2F7))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { onNavigateToRentals() }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "📍 Pickup", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF2F2F7))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { onNavigateToRentals() }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "🔌 Charging", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFF2F2F7))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { onNavigateToRentals() }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "🌿 Savings", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }
                            }
                        }
                    }
                }

                // Quick Suggestion Chips
                item(key = "ai_suggestion_chips") {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(samplePrompts) { prompt ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SurfaceCard)
                                    .clickable {
                                        showIntroCards = false
                                        inputText = prompt
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = prompt,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            } else {
                // Compact Quick Actions Bar in Full Screen AI Mode
                item(key = "ai_compact_actions_bar") {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Quick Rentals Pill
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .clickable { onNavigateToRentals() }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        tint = ForestGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "EV Rentals",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }

                        // New Trip Reset Pill
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(ForestGreen)
                                    .clickable {
                                        messages.clear()
                                        messages.add(
                                            ChatMessage(
                                                id = "msg-1",
                                                isUser = false,
                                                message = "Hello! I am JharVista AI, your personal travel companion for Jharkhand. Tell me about your dream destination — which waterfalls, hills, or wildlife sanctuaries would you like to explore and for how many days?",
                                                timestamp = "Just now"
                                            )
                                        )
                                        showIntroCards = true
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = LimeAccent,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "New Plan",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        // Suggestion Pills
                        items(samplePrompts) { prompt ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceCard)
                                    .clickable {
                                        inputText = prompt
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = prompt,
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Conversation Message Thread (Full Screen)
            items(messages, key = { it.id }) { msg ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    if (msg.isUser) {
                        UserChatBubble(msg.message, msg.timestamp)
                    } else {
                        AiChatBubble(
                            message = msg,
                            onGenerateTimelineClick = {
                                onNavigateToTimeline(1L) // navigates to active Japan/Bali itinerary
                            }
                        )
                    }
                }
            }

            if (isLoading) {
                item(key = "ai_loading_indicator") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = ForestGreen,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "JharVista AI is designing your personalized itinerary...",
                            fontSize = 13.sp,
                            color = ForestGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Bottom Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceCard)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "Ask JharVista AI to plan or adjust your trip...",
                        fontSize = 14.sp,
                        color = TextMuted
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_input_field"),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SurfaceWarm,
                    unfocusedContainerColor = SurfaceWarm,
                    disabledContainerColor = SurfaceWarm,
                    focusedIndicatorColor = ForestGreen,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Send Button
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(if (inputText.isNotBlank() && !isLoading) LimeAccent else Color(0xFFE0E0E0))
                .clickable(enabled = inputText.isNotBlank() && !isLoading) {
                    val prompt = inputText.trim()
                    inputText = ""
                    showIntroCards = false
                    messages.add(
                        ChatMessage(
                            id = "user-${System.currentTimeMillis()}",
                            isUser = true,
                            message = prompt,
                            timestamp = "Just now"
                        )
                    )
                    isLoading = true

                    coroutineScope.launch {
                        val offset = if (showIntroCards) 3 else 1
                        listState.animateScrollToItem((offset + messages.size - 1).coerceAtLeast(0))
                        val aiResponse = repository.generateAiItinerary(prompt)
                        messages.add(
                            ChatMessage(
                                id = "ai-${System.currentTimeMillis()}",
                                isUser = false,
                                message = aiResponse,
                                timestamp = "Just now",
                                isActionCard = true,
                                cardTitle = "Complete Itinerary Generated",
                                cardSubtext = "Review scheduled time blocks and green transport options.",
                                actionText = "Generate Itinerary"
                            )
                        )
                        isLoading = false
                        val newOffset = if (showIntroCards) 3 else 1
                        listState.animateScrollToItem((newOffset + messages.size - 1).coerceAtLeast(0))
                    }
                }
                .testTag("ai_send_button"),
            contentAlignment = Alignment.Center
        ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (inputText.isNotBlank() && !isLoading) ForestGreenDark else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun UserChatBubble(text: String, timestamp: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.82f),
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp),
            colors = CardDefaults.cardColors(containerColor = ForestGreen)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun AiChatBubble(
    message: ChatMessage,
    onGenerateTimelineClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.92f),
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header with Official Brand Logo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.brand_logo),
                        contentDescription = "JharVista AI",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "JharVista AI",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = ForestGreen
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message.message,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                if (message.isActionCard) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceWarm)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = message.cardTitle ?: "Generate Itinerary",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = TextPrimary
                            )
                            if (!message.cardSubtext.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = message.cardSubtext,
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = onGenerateTimelineClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("generate_itinerary_cta"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LimeAccent,
                                    contentColor = ForestGreenDark
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EventNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = message.actionText ?: "Generate Itinerary",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
