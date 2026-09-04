# JHARVISTA — MASTER SPECIFICATION FOR GOOGLE AI STUDIO

**App name:** JharVista — "Tourism in Jharkhand"
**AI assistant name:** JharVista AI
**Platform:** Native Android (Kotlin 2.x + Jetpack Compose + Material 3)
**UI aesthetic:** Apple HIG + Material 3 hybrid (must look like Apple made it)
**Date:** September 2026

> **This is the single, complete, compiled document. Google AI Studio should use ONLY this file to generate the entire working app. All previous documents are superseded by this one.**

---

## TABLE OF CONTENTS

1. Critical Instructions
2. App Overview & Problem Statement
3. User Personas
4. Core Capabilities
5. App Flow (Welcome → Login → Home → Profile)
6. Sidebar — Exact 7-Item Specification
7. Screen-by-Screen UI Specification
8. Apple-Grade Design System (Colors, Typography, Spacing, Components)
9. Real Jharkhand Data — Destinations with Coordinates & Image URLs
10. Real Hotels with Data
11. Real Flights to/from Ranchi (IXR)
12. Real Jharkhand Festivals & Events
13. Real Jharkhand Souvenirs / Handicrafts
14. HeliTourism Packages
15. Audio Guide Content
16. Maps Integration Spec
17. Email & Share Integration
18. Authentication Spec
19. AI Integration Plan (Gemini)
20. Responsible-Tourism Layer
21. Technology Stack & Module Structure
22. Apple UI Rules — Do's and Don'ts
23. Reference GitHub Repositories
24. Acceptance Criteria
25. Out of Scope

---

## 1. CRITICAL INSTRUCTIONS FOR GOOGLE AI STUDIO

> **READ THIS BEFORE GENERATING ANY CODE. VIOLATING ANY RULE HERE IS A BUILD FAILURE.**

1. **App name is JharVista.** Not TRU, not "Jharkhand Tourism." Use "JharVista" on every screen, splash, login, profile, share text, and AI assistant. AI assistant = "JharVista AI."

2. **First screen = Welcome/Splash → then Login.** The user must see a welcome splash, then a login/signup screen before reaching Home. If already logged in (session exists), skip directly to Home.

3. **Top-right avatar on Home must open a full Apple-style Profile screen.** Avatar shows user initials (e.g., "PS") or uploaded photo. Tapping opens Profile screen that looks like iOS Settings — grouped white cards, hairline separators, colored icon squares.

4. **Sidebar has exactly 7 items.** Do not add, remove, reorder, or rename. The 7 items are defined in Section 6. The sidebar coexists with the bottom nav (Home / Plan / Trips / Wallet).

5. **ALL data is real Ranchi/Jharkhand.** Every destination name, hotel, flight, festival, souvenir, price, coordinate, and image URL must be from real Jharkhand. NO Bali, Berlin, Cairo, or generic placeholders.

6. **ALL image URLs are provided in this document (Sections 9-14).** Use them directly in Kotlin data models as `imageUrl` fields. Load with Coil `AsyncImage`. Do NOT replace with placeholders.

7. **Maps must be fully working.** Google Maps Compose SDK with real lat/lng markers for every destination. Tap marker → info card → "Navigate" button launches Google Maps turn-by-turn.

8. **Email must work.** "Share Itinerary" opens Android `Intent.ACTION_SEND` with `message/rfc822` and a formatted trip summary.

9. **UI must look like an Apple app.** Follow every rule in Section 8 and Section 22. NO ripple effects. NO Material shadows on cards. Use hairline separators. Use Apple system colors. Typography at 17sp body.

10. **Every button must work.** No "coming soon," no placeholders, no dead ends. Every screen must be functional.

11. **No hardcoded secrets.** Gemini API key via `BuildConfig` / `local.properties`. Google Maps API key in `AndroidManifest.xml`.

---

## 2. APP OVERVIEW & PROBLEM STATEMENT

JharVista is an AI-powered travel companion for Jharkhand Tourism that unifies discovery, planning, booking, and trip management into one seamless native Android experience. The app is anchored in Ranchi — the capital of Jharkhand — and covers all major tourism destinations across the state.

**Problems JharVista solves:**

1. **Fragmented tourism experience** — No single app for Jharkhand tourism. Tourists use Google for search, MakeMyTrip for booking, Google Maps for navigation, WhatsApp for sharing. Context lost at every handoff.

2. **No AI-assisted trip planning** — No app lets a traveller say "Plan a 3-day eco-friendly trip to Ranchi covering waterfalls under ₹15,000" and get a complete itinerary.

3. **No real-time data or maps** — Existing Jharkhand tourism resources are static. No live map, no real-time hotel info, no navigation from within the app.

4. **No responsible-tourism layer** — Popular spots overcrowded while equally beautiful alternatives remain empty. No eco-certified stays, no carbon visibility, no fair-trade pricing.

5. **Trips are not managed** — Once planned, no live timeline, no trip-readiness tracker, no email sharing, no on-trip management.

**JharVista's solution:** A single app with AI trip planning, real-time maps, hotel/flight booking, audio guides, souvenir marketplace, heli-tourism, email sharing, and a responsible-tourism overlay — all with real Jharkhand data and Apple-grade UI.

---

## 3. USER PERSONAS

**Primary — "The Jharkhand Explorer" (18-45)**
- Lives in or visiting Ranchi/Jharkhand
- Wants to discover waterfalls, temples, wildlife, hill stations
- Comfortable with AI assistants
- Cares about budget and eco-impact

**Secondary — "The Out-of-State Tourist"**
- Visiting from Delhi, Kolkata, Bengaluru
- Needs flights to Ranchi (IXR), hotels, curated itinerary
- Wants maps, email sharing, real-time info

**Tertiary — "The Eco-Conscious Traveller"**
- Seeks sustainable stays, local experiences, low-carbon transport
- Wants transparency on footfall, carbon, fair-trade

---

## 4. CORE CAPABILITIES

1. **AI-First Trip Planning** — conversational AI builds complete Jharkhand itinerary from free-text prompt
2. **Smart Decision Support** — structured booking config with real destinations, dates, budget tiers in ₹
3. **Map-Integrated Discovery** — live Google Maps with markers for every tourism spot
4. **Curated, Not Generic** — recommendations based on budget, style, history with real Jharkhand data
5. **Seamless Payments** — in-app virtual card/wallet
6. **Trip Memory & Dashboard** — "My Trips" with tabs and stats
7. **Built for the Full Journey** — live trip timeline with timestamped events
8. **Sidebar Navigation** — 7 Jharkhand-specific sections (preserved from prototype)
9. **Email Sharing** — send itinerary via email
10. **Responsible-Tourism Overlay** — footfall-aware suggestions, eco-certified stays, carbon visibility, fair-trade pricing, verified ratings

