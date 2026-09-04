# TRU App — Sidebar Specification Document

**Document type:** Complete sidebar specification for Google AI Studio code generation
**App name:** TRU — AI-Powered Jharkhand Tourism Companion
**Target platform:** Native Android (Kotlin + Jetpack Compose)
**Purpose:** This document defines ONLY the sidebar — every item, what it does, what data it shows, what photos it uses, and how it works. Google AI Studio must implement this sidebar EXACTLY as specified.

---

## 1. Sidebar Overview

The sidebar is the primary secondary-navigation surface of the TRU app. It slides in from the left edge as a drawer (opened via hamburger icon in the top bar or a left-edge swipe gesture). It contains exactly **7 items** — no more, no less. The order, labels, and icons must match the original Visily prototype inch-to-inch.

### Visual Design

| Property | Value |
|----------|-------|
| Background colour | Dark navy / charcoal (`#2C333D` or `#1A2332`) |
| Text colour | Pure white (`#FFFFFF`) |
| Icon colour | Pure white (`#FFFFFF`) |
| Icon style | Line-art / outlined Material icons |
| Icon size | 24dp |
| Text size | 16sp, Medium weight |
| Item height | 56dp |
| Item padding (horizontal) | 20dp left, 16dp right |
| Item padding (vertical) | 16dp between items |
| Drawer width | 280dp (full-height) |
| Slide animation | 300ms, `FastOutSlowInEasing`, slides in from left |
| Active item indicator | Subtle lighter background tint (`#3A4555`) or a 4dp lime accent bar on the left edge |
| Back/close arrow | Top-left, white left-pointing chevron, 24dp — closes the drawer |
| Header (optional) | TRU logo or "Jharkhand Tourism" text at the top, white, 18sp, semibold |
| Footer (optional) | "Powered by TRU AI" text at the bottom, white 60% opacity, 12sp |

### Interaction Rules

- The sidebar is opened by tapping the hamburger icon in the top app bar OR by swiping right from the left screen edge.
- The sidebar is closed by tapping the back arrow, tapping outside the drawer, or swiping left.
- Only ONE item can be active at a time.
- Tapping an item closes the drawer and navigates to the corresponding screen.
- The sidebar is available on ALL main screens (Home, Plan, Trips, Wallet) via the hamburger icon.
- The sidebar is NOT a bottom navigation replacement — it coexists with the bottom nav bar (Home / Plan / Trips / Wallet). The sidebar holds Jharkhand-specific content sections; the bottom nav holds the core travel workflow.

---

## 2. The 7 Sidebar Items (Exact Order, Exact Labels)

> **DO NOT rename, reorder, add, or remove any item. This list is final.**

| # | Label | Icon (Material Compose) |
|---|-------|------------------------|
| 1 | Jharkhand at a Glance | `Icons.Filled.AccountBalance` (or `Icons.Filled.Tour`) |
| 2 | Events | `Icons.Filled.CalendarMonth` (or `Icons.Filled.Event`) |
| 3 | Hospitality Services | `Icons.Filled.Hotel` (or `Icons.Filled.Bed`) |
| 4 | Jharkhand Souvenirs | `Icons.Filled.ShoppingBag` |
| 5 | HeliTourism | `Icons.Filled.Flight` (or a custom helicopter vector asset) |
| 6 | Audio Guide | `Icons.Filled.Headphones` (or `Icons.Filled.GraphicEq`) |
| 7 | Share on Social Media | `Icons.Filled.Share` |

---

## 3. Item-by-Item Specification

---

### Item 1: Jharkhand at a Glance

**Label:** "Jharkhand at a Glance"
**Icon:** Tower / Monument — `Icons.Filled.AccountBalance`
**Navigates to:** `JharkhandGlanceScreen`

#### What this screen does
This is the overview dashboard for the entire state of Jharkhand. It gives the user a quick, visual snapshot of what Jharkhand offers — its geography, key statistics, must-visit destinations, and cultural identity. Think of it as the "landing page" for the state inside the app.

#### What it shows (components, top to bottom)

1. **Hero image banner** — full-width, 220dp height, rounded bottom corners (24dp). Real photo of a iconic Jharkhand landscape (Dassam Falls or Patratu Valley or Netarhat sunset). Overlay text at bottom-left: "Jharkhand" in 28sp bold white, with a subtitle "The Land of Forests" in 14sp white.

2. **Quick facts grid** — 2×3 grid of stat cards. Each card: white background, 16dp radius, 12dp padding, icon + number + label. Real data:
   - **Capital:** Ranchi (icon: `Icons.Filled.LocationCity`)
   - **Area:** 79,716 km² (icon: `Icons.Filled.Map`)
   - **Districts:** 24 (icon: `Icons.Filled.GridOn`)
   - **Waterfalls:** 10+ major (icon: `Icons.Filled.WaterDrop`)
   - **Wildlife Sanctuaries:** 3 (icon: `Icons.Filled.Pets`)
   - **Tribal Communities:** 32+ (icon: `Icons.Filled.Groups`)
   - **National Parks:** 1 — Betla (icon: `Icons.Filled.Forest`)
   - **Languages:** Hindi, Santhali, Mundari, Ho, Kurukh (icon: `Icons.Filled.Translate`)
   - **Best Time to Visit:** October–March (icon: `Icons.Filled.WbSunny`)

3. **"About Jharkhand" section** — a short paragraph (3–4 sentences) describing Jharkhand: its name meaning ("Land of Forests"), its creation in 2000 from Bihar, its rich mineral wealth and dense forests, its tribal heritage (Santhal, Munda, Oraon, Ho communities), and its tourism potential (waterfalls, hills, wildlife, temples).

4. **"Must Visit Destinations" carousel** — horizontal scroll of destination cards. Each card: 160dp wide, 200dp tall, 16dp radius, real photo of the destination, destination name overlaid at bottom. Cards (in this order):
   - Dassam Falls (Ranchi) — real photo of Dassam Falls
   - Hundru Falls (Ranchi) — real photo of Hundru Falls
   - Patratu Valley (Ramgarh) — real photo of Patratu Valley
   - Netarhat (Latehar) — real photo of Netarhat sunset point
   - Betla National Park (Latehar) — real photo of Betla wildlife
   - Sun Temple (Ranchi) — real photo of Sun Temple Ranchi
   - Rock Garden (Ranchi) — real photo of Rock Garden
   - Jonha Falls (Ranchi) — real photo of Jonha Falls

5. **"Cultural Heritage" section** — a section with 2–3 cards highlighting Jharkhand's tribal culture:
   - Tribal Art: Sohrai & Paitkar paintings — real photo of Sohrai wall painting
   - Tribal Festivals: Sarhul, Karma, Sohrai — real photo of a tribal festival
   - Tribal Music & Dance: Chhau dance, Jhumar — real photo of Chhau dance

6. **"How to Reach" section** — small info card:
   - **By Air:** Birsa Munda Airport, Ranchi (IXR) — flights from Delhi, Kolkata, Mumbai, Bengaluru, Patna
   - **By Rail:** Ranchi Junction (RNC), Hatia (HTE), Dhanbad (DHN) — well-connected to major cities
   - **By Road:** NH-33, NH-31, NH-20 — bus services from neighbouring states

#### Data source
`JharkhandData.kt` — static curated content with real facts. All images loaded via Coil from real URLs (Wikimedia Commons / Jharkhand Tourism official) or bundled assets.

#### Required photos (ALL must be real, original photos of Jharkhand)
- Dassam Falls (hero or carousel)
- Hundru Falls (carousel)
- Patratu Valley (carousel)
- Netarhat sunset (carousel)
- Betla National Park (carousel)
- Sun Temple Ranchi (carousel)
- Rock Garden Ranchi (carousel)
- Jonha Falls (carousel)
- Sohrai painting (cultural section)
- Tribal festival photo (cultural section)
- Chhau dance photo (cultural section)

---

### Item 2: Events

**Label:** "Events"
**Icon:** Calendar — `Icons.Filled.CalendarMonth`
**Navigates to:** `EventsScreen`

#### What this screen does
This screen shows a chronological list of upcoming festivals, fairs, and cultural events across Jharkhand. The user can browse events by month, see event details, and add an event to their trip.

#### What it shows (components, top to bottom)

1. **Header** — "Events & Festivals" title, 22sp semibold. Subtitle: "Experience the culture of Jharkhand."

2. **Month filter chips** — horizontal scroll of pill chips: All | January | March | August | October | November. Active chip = lime background.

3. **Event list** — vertical list of event cards. Each card: full-width, 16dp radius, white bg, 16dp padding. Layout:
   - Left: event image (80×80dp, 12dp radius) — real photo of the festival
   - Right (top): event name, 16sp semibold
   - Right (middle): date + location, 13sp, grey
   - Right (bottom): short description (1 line), 13sp
   - Bottom-right: "Add to Trip" button (outlined, small)

4. **Events data (real Jharkhand festivals):**

| Event Name | Month | Location | Description | Image |
|------------|-------|----------|-------------|-------|
| Tusu Parab | January | Singhbhum | Harvest festival with colourful Tusu art and fairs | Real photo of Tusu festival |
| Sarhul | March–April | Statewide | Spring festival celebrating the Sal tree bloom; tribal communities worship nature | Real photo of Sarhul celebration |
| Karma Festival | August | Statewide | Tribal festival of karma tree worship, with dance and music | Real photo of Karma dance |
| World Tribal Day | August 9 | Ranchi | Celebration of indigenous heritage with cultural programs | Real photo of tribal day celebration |
| Sohrai | October–November | Statewide | Cattle festival with traditional Sohrai wall paintings | Real photo of Sohrai painting |
| Bandna | November | Statewide | Festival honouring cattle and agriculture | Real photo of Bandna festival |
| Jharkhand Foundation Day | November 15 | Statewide | State formation day with cultural events, exhibitions, and fireworks | Real photo of Jharkhand Day |