---

## 5. APP FLOW

```
App Launch
  → WelcomeScreen (2s splash: JharVista logo + Dassam Falls photo, dark green bg)
    → IF not logged in → LoginScreen
      → Login success → HomeScreen
    → IF tapped "Sign Up" → SignupScreen
      → Signup success → HomeScreen
    → IF already logged in (session exists) → HomeScreen directly

HomeScreen
  → Top-right avatar tap → ProfileScreen (Apple-style settings)
  → Hamburger/swipe → Sidebar (7 items)
  → Bottom nav: Home | Plan | Trips | Wallet
  → AI card tap → AIPlanningScreen
  → Destination card tap → DestinationDetailScreen
  → Map tab → MapDiscoveryScreen

ProfileScreen
  → Edit Profile → EditProfileScreen
  → Logout → clears session → LoginScreen
```

### Navigation graph
```kotlin
NavHost(startDestination = if (sessionManager.isLoggedIn()) "home" else "welcome") {
    composable("welcome") { WelcomeScreen(onNavigateToLogin = { navController.navigate("login") }) }
    composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("home") { popUpTo("welcome") { inclusive = true } } }, onNavigateToSignup = { navController.navigate("signup") }) }
    composable("signup") { SignupScreen(onSignupSuccess = { navController.navigate("home") { popUpTo("welcome") { inclusive = true } } }, onNavigateToLogin = { navController.popBackStack() }) }
    composable("home") { JharVistaMainScreen(onProfileClick = { navController.navigate("profile") }) }
    composable("profile") { ProfileScreen(onLogout = { sessionManager.logout(); navController.navigate("login") { popUpTo("home") { inclusive = true } } }, onBack = { navController.popBackStack() }) }
    // Sidebar routes
    composable("jharkhand_glance") { JharkhandGlanceScreen() }
    composable("events") { EventsScreen() }
    composable("hospitality") { HospitalityScreen() }
    composable("souvenirs") { SouvenirsScreen() }
    composable("heli_tourism") { HeliTourismScreen() }
    composable("audio_guide") { AudioGuideScreen() }
    composable("share") { ShareScreen() }
}
```

---

## 6. SIDEBAR — EXACT 7-ITEM SPECIFICATION

> **DO NOT rename, reorder, add, or remove any item. This list is final.**

### Visual design
| Property | Value |
|----------|-------|
| Background | Dark navy `#1A2332` |
| Text/Icon colour | White `#FFFFFF` |
| Icon style | Material outlined icons, 24dp |
| Text size | 16sp, Medium |
| Item height | 56dp |
| Drawer width | 280dp |
| Slide animation | 300ms, from left |
| Opens via | Hamburger icon or left-edge swipe |
| Closes via | Back arrow, outside tap, or left swipe |
| Active item | Subtle lighter bg `#3A4555` or lime accent bar on left |
| Header | "JharVista" logo, white, 18sp semibold |
| Footer | "Powered by JharVista AI", white 40% opacity, 12sp |

### The 7 sidebar items

| # | Label | Icon | Route | What It Does |
|---|-------|------|-------|-------------|
| 1 | Jharkhand at a Glance | `Icons.Filled.AccountBalance` | `jharkhand_glance` | State overview: stats grid (capital, area, districts, waterfalls, sanctuaries, tribes), hero image, must-visit carousel, cultural heritage, how to reach |
| 2 | Events | `Icons.Filled.CalendarMonth` | `events` | Real Jharkhand festivals (Sarhul, Karma, Sohrai, Bandna, Tusu, World Tribal Day, Jharkhand Foundation Day). Each with photo, date, location, "Add to Trip" |
| 3 | Hospitality Services | `Icons.Filled.Hotel` | `hospitality` | Real hotels/lodges/homestays. Filter by city/price/rating/eco. Booking bottom sheet with dates, guests, price summary |
| 4 | Jharkhand Souvenirs | `Icons.Filled.ShoppingBag` | `souvenirs` | Real handicrafts marketplace grid (Dokra, Sohrai, Paitkar, bamboo, terracotta, tussar silk, lac, tribal jewellery). Cart + checkout. Artisan spotlight |
| 5 | HeliTourism | `Icons.Filled.Flight` | `heli_tourism` | Helicopter tour packages over Jharkhand (Patratu Valley, waterfalls, Netarhat, Betla). Route mini-map, safety info, booking |
| 6 | Audio Guide | `Icons.Filled.Headphones` | `audio_guide` | Audio guide per destination. Language selector (English/हिंदी/संथाली). Mini player bar (play/pause, seek, 15s skip). TTS or pre-recorded |
| 7 | Share on Social Media | `Icons.Filled.Share` | `share` | Share trip itinerary / destination / app via WhatsApp, Instagram, Facebook, Email, Telegram, Copy Link. Android Intent.ACTION_SEND |

### What the sidebar does NOT contain
- No Home / Plan / Trips / Wallet (bottom nav)
- No Settings / Login / Notifications (profile screen)
- No 8th item. Exactly 7.

---

## 7. SCREEN-BY-SCREEN UI SPECIFICATION

### 7.1 Welcome / Splash Screen
- Full-bleed Dassam Falls photo with dark gradient overlay
- "JharVista" centered, 36sp bold white
- "Tourism in Jharkhand" subtitle, 15sp white 70% opacity
- Auto-transition to Login after 2s
- "Powered by JharVista AI" at bottom, white 40%, 12sp

### 7.2 Login Screen
- Clean white background `#F8F9FA`
- "JharVista" logo top-center, forest green, 28sp bold
- "Tourism in Jharkhand" subtitle, grey, 14sp
- Form card (white, 24dp radius, subtle shadow):
  - Email field (filled style, `#F2F2F7` bg, 12dp radius, email icon, 17sp)
  - Password field (filled style, show/hide eye icon)
  - "Forgot password?" right-aligned, forest green, 13sp
  - "Log In" button: full-width, forest green, white text, 52dp, 14dp radius
  - Divider: "or"
  - "Continue with Google" button: white with border, Google G logo, 52dp
  - "Don't have an account? Sign Up" link
- Terms text at bottom: "By continuing, you agree to JharVista's Terms..."

### 7.3 Signup Screen
- Same layout as Login
- Fields: Full Name, Email, Password, Confirm Password
- Inline validation (email format, 6+ char password, match)
- "Sign Up" button → Home

### 7.4 Home / Discovery Screen
- **Top bar:** hamburger (left), "CURRENT LOCATION" (11sp grey) + "Ranchi, Jharkhand" (17sp bold) with pin icon, ProfileAvatar (40dp circular, lime bg, initials/photo) (right)
- **Filter chips:** Trending (active, lime) | Top Picks | Nearby
- **AI Assistant card:** lime gradient, "JHARVISTA AI" tag, "Plan Your Perfect Trip In Seconds With JharVista AI", "Start Planning →"
- **"Curated For You" section:** horizontal carousel of destination cards (real photos, ₹ prices, "Explore Itinerary" button, Eco-Choice badge)
- **Smart Footfall Prediction card:** lightbulb icon, "High footfall (≈22%) predicted this weekend. Consider Panch Gagh Falls for zero wait times."
- **Trip Planner widget:** "Your Ranchi Waterfall Circuit is 68% Ready", "5 Days Left" badge
- **Bottom nav:** Home (active) | Plan | Trips | Wallet — 49dp height, hairline separator, NO ripple, NO pill bg

### 7.5 AI Planning Screen
- Header: back arrow, "AI Assistant", "Ready to help anytime", profile icon
- Green banner: "Plan Your Perfect Trip In Seconds With JharVista AI"
- Chat thread: user messages right-aligned (forest green bg, white text), AI responses left-aligned (`#F2F2F7` bg, black text), 16sp, rounded 16dp
- AI response includes: day-by-day itinerary, carbon footprint, fair-trade %, EV options
- Input bar: "Ask JharVista AI to plan or adjust your trip..." with send icon

### 7.6 Booking Config Screen
- Destination chip selector (real Jharkhand places)
- Dates & Duration (date picker + "X nights")
- Budget: Budget (₹3,000-5,000/day) | Standard (₹5,000-10,000/day) | Luxury (₹10,000+/day)
- Trip type: One Way | Round Trip
- Passengers stepper, Class dropdown
- "Search" button (lime)

### 7.7 Map Discovery Screen
- Full-bleed Google Maps Compose
- Custom markers for all 15 destinations (Section 9)
- Floating search bar at top (white, 12dp radius, subtle shadow)
- Floating info card on marker tap: destination name, thumbnail, rating, "Navigate" button
- Bottom sheet with nearby cards
- Clustering when zoomed out

### 7.8 Flight Search Screen
- From (DEL, CCU, BOM, BLR, PAT) / To: Ranchi (IXR) / Date / Passengers / Class
- Results: airline, times, duration, price ₹, "Book" button

### 7.9 Wallet Screen
- Virtual card (lime/dark-green gradient, brand logo)
- Cardholder, masked number, expiry, CVV
- Balance ₹
- Transaction list
- "Add Money" / "Pay" buttons

### 7.10 My Trips Dashboard
- Header: "My Trips"
- Tabs: All | Upcoming | Ended | Past
- Stats: "12 Places Visited", "8 Saved Places", "3 Upcoming"
- Trip cards with cover image, status, readiness %
- Share button per trip → email intent

### 7.11 Trip Timeline Screen
- Header: "My Trip: Ranchi, Jharkhand"
- Chronological timeline: 08:00 / 10:30 / 13:00 / 16:00 events with images
- Discovery tabs: Trending | Top Picks | Nearby
- Email itinerary button (top right)
- Tap event to swap/replace with eco-alternative

### 7.12 Destination Detail Screen
- Hero image (full-width, rounded bottom 24dp, extends under status bar)
- Floating back button (circular, semi-transparent white, 36dp)
- Title overlaid on image (white bold + dark gradient)
- Rating, eco badge, footfall indicator
- Description, Best Time, Entry Fee, Timings
- "Get Directions" → Google Maps intent
- "Add to Trip" button
- "Play Audio Guide" → mini player
- Gallery section
- Nearby places carousel

### 7.13 Profile Screen (Apple-style)
- Background: `#F2F2F7`
- Header: large circular avatar (96dp), name (22sp bold), email (15sp grey), "Edit Profile" button
- Stats row: Trips Planned, Saved Places, Places Visited
- Grouped settings sections (white cards, 12dp radius, hairline separators):
  - **Account:** Personal Info (blue icon), Change Password (grey icon), Linked Accounts
  - **Preferences:** Language (English/हिंदी/संथाली), Notifications (toggle), Eco-Mode (toggle)
  - **Trips & Data:** My Trips, Saved Places, Payment Methods, Downloaded Guides
  - **About:** About JharVista, Privacy Policy, Terms, Rate JharVista
  - **Logout:** red text, centered, 52dp

### 7.14 Sidebar Sub-Screens
- **JharkhandGlanceScreen:** hero banner, stats grid, must-visit carousel, cultural heritage, how to reach
- **EventsScreen:** month filter chips, event list with photos, "Add to Trip"
- **HospitalityScreen:** search bar, filter chips (city/price/rating/eco), hotel list with photos, booking bottom sheet
- **SouvenirsScreen:** category chips, product grid, cart icon, artisan spotlight
- **HeliTourismScreen:** hero image, how-it-works, package list with mini-map, safety info, booking sheet
- **AudioGuideScreen:** language selector, search, destination list with play buttons, mini player bar
- **ShareScreen:** 3 share options, share target grid, formatted content

---

## 8. APPLE-GRADE DESIGN SYSTEM

### 8.1 Design principles
1. **Clarity** — legible text, precise icons, content is king
2. **Deference** — UI recedes, no heavy borders or noise
3. **Depth** — layered views, subtle shadows on floating elements only
4. **Consistency** — same spacing, fonts, colors across all screens
5. **No ripples** — disable ripple on ALL touchable elements

### 8.2 Color palette
```kotlin
// Apple System Colors
val SystemBackground = Color(0xFFFFFFFF)
val SecondarySystemBackground = Color(0xFFF2F2F7)  // grouped bg
val Label = Color(0xFF000000)
val SecondaryLabel = Color(0xFF3C3C43).copy(alpha = 0.6f)
val TertiaryLabel = Color(0xFF3C3C43).copy(alpha = 0.3f)
val Separator = Color(0xFF3C3C43).copy(alpha = 0.29f)
val OpaqueSeparator = Color(0xFFC6C6C8)
val SystemGreen = Color(0xFF34C759)
val SystemRed = Color(0xFFFF3B30)
val SystemBlue = Color(0xFF007AFF)
val SystemOrange = Color(0xFFFF9500)
val SystemGrey = Color(0xFF8E8E93)
val SystemGrey3 = Color(0xFFC7C7CC)
val SystemGrey5 = Color(0xFFE5E5EA)
val SystemGrey6 = Color(0xFFF2F2F7)

// JharVista Brand
val ForestGreen = Color(0xFF0B3D2E)
val LimeAccent = Color(0xFFC6F432)
val NavySidebar = Color(0xFF1A2332)

// Footfall
val FootfallLow = Color(0xFF34C759)   // green
val FootfallMedium = Color(0xFFFF9500) // orange
val FootfallHigh = Color(0xFFFF3B30)   // red

// Eco
val EcoBadgeBg = Color(0xFFE8F5E9)
val EcoBadgeText = Color(0xFF2E7D32)
```