5. **"Past Events" section** (collapsed) — a collapsible section showing recently concluded events for reference.

#### Data source
`EventRepository` — list of real Jharkhand festivals with name, month, location, description, and image URL.

#### Required photos (ALL real)
- Tusu Parab festival photo
- Sarhul celebration photo
- Karma festival dance photo
- World Tribal Day celebration photo
- Sohrai painting / festival photo
- Bandna festival photo
- Jharkhand Foundation Day celebration photo

---

### Item 3: Hospitality Services

**Label:** "Hospitality Services"
**Icon:** Bed / Hotel — `Icons.Filled.Hotel`
**Navigates to:** `HospitalityScreen`

#### What this screen does
This is the hotel and accommodation booking screen. It lists real hotels, lodges, homestays, and eco-resorts across Ranchi and Jharkhand. The user can filter by city, price, rating, and eco-certification, then book a room.

#### What it shows (components, top to bottom)

1. **Header** — "Hospitality Services" title. Subtitle: "Stay comfortably across Jharkhand."

2. **Search bar** — "Search hotels, lodges, homestays..." with search icon.

3. **Filter chips** — horizontal scroll:
   - **City:** All | Ranchi | Netarhat | Betla | Hazaribagh | Deoghar
   - **Price:** All | Budget (₹1,000–2,500) | Mid-range (₹2,500–5,000) | Luxury (₹5,000+)
   - **Rating:** All | 4★+ | 3★+
   - **Eco-Certified:** toggle chip (green outline)

4. **Sort dropdown** — "Sort by: Recommended / Price (low to high) / Price (high to low) / Rating"

5. **Hotel list** — vertical list of hotel cards. Each card layout:
   - **Image** (full-width, 180dp height, 16dp top radius): real photo of the hotel exterior or room
   - **Hotel name** (18sp semibold): e.g., "Radisson Hotel Ranchi"
   - **Location** (13sp grey): e.g., "Ranchi, Jharkhand"
   - **Rating** (star icon + number): e.g., "4.5 ★"
   - **Price** (16sp bold green): e.g., "₹6,500 / night"
   - **Amenities** (row of small chips): WiFi, AC, Restaurant, Parking, Pool, Gym, EV Charging
   - **Eco badge** (if applicable): green badge "Eco-Certified" with leaf icon
   - **"Book Now" button** (lime, full-width, 48dp height, 16dp radius)
   - **"View on Map" link** (text button) — opens map with hotel location pinned

6. **Hotel data (real hotels):**

| Hotel Name | City | Price/Night (₹) | Rating | Amenities | Eco? |
|------------|------|-----------------|--------|-----------|------|
| Radisson Hotel Ranchi | Ranchi | 6,500 | 4.5 | WiFi, Pool, Restaurant, Gym, Parking | No |
| Capitol Residency Hotel | Ranchi | 5,000 | 4.3 | WiFi, Restaurant, Bar, Parking | No |
| Hotel AVN Grand | Ranchi | 3,500 | 4.2 | WiFi, Restaurant, Parking | No |
| Hotel Capitol Hill | Ranchi | 4,000 | 4.1 | WiFi, Restaurant, Parking | No |
| Hotel Green Acres | Ranchi | 3,000 | 4.0 | WiFi, Restaurant, Parking, EV Charging | Yes |
| Hotel Cloud 9 | Ranchi | 3,800 | 4.1 | WiFi, Restaurant, Bar | No |
| Hotel Birsa Vihar | Netarhat | 2,500 | 3.9 | WiFi, Restaurant | No |
| Hotel Vindravani | Betla | 2,000 | 3.8 | Restaurant, Parking | No |
| Eco-Lodge Betla | Betla | 1,800 | 4.3 | Solar Power, EV, Organic Food, WiFi | Yes |
| Netarhat Tourist Lodge | Netarhat | 1,500 | 3.7 | Basic, Govt-run | No |
| Hazaribagh Tourist Lodge | Hazaribagh | 1,500 | 3.7 | Basic, Govt-run | No |
| Treebo Trend Radha Krishna | Ranchi | 2,200 | 4.0 | WiFi, Restaurant | No |
| FabHotel Prime Galaxy | Ranchi | 2,500 | 4.0 | WiFi, Restaurant | No |
| Hotel Akashdeep | Ranchi | 2,800 | 3.9 | WiFi, Restaurant | No |
| TribAL Homestay Khunti | Khunti | 1,200 | 4.5 | Home-cooked food, Cultural experience, WiFi | Yes |
| Patratu Riverside Camp | Patratu | 2,000 | 4.4 | Tents, Bonfire, Stargazing, EV | Yes |

7. **Booking bottom sheet** (on "Book Now" tap) — slides up from bottom:
   - Check-in date picker
   - Check-out date picker
   - Guests stepper
   - Rooms stepper
   - Price summary (nights × price + taxes + fees = total)
   - "Confirm Booking" button → navigates to Wallet for payment