### 8.3 Typography (SF Pro / Inter style)
```kotlin
val JharVistaTypography = Typography(
    displayLarge = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold),       // Large title
    displayMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),        // Title 1
    displaySmall = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),    // Title 2
    headlineMedium = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),  // Title 3
    headlineSmall = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold),    // Headline
    bodyLarge = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Normal),          // Body (Apple standard)
    bodyMedium = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal),         // Subhead
    bodySmall = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal),          // Footnote
    labelLarge = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium),          // Section header
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),        // Caption
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Normal)           // Fine print
)
```

### 8.4 Spacing & shape
- Screen margin: 20dp
- Card padding: 16-20dp
- Card radius: 12dp (grouped lists), 16dp (standalone)
- Button height: 52dp, radius: 14dp
- Button radius (chips): full pill
- Bottom sheet radius: 24dp (top corners)
- Text field radius: 12dp
- Avatar: full circle
- Bottom nav height: 49dp (Apple tab bar height)
- Section spacing: 24-32dp
- Minimum touch target: 44×44dp
- Hairline separator: 0.5dp

### 8.5 Shapes
```kotlin
val JharVistaShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)
```

### 8.6 Key component rules
- **Cards:** NO elevation/shadow. White on `#F2F2F7` background. Use background contrast.
- **Separators:** 0.5dp `#E5E5EA`, indented to align after icon (start = 56dp)
- **Buttons:** 52dp height, 14dp radius, forest green bg + white text (primary), white + border (secondary), red (destructive)
- **Toggle switches:** Apple green `#34C759` when on, `#E5E5EA` when off, white thumb
- **Text fields:** Filled style, `#F2F2F7` bg, 12dp radius, 50dp height, no outline border when unfocused
- **Bottom nav:** 49dp, white bg, hairline separator on top, NO pill on active, just color change (forest green active, `#8E8E93` inactive), labels 10sp
- **Settings rows:** icon in colored 28dp squircle (7dp radius), 17sp black label, chevron `>` trailing, hairline separator between items
- **Chat bubbles:** 16dp radius, one corner 4dp (iMessage style), forest green (user) / `#F2F2F7` (AI)
- **Floating elements (search bars, back buttons):** subtle shadow allowed (2dp), white bg, circular or 12dp radius
- **Grabber handle on bottom sheets:** 4×40dp grey pill (`#C7C7CC`) at top center

### 8.7 Disable ripple on ALL clickables
```kotlin
Modifier.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() }
) { onClick() }
```

### 8.8 Animations
- Page push: slide in from right, 350ms, `EaseInOut`
- Bottom sheet: slide up, 400ms, with grabber handle
- Tab switch: crossfade, 200ms
- Card tap: background highlight grey briefly, NO scale, NO ripple
- Splash → login: fade + scale, 500ms

---

## 9. REAL JHARKHAND DATA — DESTINATIONS

> **Use these directly in Kotlin data models. All image URLs are real Wikimedia Commons photos.**

```kotlin
data class Destination(
    val id: Int,
    val name: String,
    val city: String,
    val type: String,
    val lat: Double,
    val lng: Double,
    val entryFee: String,
    val bestTime: String,
    val imageUrl: String,
    val description: String,
    val rating: Double,
    val ecoCertified: Boolean = false,
    val crowdLevel: String = "Medium"
)

val jharkhandDestinations = listOf(
    Destination(1, "Dassam Falls", "Ranchi, Jharkhand", "Waterfall", 23.1434, 85.4664, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg",
        "Spectacular 44m waterfall on the Kanchi River, 40km from Ranchi. Also known as Dassam Ghagh.", 4.5, crowdLevel = "High"),
    Destination(2, "Hundru Falls", "Ranchi, Jharkhand", "Waterfall", 23.4509, 85.6600, "₹20", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg",
        "Highest waterfall in Jharkhand at 98m, on the Subarnarekha River. 34th highest in India.", 4.6, crowdLevel = "High"),
    Destination(3, "Jonha Falls", "Ranchi, Jharkhand", "Waterfall", 23.3417, 85.6083, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg",
        "Also called Gautamdhara Falls, 43m waterfall on the Raru River. Buddhist shrine nearby.", 4.4, crowdLevel = "Medium"),
    Destination(4, "Panch Ghagh Falls", "Khunti, Jharkhand", "Waterfall", 22.9447, 85.2547, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg",
        "Five streams of the Banai River. Safest waterfall for tourists. 55km from Ranchi.", 4.3, ecoCertified = true, crowdLevel = "Low"),
    Destination(5, "Sun Temple", "Ranchi, Jharkhand", "Temple", 23.3470, 85.2760, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg",
        "Temple in form of a chariot with 18 wheels and 7 horses, dedicated to Surya. Near Bundu, 40km from Ranchi.", 4.4, crowdLevel = "Medium"),
    Destination(6, "Rock Garden", "Ranchi, Jharkhand", "Garden", 23.3630, 85.3100, "₹10", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg",
        "Carved from rocks of Gonda Hill on Kanke Road. Sculptures, waterfalls, picnic spot near Kanke Dam.", 4.1, crowdLevel = "Medium"),
    Destination(7, "Tagore Hill", "Ranchi, Jharkhand", "Hill / Viewpoint", 23.3760, 85.3120, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg",
        "Scenic hill in Morabadi named after Rabindranath Tagore's brother. Beautiful sunrise/sunset views.", 4.2, crowdLevel = "Low"),
    Destination(8, "Jagannath Temple", "Ranchi, Jharkhand", "Temple", 23.3167, 85.2814, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/1280px-Jagannath_Temple%2C_Ranchi.jpg",
        "17th-century temple built in 1691 by Ani Nath Shahdeo, resembling Jagannath Temple of Puri. Annual Rath Yatra.", 4.5, crowdLevel = "Medium"),
    Destination(9, "Pahari Mandir", "Ranchi, Jharkhand", "Temple / Hill", 23.3650, 85.3140, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG/960px-Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG",
        "Shiva temple atop Ranchi Hill. Panoramic views of the city from the top.", 4.3, crowdLevel = "Low"),
    Destination(10, "Patratu Valley", "Ramgarh, Jharkhand", "Valley / Dam", 23.5900, 85.3000, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg",
        "Scenic valley with dam, red soil hills, forest canopy. Popular for road trips and boating.", 4.5, crowdLevel = "Medium"),
    Destination(11, "Netarhat", "Latehar, Jharkhand", "Hill Station", 23.4700, 84.2600, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg",
        "Queen of Chotanagpur at 1071m. Famous for Magnolia sunset point, sunrise point, pine forests. 156km from Ranchi.", 4.7, ecoCertified = true, crowdLevel = "Low"),
    Destination(12, "Betla National Park", "Latehar, Jharkhand", "Wildlife / National Park", 23.8870, 84.1900, "₹250 (Indians)", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg",
        "Jharkhand's only national park, 226 km². Part of Palamu Tiger Reserve. Tigers, elephants, leopards. 170km from Ranchi.", 4.4, ecoCertified = true, crowdLevel = "Low"),
    Destination(13, "Parasnath Hill (Shikharji)", "Giridih, Jharkhand", "Pilgrimage / Hill", 23.9611, 86.1371, "Free", "October - March",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg",
        "Highest peak in Jharkhand at 1365m. Holiest Jain pilgrimage site — 20 of 24 Tirthankaras attained nirvana here.", 4.6, crowdLevel = "Medium"),
    Destination(14, "Baidyanath Temple (Deoghar)", "Deoghar, Jharkhand", "Pilgrimage", 24.4925, 86.7000, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg",
        "One of 12 Jyotirlingas of Lord Shiva. Destination of Shravani Mela — world's largest pedestrian pilgrimage (8-10M devotees).", 4.8, crowdLevel = "High"),
    Destination(15, "Gonda Hill", "Ranchi, Jharkhand", "Hill", 23.3670, 85.2980, "Free", "All year",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Gonda_Hill_-_Ranchi_9290.JPG/960px-Gonda_Hill_-_Ranchi_9290.JPG",
        "Hill whose rocks were used to build Rock Garden. Located on Ranchi-Kanke Road with Kanke Dam at its base.", 4.0, crowdLevel = "Low")
)
```

---

## 10. REAL HOTELS

```kotlin
data class Hotel(
    val id: Int, val name: String, val city: String, val pricePerNight: Int,
    val rating: Double, val amenities: List<String>, val imageUrl: String,
    val ecoCertified: Boolean = false, val lat: Double, val lng: Double
)

val jharkhandHotels = listOf(
    Hotel(1, "Radisson Hotel Ranchi", "Ranchi", 6500, 4.5, listOf("WiFi","Pool","Restaurant","Gym","Parking"), "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg", lat = 23.3630, lng = 85.3180),
    Hotel(2, "Capitol Residency Hotel", "Ranchi", 5000, 4.3, listOf("WiFi","Restaurant","Bar","Parking"), "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/960px-Jagannath_Temple%2C_Ranchi.jpg", lat = 23.3500, lng = 85.3100),
    Hotel(3, "Hotel AVN Grand", "Ranchi", 3500, 4.2, listOf("WiFi","Restaurant","Parking"), "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/960px-Dassam_falls.jpg", lat = 23.3550, lng = 85.3250),
    Hotel(4, "Hotel Green Acres", "Ranchi", 3000, 4.0, listOf("WiFi","Restaurant","Parking","EV Charging"), "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg", ecoCertified = true, lat = 23.3600, lng = 85.3050),
    Hotel(5, "Hotel Birsa Vihar", "Netarhat", 2500, 3.9, listOf("WiFi","Restaurant"), "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/960px-Sunset_in_netarhatt%2C_jharkhand.jpg", lat = 23.4700, lng = 84.2600),
    Hotel(6, "Eco-Lodge Betla", "Betla", 1800, 4.3, listOf("Solar Power","EV","Organic Food","WiFi"), "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/960px-Monkey_in_betla_park.jpg", ecoCertified = true, lat = 23.8870, lng = 84.1900),
    Hotel(7, "Netarhat Tourist Lodge", "Netarhat", 1500, 3.7, listOf("Basic","Govt-run"), "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/500px-Sunset_in_netarhatt%2C_jharkhand.jpg", lat = 23.4710, lng = 84.2610),
    Hotel(8, "Patratu Riverside Camp", "Patratu", 2000, 4.4, listOf("Tents","Bonfire","Stargazing","EV"), "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/960px-Patratu_dam.jpg", ecoCertified = true, lat = 23.5900, lng = 85.3000),
    Hotel(9, "Tribal Homestay Khunti", "Khunti", 1200, 4.5, listOf("Home-cooked Food","Cultural Experience","WiFi"), "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg", ecoCertified = true, lat = 22.9447, lng = 85.2547)
)
```

---

## 11. REAL FLIGHTS TO/FROM RANCHI (IXR)

| From | To | Airlines | Duration | Price (₹) |
|------|-----|----------|----------|-----------|
| Delhi (DEL) | Ranchi (IXR) | IndiGo, Air India, Vistara | 1h 50m | 4,500-7,000 |
| Kolkata (CCU) | Ranchi (IXR) | IndiGo, SpiceJet | 1h 05m | 3,000-5,000 |
| Mumbai (BOM) | Ranchi (IXR) | IndiGo, Air India | 2h 15m | 5,000-8,000 |
| Bengaluru (BLR) | Ranchi (IXR) | IndiGo | 2h 30m | 5,500-9,000 |
| Patna (PAT) | Ranchi (IXR) | IndiGo | 1h 00m | 2,500-4,000 |

---

## 12. REAL JHARKHAND FESTIVALS & EVENTS

```kotlin
data class Event(val id: Int, val name: String, val month: String, val location: String, val description: String, val imageUrl: String)

val jharkhandEvents = listOf(
    Event(1, "Sohrai Painting Festival", "October - November", "Hazaribagh, Jharkhand", "Cattle festival celebrating the harvest. Women paint vibrant Sohrai art on mud walls — a GI-tagged tribal art form.", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"),
    Event(2, "Adivasi Art Exhibition", "Year-round", "Hazaribagh, Jharkhand", "Tribal women artists showcase traditional mural art. Exhibited internationally at Museum Rietberg, Zurich.", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"),
    Event(3, "Sarhul", "March - April", "Statewide, Jharkhand", "Spring festival celebrating the bloom of the Sal tree. Tribal communities worship nature with dance, music, and flowers.", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"),
    Event(4, "Karma Festival", "August", "Statewide, Jharkhand", "Tribal festival worshipping the Karma tree. Features the Karma dance performed through the night by tribal youth.", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"),
    Event(5, "Jharkhand Foundation Day", "November 15", "Statewide, Jharkhand", "Celebrating the formation of Jharkhand state in 2000. Cultural events, exhibitions, and tribal performances.", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg")
)
```