#### Data source
`HotelRepository` — list of real hotels with name, city, price, rating, amenities, eco flag, image URL, latitude, longitude.

#### Required photos (ALL real)
- Radisson Hotel Ranchi exterior/room photo
- Capitol Residency Hotel photo
- Hotel AVN Grand photo
- Each hotel: real exterior or room photo from the hotel's official site / booking platform / Wikimedia
- Eco-Lodge Betla photo
- Netarhat Tourist Lodge photo
- TribAL Homestay Khunti photo (or representative tribal homestay photo)
- Patratu Riverside Camp / tent camping photo

---

### Item 4: Jharkhand Souvenirs

**Label:** "Jharkhand Souvenirs"
**Icon:** Shopping Bag — `Icons.Filled.ShoppingBag`
**Navigates to:** `SouvenirsScreen`

#### What this screen does
This is a marketplace for authentic Jharkhand handicrafts, handlooms, and tribal products. The user can browse by category, view product details, add to cart, and purchase. Proceeds go directly to local artisans.

#### What it shows (components, top to bottom)

1. **Header** — "Jharkhand Souvenirs" title. Subtitle: "Authentic handicrafts by local artisans."

2. **Category chips** — horizontal scroll: All | Dokra Craft | Sohrai Art | Paitkar Painting | Bamboo Craft | Terracotta | Tussar Silk | Lac Bangles | Tribal Jewellery | Wooden Toys

3. **Product grid** — 2-column grid of product cards. Each card:
   - **Image** (square, 16dp radius): real photo of the handicraft product
   - **Product name** (14sp semibold)
   - **Artisan/community** (12sp grey): e.g., "by Malhar Artisans"
   - **Price** (14sp bold green): e.g., "₹1,200"
   - **Eco badge** (if applicable): "Handmade" / "Eco-Friendly"
   - **"Add to Cart" icon button** (small circular, lime)

4. **Product data (real Jharkhand handicrafts):**

| Product | Category | Origin/Community | Price (₹) | Image |
|---------|----------|-----------------|-----------|-------|
| Dokra Horse Figurine | Dokra Craft | Malhar, Bastar | 1,500 | Real photo of Dokra horse |
| Dokra Elephant Statue | Dokra Craft | Malhar | 2,000 | Real photo of Dokra elephant |
| Sohrai Wall Painting (canvas) | Sohrai Art | Hazaribagh | 3,000 | Real photo of Sohrai painting |
| Sohrai Painting (small) | Sohrai Art | Hazaribagh | 800 | Real photo of small Sohrai art |
| Paitkar Scroll Painting | Paitkar Painting | Amadubi | 2,500 | Real photo of Paitkar painting |
| Bamboo Basket | Bamboo Craft | Statewide | 400 | Real photo of bamboo basket |
| Bamboo Wall Hanging | Bamboo Craft | Statewide | 600 | Real photo of bamboo decor |
| Terracotta Pot (medium) | Terracotta | Statewide | 350 | Real photo of terracotta pot |
| Terracotta Diya Set | Terracotta | Statewide | 150 | Real photo of terracotta diyas |
| Tussar Silk Saree | Tussar Silk | Kharsawan | 5,000 | Real photo of tussar silk saree |
| Tussar Silk Dupatta | Tussar Silk | Kharsawan | 2,000 | Real photo of tussar dupatta |
| Lac Bangle Set | Lac Bangles | Statewide | 250 | Real photo of lac bangles |
| Tribal Beaded Necklace | Tribal Jewellery | Santhal community | 500 | Real photo of tribal necklace |
| Tribal Silver Earrings | Tribal Jewellery | Munda community | 700 | Real photo of tribal earrings |
| Wooden Toy Set | Wooden Toys | Statewide | 400 | Real photo of wooden toys |
| Wooden Tribal Mask | Wooden Toys | Statewide | 800 | Real photo of tribal mask |

5. **Cart icon** (top-right of screen) — shows item count badge. Tapping opens the cart screen:
   - List of added items with quantity steppers
   - Subtotal, shipping (free for local artisan direct-ship), total
   - "Fair-Trade Note": "100% of your purchase goes directly to the artisan. No middlemen."
   - "Checkout" button → navigates to Wallet for payment

6. **Artisan spotlight section** — a highlighted card at the bottom featuring one artisan:
   - Artisan photo
   - Name, craft, village
   - Short story (2–3 sentences)
   - "View Their Products" button → filters grid to that artisan

#### Data source
`SouvenirRepository` — list of real Jharkhand handicraft products with name, category, price, artisan, origin, image URL.

#### Required photos (ALL real)
- Dokra horse figurine photo
- Dokra elephant statue photo
- Sohrai painting photo (large and small)
- Paitkar scroll painting photo
- Bamboo basket photo
- Bamboo wall hanging photo
- Terracotta pot photo
- Terracotta diya set photo
- Tussar silk saree photo
- Tussar silk dupatta photo
- Lac bangles photo
- Tribal beaded necklace photo
- Tribal silver earrings photo
- Wooden toys photo
- Tribal wooden mask photo
- Artisan portrait photo (for spotlight)