---

## 13. REAL JHARKHAND SOUVENIRS / HANDICRAFTS

```kotlin
data class Souvenir(val id: Int, val name: String, val category: String, val artisan: String, val price: Int, val imageUrl: String, val description: String)

val jharkhandSouvenirs = listOf(
    Souvenir(1, "Dokra Metal Figurine", "Dokra Craft", "Malhar Artisans, Ranchi", 1500, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/500px-Village_lady_grinding_ants_for_her_family.jpg", "Handcrafted using the 4000-year-old lost-wax casting technique. Each piece is unique."),
    Souvenir(2, "Sohrai Wall Painting (Canvas)", "Sohrai Art", "Tribal Women Artists, Hazaribagh", 3000, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg", "GI-tagged tribal mural art from Hazaribagh. Traditionally painted on mud walls during Sohrai festival, now on canvas."),
    Souvenir(3, "Adivasi Tribal Art Canvas", "Tribal Art", "TWAC Cooperative, Hazaribagh", 2500, "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg", "Tribal art by women artists. Exhibited at Museum Rietberg, Zurich."),
    Souvenir(4, "Tussar Silk Saree", "Tussar Silk", "Kharsawan Weavers", 5000, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/400px-Village_lady_grinding_ants_for_her_family.jpg", "Pure tussar silk saree handwoven by Jharkhand tribal weavers. Known for its natural golden texture."),
    Souvenir(5, "Lac Bangles Set", "Lac Jewellery", "Statewide Artisans", 250, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/300px-Village_lady_grinding_ants_for_her_family.jpg", "Traditional lac bangles crafted by Jharkhand artisans. Available in vibrant tribal colours."),
    Souvenir(6, "Tribal Beaded Necklace", "Tribal Jewellery", "Santhal Community", 500, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/350px-Village_lady_grinding_ants_for_her_family.jpg", "Handmade beaded necklace by Santhal tribal artisans. Traditional design with natural materials.")
)
```

---

## 14. HELITOURISM PACKAGES

| Package | Route | Duration | Price (₹) | Highlights |
|---------|-------|----------|-----------|------------|
| Ranchi Valley Skyview | Ranchi → Patratu Valley → return | 30 min | 4,500 | Patratu Dam, red soil hills, forest canopy |
| Waterfall Circuit | Ranchi → Dassam → Hundru → Jonha → return | 45 min | 6,500 | Three waterfalls from above, Subarnarekha river |
| Netarhat Sunrise | Ranchi → Netarhat → return | 60 min | 8,000 | Netarhat sunset/sunrise point, pine forests, hills |
| Betla Wildlife Safari Air | Ranchi → Betla National Park → return | 75 min | 10,000 | Betla forest, wildlife from air, Palamu fort ruins |

---

## 15. AUDIO GUIDE CONTENT

| Destination | Duration | Content Summary |
|-------------|----------|-----------------|
| Dassam Falls | 4 min | History, legend of Dassam (ten streams), best viewpoints, safety tips |
| Hundru Falls | 4 min | Subarnarekha River, 98m drop, monsoon vs dry season |
| Jonha Falls | 3 min | Gautamdhara name, Buddhist shrine, surrounding forest |
| Panch Ghagh Falls | 3 min | Five streams, why it's a great eco-alternative to Hundru |
| Rock Garden | 3 min | Carved from Gonda Hill, design, waterfall inside |
| Sun Temple | 4 min | Konark-inspired architecture, chariot of the Sun God |
| Tagore Hill | 2 min | Connection to Rabindranath Tagore, Ramakrishna Mission Ashram |
| Patratu Valley | 4 min | The dam, valley view, red hills, boating |
| Netarhat | 5 min | Queen of Chotanagpur, sunrise/sunset, pine forests |
| Betla National Park | 5 min | Tigers, elephants, first tiger reserves, safari info |
| Jagannath Temple | 3 min | 17th century, Rath Yatra in Ranchi |
| Baidyanath Temple | 5 min | One of 12 Jyotirlingas, Shravani Mela |
| Parasnath Hill | 4 min | Highest peak, Jain pilgrimage, 23 Tirthankaras |

**Implementation:** Android TextToSpeech (TTS) with language selector (English/हिंदी/संथाली). Mini player bar at bottom (play/pause, seek, 15s skip). Pre-recorded .mp3 files if available, else TTS reads `audioGuideText` script.

---

## 16. MAPS INTEGRATION

- **SDK:** Google Maps Compose (`com.google.maps.android:maps-compose`)
- **API key:** in `AndroidManifest.xml` meta-data, read from `local.properties` / `BuildConfig`
- **Default camera:** Ranchi (lat: 23.3441, lng: 85.3096), zoom 10
- **Markers:** all 15 destinations with custom `MarkerState`
- **Marker tap → floating info card:** name, thumbnail, rating, "Navigate" button
- **Navigate intent:**
```kotlin
fun navigateTo(context: Context, lat: Double, lng: Double, label: String) {
    val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng($label)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply { setPackage("com.google.android.apps.maps") }
    if (mapIntent.resolveActivity(context.packageManager) != null) context.startActivity(mapIntent)
}
```
- **User location:** request `ACCESS_FINE_LOCATION`, show blue dot, default to Ranchi if denied
- **Clustering:** when zoomed out, cluster markers

---

## 17. EMAIL & SHARE INTEGRATION

### Itinerary email
```kotlin
fun shareItinerary(context: Context, trip: Trip) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_SUBJECT, "My JharVista Trip: ${trip.name}")
        putExtra(Intent.EXTRA_TEXT, formatItineraryEmail(trip))
    }
    context.startActivity(Intent.createChooser(intent, "Share Itinerary via"))
}
```

### Email body format
```
🌍 My JharVista Trip: Ranchi Waterfall Circuit

📅 Dates: Oct 12 – Oct 14 (3 days)
💰 Budget: ₹12,000 (Standard)
🌿 Carbon Footprint: 6.3 kg CO₂

Day 1 (Oct 12):
 • 09:00 — Dassam Falls (Ranchi)
 • 12:00 — Lunch at local homestay
 • 15:00 — Rock Garden
 • Stay: Hotel AVN Grand

Day 2 (Oct 13):
 • 08:00 — Hundru Falls
 • 12:00 — Jonha Falls
 • 16:00 — Sun Temple sunset
 • Stay: Hotel Green Acres (Eco-Certified)

Day 3 (Oct 14):
 • 09:00 — Panch Gagh Falls
 • 13:00 — Birsa Zoological Park
 • 17:00 — Departure

Total: ₹11,850 | 82% goes to local providers

Planned with JharVista AI — Jharkhand Tourism 🌿
```