---

### Item 5: HeliTourism

**Label:** "HeliTourism"
**Icon:** Helicopter — `Icons.Filled.Flight` (or custom helicopter vector asset)
**Navigates to:** `HeliTourismScreen`

#### What this screen does
This screen showcases helicopter tourism packages — aerial sightseeing tours over Jharkhand's most scenic landscapes. The user can browse packages, see the route on a mini-map, check availability, and book a ride.

#### What it shows (components, top to bottom)

1. **Header** — "HeliTourism" title. Subtitle: "See Jharkhand from the sky."

2. **Hero image** — full-width, 200dp height. Real photo of a helicopter flying over a scenic landscape (use a representative helicopter photo with Jharkhand terrain overlay or a real heli-tourism photo if available). Overlay text: "Experience Jharkhand from above."

3. **"How it works" info card** — 3 steps with icons:
   - Step 1: Choose your route
   - Step 2: Select date & passengers
   - Step 3: Board at Ranchi Helipad

4. **Package list** — vertical list of package cards. Each card:
   - **Route name** (18sp semibold): e.g., "Ranchi Valley Skyview"
   - **Route description** (14sp): what you'll see from the air
   - **Duration** (chip): e.g., "30 minutes"
   - **Price** (16sp bold green): e.g., "₹4,500 / person"
   - **Mini-map** (120dp height): shows the route as a polyline on a small Google Map
   - **Highlights** (bullet list): e.g., "Patratu Valley dam", "Red hills", "Forest canopy"
   - **"Book Ride" button** (lime, full-width)

5. **Package data:**

| Package Name | Route | Duration | Price (₹) | Highlights |
|--------------|-------|----------|-----------|------------|
| Ranchi Valley Skyview | Ranchi → Patratu Valley → return | 30 min | 4,500 | Patratu Dam, red soil hills, forest canopy |
| Waterfall Circuit | Ranchi → Dassam Falls → Hundru Falls → Jonha Falls → return | 45 min | 6,500 | Three waterfalls from above, Subarnarekha river |
| Netarhat Sunrise | Ranchi → Netarhat → return | 60 min | 8,000 | Netarhat sunset/sunrise point, pine forests, hills |
| Betla Wildlife Safari Air | Ranchi → Betla National Park → return | 75 min | 10,000 | Betla forest, wildlife from air, Palamu fort ruins |

6. **Safety information section** — collapsible card:
   - Passenger weight limit
   - Weather dependency note
   - Safety gear provided
   - Insurance coverage included
   - Emergency contact number

7. **Booking bottom sheet** (on "Book Ride" tap):
   - Date picker (calendar)
   - Time slot selector (morning / afternoon)
   - Passengers stepper (1–6)
   - Total price
   - "Confirm Booking" → navigates to Wallet

#### Data source
`HeliTourRepository` — list of helicopter tour packages with route, duration, price, highlights, coordinates for mini-map.

#### Required photos (ALL real)
- Helicopter in flight over a scenic landscape (hero image)
- Aerial photo of Patratu Valley
- Aerial photo of a waterfall in Jharkhand (Dassam or Hundru from above)
- Aerial photo of Netarhat / Netarhat sunset point
- Aerial photo of Betla National Park forest
- Helipad photo (Ranchi)

---

### Item 6: Audio Guide

**Label:** "Audio Guide"
**Icon:** Headphones — `Icons.Filled.Headphones`
**Navigates to:** `AudioGuideScreen`

#### What this screen does
This screen provides an audio guide for every tourism destination in the app. The user selects a destination, presses play, and listens to a narrated guide about its history, significance, what to see, and tips. The audio plays in a mini player bar at the bottom of the screen (like a music app), so the user can keep browsing while listening.

#### What it shows (components, top to bottom)

1. **Header** — "Audio Guide" title. Subtitle: "Listen and explore Jharkhand."

2. **Language selector** — segmented control: English | हिंदी | संथाली (Santhali). Default: English.

3. **Search bar** — "Search destinations..." to filter the audio guide list.

4. **"Now Playing" mini player** (visible only when audio is playing, fixed at bottom above bottom nav):
   - Destination thumbnail (40×40dp, 8dp radius)
   - Destination name (14sp semibold)
   - Play/pause toggle button
   - Seek bar (progress)
   - Skip forward/back 15s buttons
   - Close (X) button

5. **Destination audio list** — vertical list of destination cards. Each card:
   - **Thumbnail** (60×60dp, 12dp radius): real photo of the destination
   - **Destination name** (16sp semibold): e.g., "Dassam Falls"
   - **Subtitle** (13sp grey): e.g., "Ranchi • 4 min guide"
   - **Play button** (circular, 44dp, lime): `Icons.Filled.PlayArrow` (or `Pause` if currently playing)
   - **Download icon** (for offline): `Icons.Filled.Download` (optional)