### Destination share
Uses `Intent.ACTION_SEND` with `text/plain` for WhatsApp/Instagram, or `image/*` with image attachment.

### Sidebar "Share on Social Media" screen
3 options: Share Trip Itinerary, Share Destination, Share JharVista App. Share target grid: WhatsApp, Instagram, Facebook, Email, Telegram, Copy Link.

---

## 18. AUTHENTICATION SPEC

### Option A: Firebase Auth (preferred)
- Email/Password sign-in
- Google Sign-In (`play-services-auth`)
- `google-services.json` in `android/app/`
- Session persisted via Firebase

### Option B: Local Room-based (if no Firebase)
```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val passwordHash: String,
    val photoUrl: String? = null
)

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("jharvista", Context.MODE_PRIVATE)
    fun saveUser(email: String) { prefs.edit().putString("user_email", email).apply() }
    fun isLoggedIn() = prefs.contains("user_email")
    fun logout() { prefs.edit().clear().apply() }
}
```

### User model
```kotlin
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
    val phone: String? = null,
    val language: String = "English",
    val ecoMode: Boolean = true,
    val notificationsEnabled: Boolean = true
) {
    val initials: String get() {
        val parts = name.trim().split(" ")
        return if (parts.size >= 2) "${parts[0].first()}${parts[1].first()}".uppercase()
        else if (name.isNotEmpty()) name.take(2).uppercase() else "JV"
    }
}
```

### Profile avatar on Home
- 40dp circular, lime bg `#C6F432`
- If user has photoUrl → `AsyncImage` with Coil
- Else → initials text (forest green, 16sp bold)
- Tap → ProfileScreen

---

## 19. AI INTEGRATION PLAN (GEMINI)

### Itinerary generation
- **Input:** free-text dream trip + structured constraints (destination, dates, duration, budget ₹, style)
- **Gemini task:** produce day-by-day itinerary (JSON: days[], each with morning/afternoon/evening slots, place name from Jharkhand list, reason, estimated cost ₹, eco-score, carbon kg CO₂)
- **Output:** rendered into Trip Timeline screen, editable

### Conversational planning
- Multi-turn chat. User refines ("make day 2 more relaxed", "swap hotel for eco-lodge")
- Gemini returns updated structured itinerary

### Recommendation reasoning
- For each curated card, Gemini supplies one-line "why this for you" reason

### Eco-alternative suggestions
- When footfall high, Gemini proposes 1-2 lesser-known Jharkhand alternatives (e.g., Panch Gagh instead of Hundru)

### Smart defaults
- Pre-fill booking from user's location (Ranchi), past trips, budget tier

---

## 20. RESPONSIBLE-TOURISM LAYER

### 20.1 Footfall-aware discovery
- Each destination has `crowdLevel` (Low/Medium/High) + `predictedFootfall(date)`
- Weekends/holidays → High for popular spots (Hundru, Dassam)
- When High → show "Busy on these dates" hint + surface alternatives (Panch Gagh for Hundru)
- Color-coded: green (low), orange (medium), red (high)

### 20.2 Eco-certified inventory
- Hotels/transport carry `ecoCertified` flag + `ecoScore` (0-100)
- Eco items get green badge, sorted up when eco-preference enabled

### 20.3 Transparent pricing
- Booking breakdown: provider share, platform fee, taxes, local-community %
- Example: "₹2,000 → ₹1,600 (80%) goes directly to local homestay owner"

### 20.4 Carbon footprint
- Each trip leg shows estimated CO₂ (e.g., "2.1 kg CO₂")
- EV/train alternatives badged and offered as swaps

### 20.5 Verified ratings
- Ratings tied to verified bookings only. "Verified" tag shown.

---

## 21. TECHNOLOGY STACK & MODULE STRUCTURE

### Stack
- **Language:** Kotlin 2.x
- **UI:** Jetpack Compose + Material 3
- **Navigation:** Compose Navigation (single-activity) + drawer for sidebar
- **State:** StateFlow / ViewModel
- **Local persistence:** Room
- **Networking:** Retrofit + kotlinx.serialization
- **AI:** Google AI Studio / Gemini SDK
- **Maps:** Google Maps Compose SDK
- **DI:** Hilt
- **Images:** Coil
- **Audio:** ExoPlayer / MediaPlayer + Android TTS
- **Email/Share:** Android Intent.ACTION_SEND
- **Auth:** Firebase Auth OR local Room-based stub
- **Payments:** tokenised stub

### Module structure
```
com.jharvista.app
├── MainActivity.kt
├── JharVistaApplication.kt
├── ui/
│   ├── theme/           (Color.kt, Type.kt, Shape.kt, Theme.kt)
│   ├── navigation/      (JharVistaNavHost.kt, Destinations.kt)
│   ├── auth/            (WelcomeScreen.kt, LoginScreen.kt, SignupScreen.kt)
│   ├── home/            (HomeScreen.kt, HomeViewModel.kt)
│   ├── plan/            (AIPlanningScreen.kt, PlanningViewModel.kt)
│   ├── booking/         (BookingConfigScreen.kt, FlightSearchScreen.kt)
│   ├── discovery/       (MapDiscoveryScreen.kt, DestinationDetailScreen.kt)
│   ├── trips/           (MyTripsScreen.kt, TripTimelineScreen.kt)
│   ├── wallet/          (WalletScreen.kt)
│   ├── profile/         (ProfileScreen.kt, EditProfileScreen.kt)
│   ├── sidebar/         (JharkhandGlanceScreen.kt, EventsScreen.kt, HospitalityScreen.kt,
│   │                      SouvenirsScreen.kt, HeliTourismScreen.kt, AudioGuideScreen.kt, ShareScreen.kt)
│   └── components/      (DestinationCard.kt, TripPlannerWidget.kt, EcoBadge.kt, VirtualCard.kt,
│                         AudioPlayerBar.kt, FootfallIndicator.kt, SidebarDrawer.kt, ProfileAvatar.kt,
│                         AppleButton.kt, AppleTextField.kt, AppleSettingsRow.kt, AppleBottomNav.kt,
│                         ChatBubble.kt, FloatingSearchBar.kt, FloatingBackButton.kt, HairlineSeparator.kt)
├── domain/
│   ├── model/           (Trip, Itinerary, Destination, Hotel, Flight, Event, Souvenir,
│   │                      HeliTourPackage, PaymentCard, EcoInfo, AudioGuide, User)
│   └── usecase/         (GenerateItineraryUseCase, SearchFlightsUseCase,
│                         GetEcoAlternativesUseCase, ShareItineraryUseCase,
│                         NavigateToDestinationUseCase, PlayAudioGuideUseCase)
└── data/
    ├── remote/          (GeminiService.kt, BookingApi.kt)
    ├── local/           (TripDao.kt, UserDao.kt, AppDatabase.kt, SessionManager.kt)
    ├── repository/      (TripRepositoryImpl.kt, DestinationRepositoryImpl.kt,
    │                     HotelRepositoryImpl.kt, EventRepositoryImpl.kt,
    │                     SouvenirRepositoryImpl.kt, HeliTourRepositoryImpl.kt, AuthRepositoryImpl.kt)
    └── seed/            (JharkhandData.kt — ALL real data from Sections 9-15)
```