6. **Destination audio data:**

| Destination | Duration | Content Summary |
|-------------|----------|-----------------|
| Dassam Falls | 4 min | History of the falls, the legend of Dassam (ten water streams), best viewpoints, safety tips |
| Hundru Falls | 4 min | How the Subarnarekha River creates the 98m falls, monsoon vs dry season views |
| Jonha Falls | 3 min | Also known as Ghaghri Falls, the surrounding forest, the nearby tourist lodge |
| Panch Gagh Falls | 3 min | Five streams of the waterfall, why it's a great eco-alternative to Hundru |
| Rock Garden | 3 min | How it was carved from rock, the design, the waterfall inside |
| Sun Temple | 4 min | Architecture inspired by Konark, the chariot of the Sun God, best time to visit |
| Tagore Hill | 2 min | Connection to Rabindranath Tagore, the Ramakrishna Mission Ashram |
| Patratu Valley | 4 min | The dam, the valley view, the red hills, boating |
| Netarhat | 5 min | "Queen of Chotanagpur", sunrise/sunset points, pine forests, Magnolia Point |
| Betla National Park | 5 min | Tigers, elephants, history as one of India's first tiger reserves, safari info |
| Jagannath Temple | 3 min | 17th century temple, Rath Yatra celebration in Ranchi |
| Deoghar (Baidyanath) | 5 min | One of the 12 Jyotirlingas, the temple's significance, Shravani Mela |
| Parasnath Hill | 4 min | Highest peak in Jharkhand, Jain pilgrimage site, 23 Tirthankaras |

7. **Implementation:**
   - **Primary method:** Android TextToSpeech (TTS) engine. Each destination has an `audioGuideText` field (a 3–5 paragraph script). When the user presses play, TTS reads the script aloud in the selected language.
   - **Secondary method (preferred if available):** Pre-recorded `.mp3` audio files bundled in `assets/audio/` or fetched from a URL. If an audio file exists for a destination, use it instead of TTS.
   - **Audio player:** Use `MediaPlayer` or `ExoPlayer` for audio files; use `TextToSpeech` for TTS.
   - **Background play:** Audio continues playing even when the user navigates to another screen (via a foreground service or a ViewModel-scoped player).
   - **Mini player:** Visible across all screens while audio is playing, pinned above the bottom nav bar.

#### Data source
`AudioGuideRepository` — list of destinations with `audioGuideText` (English + Hindi + Santhali scripts), `duration`, `thumbnailUrl`.

#### Required photos (ALL real)
- Real thumbnail photo for each destination (same images used in Home/Destination cards): Dassam Falls, Hundru Falls, Jonha Falls, Panch Gagh Falls, Rock Garden, Sun Temple, Tagore Hill, Patratu Valley, Netarhat, Betla National Park, Jagannath Temple, Deoghar Baidyanath, Parasnath Hill.

---

### Item 7: Share on Social Media

**Label:** "Share on Social Media"
**Icon:** Share — `Icons.Filled.Share`
**Navigates to:** `ShareScreen` (or triggers a system share sheet directly)

#### What this screen does
This screen lets the user share their trip itinerary, a specific destination, or the app itself via WhatsApp, Instagram, Facebook, Email, or any other sharing app installed on their phone. It also lets them copy a link.

#### What it shows (components, top to bottom)

1. **Header** — "Share" title. Subtitle: "Spread the word about Jharkhand."

2. **"What would you like to share?" section** — 3 large option cards (tappable):
   - **"Share My Trip Itinerary"** — card with a trip icon. Tapping opens a trip selector; user picks a trip, and a formatted summary is generated for sharing.
   - **"Share a Destination"** — card with a map pin icon. Tapping opens a destination selector; user picks a destination, and a rich snippet is generated.
   - **"Share TRU App"** — card with an app icon. Tapping generates a "Check out TRU — Jharkhand Tourism" message with a Play Store link.

3. **Share target grid** (appears after selecting what to share) — grid of app icons:
   - WhatsApp (green icon)
   - Instagram (gradient icon)
   - Facebook (blue icon)
   - Email (envelope icon)
   - Telegram (blue icon)
   - Copy Link (chain icon)
   - More (three dots — opens system share sheet)

4. **Share content format:**

   **For an itinerary:**
   ```
   🌍 My TRU Trip: Ranchi Waterfall Circuit

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

   Planned with TRU AI — Jharkhand Tourism 🌿
   ```

   **For a destination:**
   ```
   📍 Dassam Falls — Ranchi, Jharkhand

   A spectacular waterfall where the Kanchi River falls from 44m.
   Best time: October–March. Free entry.

   🗺️ https://maps.google.com/?q=Dassam+Falls+Ranchi

   Discovered via TRU AI — Jharkhand Tourism 🌿
   ```

   **For the app:**
   ```
   🌿 Check out TRU — the AI-powered Jharkhand Tourism app!
   Discover waterfalls, temples, wildlife, and more.
   Plan trips with AI, book hotels, navigate with maps.

   Download: [Play Store link]

   TRU — Jharkhand Tourism 🌿
   ```

5. **Email-specific format** (when user selects Email):
   - **To:** empty (user fills in)
   - **Subject:** "My TRU Trip: [Trip Name] — planned with TRU AI"
   - **Body:** HTML formatted email with the itinerary, destination images embedded, and a map link.
   - **Intent:** `Intent.ACTION_SEND` with MIME `message/rfc822`.

6. **Implementation:**
   ```kotlin
   fun shareContent(context: Context, text: String, subject: String? = null, imageUri: Uri? = null) {
       val intent = Intent(Intent.ACTION_SEND).apply {
           type = if (imageUri != null) "image/*" else "text/plain"
           putExtra(Intent.EXTRA_TEXT, text)
           if (subject != null) putExtra(Intent.EXTRA_SUBJECT, subject)
           if (imageUri != null) putExtra(Intent.EXTRA_STREAM, imageUri)
       }
       context.startActivity(Intent.createChooser(intent, "Share via"))
   }
   ```

#### Data source
No external data needed — uses the user's saved trips and destinations from `TripRepository` and `DestinationRepository`.

#### Required photos
- No specific photos required for this screen (uses thumbnails from trips/destinations the user selects).
- App icon / logo for "Share TRU App" option.

---

## 4. What the Sidebar Does NOT Have

To be explicit, the sidebar must NOT contain:
- A "Home" button (Home is in the bottom nav)
- A "Plan" or "AI Planning" button (Plan is in the bottom nav)
- A "Trips" or "My Trips" button (Trips is in the bottom nav)
- A "Wallet" or "Payments" button (Wallet is in the bottom nav)
- A "Settings" or "Profile" button (accessed via profile avatar in the top bar)
- A "Login/Logout" button (accessed from the profile screen)
- A "Notifications" button (accessed from the top bar)
- Any item not listed in Section 2

The sidebar is ONLY for Jharkhand-specific content sections. Core travel workflow (discover → plan → trip management → payment) lives in the bottom nav. This separation is intentional and must be preserved.

---

## 5. Image Requirements — Master List

> **ALL photos in the sidebar screens must be REAL, ORIGINAL photos of Ranchi / Jharkhand. No stock placeholders. No AI-generated images. No non-Jharkhand photos.**

### 5.1 Where to source images
- **Wikimedia Commons** — free, real photos (search: "Dassam Falls", "Hundru Falls", "Netarhat", "Betla National Park", "Sohrai painting", "Dokra craft", etc.)
- **Jharkhand Tourism official website** — tourism.jharkhand.gov.in (official photos)
- **Unsplash** — search: "Ranchi", "Jharkhand", "waterfall Jharkhand"
- **Government of Jharkhand media** — press photos, district administration photos

### 5.2 How to load images
- Use **Coil** image loading library (`coil-compose`).
- Each data model has an `imageUrl: String` field pointing to a real, working URL.
- Coil handles caching, placeholder, and error states.
- If a URL is broken, fall back to a bundled asset in `res/drawable/`.

### 5.3 Complete photo checklist

#### Jharkhand at a Glance (Item 1)
- [ ] Dassam Falls (hero/banner)
- [ ] Hundru Falls
- [ ] Patratu Valley
- [ ] Netarhat sunset
- [ ] Betla National Park
- [ ] Sun Temple Ranchi
- [ ] Rock Garden Ranchi
- [ ] Jonha Falls
- [ ] Sohrai wall painting
- [ ] Tribal festival photo
- [ ] Chhau dance photo

#### Events (Item 2)
- [ ] Tusu Parab festival
- [ ] Sarhul celebration
- [ ] Karma festival dance
- [ ] World Tribal Day celebration
- [ ] Sohrai festival / painting
- [ ] Bandna festival
- [ ] Jharkhand Foundation Day

#### Hospitality Services (Item 3)
- [ ] Radisson Hotel Ranchi (exterior or room)
- [ ] Capitol Residency Hotel
- [ ] Hotel AVN Grand
- [ ] Hotel Capitol Hill
- [ ] Hotel Green Acres
- [ ] Hotel Cloud 9
- [ ] Hotel Birsa Vihar (Netarhat)
- [ ] Hotel Vindravani (Betla)
- [ ] Eco-Lodge Betla
- [ ] Netarhat Tourist Lodge
- [ ] Hazaribagh Tourist Lodge
- [ ] Treebo Trend Radha Krishna
- [ ] FabHotel Prime Galaxy
- [ ] Hotel Akashdeep
- [ ] TribAL Homestay Khunti
- [ ] Patratu Riverside Camp