---

## 22. APPLE UI RULES — DO'S AND DON'TS

### DO:
- ✅ Use `#F2F2F7` for grouped/settings backgrounds
- ✅ Use 0.5dp hairline separators (`#E5E5EA`) between list items
- ✅ Body text at 17sp (Apple standard)
- ✅ 20dp screen margins
- ✅ Forest green `#0B3D2E` for primary actions
- ✅ Apple system colors for semantic UI (green=success, red=destructive, blue=link)
- ✅ Buttons 52dp tall, 14dp radius
- ✅ Circular avatars (full clip)
- ✅ Grabber handles on bottom sheets (4×40dp grey pill)
- ✅ Disable ripple on ALL touchable elements
- ✅ 12dp radius on grouped list cards
- ✅ iOS-style chevron (`>`) for trailing indicators
- ✅ Colored 28dp squircle (7dp radius) icons in settings rows
- ✅ Subtle shadow only on floating elements (search bars, back buttons)

### DON'T:
- ❌ NO Material ripple effects
- ❌ NO card elevation/shadows (except floating elements)
- ❌ NO bright Material colors — use Apple system colors
- ❌ NO `TopAppBar` from Material3 — build custom top bars
- ❌ NO `FloatingActionButton` — use custom circular buttons
- ❌ NO borders on cards — use background contrast
- ❌ NO Material `Switch` default colors — use Apple green `#34C759`
- ❌ NO `Snackbar` — use Apple-style toast (black pill, white text, centered)
- ❌ NO Material `Chip` — use custom pill composables
- ❌ NO `subtitle1`/`caption` Material typography — use custom Apple typography scale

---

## 23. REFERENCE GITHUB REPOSITORIES

> **These are real, working repos that Google AI Studio can reference for architecture and patterns.**

| Repo | URL | Why it's useful |
|------|-----|-----------------|
| ExploreEase | `github.com/sksinha2410/ExploreEase` | Kotlin + Compose + Gemini + Firebase Auth + Google Maps + Profile + Trip Planning. Closest match. |
| Bharat Bhraman | `github.com/shahilkhan001/Bharat-Bhraman` | Kotlin + Firebase + Gemini API + Google Maps. AI monument scanning, multilingual. |
| Voyexa Travel Planner | `github.com/debasrita15/Voyexa-Travel-Planner` | Kotlin + Compose + Clean Architecture + AI (Groq). Premium architecture reference. |
| Travenor | `github.com/KhanMubashshirAzeem/Travenor` | Kotlin + Compose + MVVM + Supabase + Coil. Splash + SignIn + Home + Destination cards. |
| Namaste Jharkhand | `github.com/abhisheknaik2k20/namaste-jharkhand` | Jharkhand-specific tourism app (Flutter). AI itinerary, multilingual chatbot, Maps. Data reference. |
| TourismApp-JetpackCompose | `github.com/rrdhoi/TourismApp-JetpackCompose` | Kotlin + Compose + MVVM. Simple tourism UI reference. |

**Recommended:** Clone `ExploreEase` as the base — it already has login, AI chatbot, maps, profiles, and trip planning in Kotlin + Compose. Add JharVista data, sidebar, and Apple UI on top.

---

## 24. ACCEPTANCE CRITERIA

The generated app is "done" when:

1. App launches → **JharVista Welcome screen** (splash with logo + Dassam Falls photo) → **Login screen** → Home
2. Login works (email/password or Google). Session persists across restarts.
3. Home screen shows "JharVista" branding, "Ranchi, Jharkhand" location, user avatar (initials/photo) top-right
4. Tapping avatar → **Apple-style Profile screen** (grouped settings, hairline separators, colored icons, logout)
5. **Sidebar drawer** opens via hamburger/swipe — 7 items in exact order, exact labels, dark navy bg
6. Each sidebar item navigates to its screen with **real Jharkhand data**
7. **Map screen** renders live Google Maps with markers for all 15 destinations, tap → info card → Navigate
8. **AI Planning screen** calls Gemini and renders a real Jharkhand itinerary into Trip Timeline
9. **Hospitality screen** shows real hotels with prices, ratings, "Book Now"
10. **Flight search** shows real airlines and routes to/from Ranchi (IXR)
11. **Souvenirs screen** shows real handicrafts with prices and artisan info, cart works
12. **Audio Guide** plays audio for each destination (TTS)
13. **HeliTourism** shows 4 helicopter packages with real routes
14. **Email sharing** opens email intent with formatted itinerary
15. **Eco overlay** shows: eco badges, footfall indicators, carbon figures, price breakdowns
16. **My Trips** persists in Room across restarts
17. **UI looks like Apple** — no ripples, hairline separators, 17sp body, `#F2F2F7` backgrounds, 20dp margins
18. **No hardcoded secrets** — Gemini key via BuildConfig, Maps key in manifest
19. **Every button works** — no dead ends, no placeholders
20. **App name is JharVista** everywhere

---

## 25. OUT OF SCOPE (v1)

- Real payment processing / PCI scope (tokenised stub)
- iOS / web (Android only)
- Full blockchain backend (model data shape only)
- Social / community feed
- Offline AI generation (requires connectivity for Gemini)
- Real hotel/flight live inventory API (use seed data; pluggable later)

---

*END OF MASTER SPECIFICATION. Hand this single document to Google AI Studio. Instruct: "Generate the complete, fully-working native Android Kotlin + Jetpack Compose project per ALL sections of this document. App name is JharVista. Follow Apple UI rules. Use real Jharkhand data and image URLs. Sidebar must have exactly 7 items. Every screen must be functional."*