#### Jharkhand Souvenirs (Item 4)
- [ ] Dokra horse figurine
- [ ] Dokra elephant statue
- [ ] Sohrai painting (large canvas)
- [ ] Sohrai painting (small)
- [ ] Paitkar scroll painting
- [ ] Bamboo basket
- [ ] Bamboo wall hanging
- [ ] Terracotta pot
- [ ] Terracotta diya set
- [ ] Tussar silk saree
- [ ] Tussar silk dupatta
- [ ] Lac bangle set
- [ ] Tribal beaded necklace
- [ ] Tribal silver earrings
- [ ] Wooden toy set
- [ ] Tribal wooden mask
- [ ] Artisan portrait (for spotlight)

#### HeliTourism (Item 5)
- [ ] Helicopter in flight (hero)
- [ ] Aerial photo of Patratu Valley
- [ ] Aerial photo of waterfall (Dassam/Hundru from above)
- [ ] Aerial photo of Netarhat
- [ ] Aerial photo of Betla forest
- [ ] Ranchi helipad photo

#### Audio Guide (Item 6)
- [ ] Dassam Falls thumbnail
- [ ] Hundru Falls thumbnail
- [ ] Jonha Falls thumbnail
- [ ] Panch Gagh Falls thumbnail
- [ ] Rock Garden thumbnail
- [ ] Sun Temple thumbnail
- [ ] Tagore Hill thumbnail
- [ ] Patratu Valley thumbnail
- [ ] Netarhat thumbnail
- [ ] Betla National Park thumbnail
- [ ] Jagannath Temple thumbnail
- [ ] Deoghar Baidyanath Temple thumbnail
- [ ] Parasnath Hill thumbnail

#### Share on Social Media (Item 7)
- [ ] App icon / TRU logo

**Total unique real photos required: ~70+**

> **Instruction to Google AI Studio:** For each image, search Wikimedia Commons for the real photo, verify the URL is accessible, and use it as the `imageUrl` in the data model. If the URL is not accessible, download the image and bundle it as a drawable asset. Do NOT use any image that is not a real photo of the specified Jharkhand place/product. Do NOT use Bali, Berlin, Cairo, or any non-Jharkhand image.

---

## 6. Sidebar Technical Implementation

### 6.1 Drawer setup in Compose
```kotlin
@Composable
fun TRUApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            TRUSidebar(
                onItemSelected = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route)
                }
            )
        }
    ) {
        // Main content: Scaffold with top bar (hamburger) + bottom nav
        TRUMainScaffold(
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
}
```

### 6.2 Sidebar composable
```kotlin
@Composable
fun TRUSidebar(onItemSelected: (String) -> Unit) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF1A2332) // dark navy
    ) {
        // Back arrow
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Close",
            tint = Color.White,
            modifier = Modifier.padding(16dp)
        )

        // Menu items
        sidebarItems.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, tint = Color.White) },
                label = { Text(item.label, color = Color.White, fontSize = 16.sp) },
                selected = false,
                onClick = { onItemSelected(item.route) },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                    selectedContainerColor = Color(0xFF3A4555)
                ),
                modifier = Modifier.padding(horizontal = 12dp, vertical = 4dp)
            )
        }

        // Footer
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Powered by TRU AI",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier.padding(16dp)
        )
    }
}
```

### 6.3 Sidebar items data
```kotlin
data class SidebarItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val sidebarItems = listOf(
    SidebarItem("Jharkhand at a Glance", Icons.Filled.AccountBalance, "jharkhand_glance"),
    SidebarItem("Events", Icons.Filled.CalendarMonth, "events"),
    SidebarItem("Hospitality Services", Icons.Filled.Hotel, "hospitality"),
    SidebarItem("Jharkhand Souvenirs", Icons.Filled.ShoppingBag, "souvenirs"),
    SidebarItem("HeliTourism", Icons.Filled.Flight, "heli_tourism"),
    SidebarItem("Audio Guide", Icons.Filled.Headphones, "audio_guide"),
    SidebarItem("Share on Social Media", Icons.Filled.Share, "share")
)
```

---

## 7. Summary

| Aspect | Value |
|--------|-------|
| Total sidebar items | 7 (exactly) |
| Background | Dark navy (`#1A2332`) |
| Text/icon colour | White |
| Item order | Jharkhand at a Glance → Events → Hospitality Services → Jharkhand Souvenirs → HeliTourism → Audio Guide → Share on Social Media |
| Drawer width | 280dp |
| Slide animation | 300ms from left |
| Opens via | Hamburger icon or left-edge swipe |
| Closes via | Back arrow, outside tap, or left swipe |
| Coexists with | Bottom nav (Home / Plan / Trips / Wallet) |
| Photos | ALL real, original photos of Ranchi/Jharkhand |
| Data | ALL real Jharkhand places, hotels, festivals, handicrafts |

---

*End of sidebar specification. Hand this to Google AI Studio alongside the main problem statement. Instruct: "Implement the sidebar EXACTLY as specified — 7 items, exact order, exact labels, dark navy background, white icons/text. Each item navigates to its specified screen with real Jharkhand data and real photos. No item may be added, removed, or reordered."*
