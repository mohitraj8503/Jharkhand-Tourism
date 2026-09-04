# TRU — Problem Statement & Build Brief for Google AI Studio (v2)

**Document type:** Problem statement / build brief for generating a native Android (Kotlin) app inside Google AI Studio
**App name:** TRU — AI-Powered Jharkhand Tourism Companion
**Target platform:** Native Android (Kotlin + Jetpack Compose), Material 3 + Apple-style aesthetics
**Generation target:** Google AI Studio (Gemini) should use this document to generate the COMPLETE, FULLY-WORKING app — every screen, every interaction, every integration
**Date:** September 2026
**Version:** 2 — updated with sidebar spec, real Ranchi data, real-time maps, email, Apple/Google UI

---

## 0. CRITICAL INSTRUCTIONS FOR GOOGLE AI STUDIO

> **Read this before generating any code.**

1. **The sidebar must be EXACTLY as specified in Section 7.0.** Do not rename, reorder, add, or remove any sidebar items. The sidebar has 7 items: Jharkhand at a Glance, Events, Hospitality Services, Jharkhand Souvenirs, HelTourism, Audio Guide, Share on Social Media. Each item is defined with its icon, behaviour, destination screen, and data source.

2. **All data must be REAL and from RANCHI / JHARKHAND.** Every destination name, hotel name, flight route, image, price, and location must correspond to a real place in Jharkhand, India. Use the comprehensive place list in Section 9. Do NOT use Bali, Berlin, Cairo, or any non-Jharkhand placeholder. The app is for Jharkhand Tourism.

3. **All photos must be from Ranchi and Jharkhand.** Extract and use real images of: Dasam Falls, Hundru Falls, Jonha Falls, Panch Gagh Falls, Rock Garden Ranchi, Sun Temple Ranchi, Patratu Valley, Netarhat, Betla National Park, Jagannath Temple, Tagore Hill, Ranchi Lake, Dassam Falls, Gonda Hill, Muta Crocodile Centre, Birsa Zoological Park, etc. Use Coil image loading with real image URLs or bundled assets.

4. **Maps integration must be FULLY WORKING.** Use Google Maps Compose SDK. Every destination must have a real latitude/longitude marker. The map screen must render live tiles, show custom markers, support tap-to-info-card, and enable turn-by-turn navigation intent to Google Maps.

5. **Email functionality must work.** The app must send a real email (via Android Intent.ACTION_SEND with email MIME type) containing the user's itinerary/trip summary. See Section 11.

6. **The ENTIRE prototype must be working.** Every button must navigate. Every form must validate and submit. Every list must be populated from a repository. The AI chat must call Gemini. The wallet must display a card. The trip timeline must render. Nothing is a placeholder. Nothing says "coming soon."

7. **UI must follow Apple/Google design standards.** See Section 8 for the full design system. Key principles: generous whitespace, large rounded corners (24dp+), soft shadows, SF-Pro/Roboto typography, smooth transitions, haptic-like micro-animations, bottom-sheet modals, and a premium, clean aesthetic. Think Apple Travel + Google Material 3.

---

## 1. Executive Summary

TRU is an AI-powered travel companion for Jharkhand Tourism that unifies **discovery, planning, booking, and trip management** into one seamless native Android experience. The app is anchored in Ranchi — the capital of Jharkhand — and covers all major tourism destinations across the state. A traveller opens the app, sees their location as "Ranchi, Jharkhand," and can immediately discover nearby waterfalls, temples, wildlife sanctuaries, and hill stations; plan a trip with the AI assistant; book hotels and flights in real time; navigate using integrated maps; share their itinerary via email; and manage their entire journey through a live timeline.

The app retains its original sidebar navigation (from the Visily prototype) with seven dedicated sections, and adds a modern bottom navigation bar (Home / Plan / Trips / Wallet) following Apple/Google design conventions.

---

## 2. The Problem

### 2.1 Fragmented tourism experience in Jharkhand
Jharkhand has extraordinary tourism assets — Dasam Falls, Hundru Falls, Jonha Falls, Rock Garden, Sun Temple, Patratu Valley, Netarhat, Betla National Park — but there is no single app that helps a traveller discover, plan, book, and navigate all of them. Tourists today use Google for search, MakeMyMyTrip for booking, Google Maps for navigation, and WhatsApp for sharing itineraries. Context is lost at every handoff.

### 2.2 No AI-assisted trip planning for Jharkhand
No existing app lets a traveller say "Plan a 3-day eco-friendly trip to Ranchi covering waterfalls and temples under ₹15,000" and get a complete, bookable itinerary. The AI layer is missing entirely from Jharkhand tourism.

### 2.3 No real-time data or maps integration
Existing Jharkhand tourism websites are static. There is no live map showing all tourism spots with markers, no real-time hotel availability, no live flight information, and no turn-by-turn navigation from within the app.

### 2.4 No responsible-tourism layer
Popular spots like Hundru Falls get overcrowded on weekends while equally beautiful Panch Gagh Falls remains empty. No app distributes footfall or shows eco-certified stays. The research paper on regenerative tourism in Jharkhand (BIT Mesra, 2025) proposes AI-driven visitor management, eco-certified inventory, fair-trade pricing, carbon footprint tracking, and verified ratings — none of which exist in any consumer app today.

### 2.5 Trips are not managed, only imagined
Once a traveller plans a trip, there is no live timeline, no trip-readiness tracker, no email sharing, and no on-trip management surface.

---

## 3. Who TRU Is For

**Primary persona — "The Jharkhand Explorer" (age 18–45)**
- Lives in or is visiting Ranchi / Jharkhand
- Wants to discover local waterfalls, temples, wildlife sanctuaries, hill stations
- Comfortable with AI assistants; prefers describing what they want
- Cares about budget, convenience, and increasingly about eco-impact

**Secondary persona — "The Out-of-State Tourist"**
- Visiting Jharkhand from another state (Delhi, Kolkata, Bengaluru)
- Needs flights to Ranchi (IXR), hotel bookings, and a curated itinerary
- Wants maps, email sharing, and real-time information

**Tertiary persona — "The Eco-Conscious Traveller"**
- Actively seeks sustainable stays, local experiences, low-carbon transport
- Wants transparency on footfall, carbon footprint, and fair-trade pricing

---

## 4. Core Capabilities

1. **AI-First Trip Planning** — conversational AI that builds a complete Jharkhand itinerary from a free-text prompt.
2. **Smart Decision Support** — structured booking config with real Jharkhand destinations, dates, budget tiers in ₹.
3. **Map-Integrated Discovery** — live Google Maps with markers for every tourism spot in Jharkhand.
4. **Curated, Not Generic** — recommendations driven by the traveller's budget, style, and history, with real Jharkhand data.
5. **Seamless Payments** — in-app virtual card / wallet for booking and on-trip payments.
6. **Trip Memory & Dashboard** — "My Trips" with tabs (All / Upcoming / Ended / Past) and stats.
7. **Built for the Full Journey** — live trip timeline with timestamped events, images, and discovery tabs.
8. **Sidebar Navigation (preserved from prototype)** — 7 dedicated sections for Jharkhand-specific content.
9. **Email Sharing** — send itinerary / trip summary via email.
10. **Responsible-Tourism Overlay** — footfall-aware suggestions, eco-certified stays, carbon visibility, fair-trade pricing, verified ratings.

---

## 5. Functional Requirements

| ID | Requirement |
|----|-------------|
| FR-1 | User can start a trip via AI prompt ("Tell me about your dream trip") and receive a full Jharkhand itinerary. |
| FR-2 | User can configure booking: destination (from Jharkhand places), dates, duration, budget tier (Budget ₹ / Standard ₹ / Luxury ₹), passengers, trip type. |
| FR-3 | User can discover destinations on an interactive Google Map with real latitude/longitude markers for every Jharkhand tourism spot. |
| FR-4 | User can browse curated destination cards (Trending / Top Picks / Nearby), each with a real Jharkhand place name, image, and detail action. |
| FR-5 | User can view and manage a virtual payment card (number, holder, expiry, CVV, brand) and balance. |
| FR-6 | User can view "My Trips" dashboard with tabs and lifetime stats (places visited, saved places, upcoming). |
| FR-7 | User can open a live trip timeline with timestamped events, images, and discovery tabs. |
| FR-8 | User can see a trip-readiness widget (e.g., "Your Ranchi Waterfall Circuit is 68% Ready", "5 Days Left"). |
| FR-9 | User can see eco/footfall indicators on destinations and choose lower-impact alternatives. |
| FR-10 | User can see transparent price breakdowns showing the local-provider share. |
| FR-11 | User can view carbon footprint per trip leg and opt for greener options. |
| FR-12 | User can search flights to/from Ranchi (Birsa Munda Airport, IXR). |
| FR-13 | App detects user location (default: "Ranchi, Jharkhand") and uses it as a default. |
| FR-14 | **SIDEBAR:** User can open the sidebar (hamburger menu or swipe from left) and navigate to all 7 sections defined in Section 7.0. |
| FR-15 | **EMAIL:** User can tap "Share Itinerary" and the app opens an email compose intent (ACTION_SEND) pre-filled with trip summary, destination details, and a formatted HTML/plain-text body. |
| FR-16 | **MAPS:** User can tap any destination marker on the map and get a floating info card with name, image thumbnail, rating, and a "Navigate" button that launches Google Maps turn-by-turn navigation. |
| FR-17 | **HOTELS:** User can browse real hotels in Ranchi/Jharkhand with name, price per night, rating, amenities, and a "Book Now" action. |
| FR-18 | **FLIGHTS:** User can search flights to/from Ranchi (IXR) with airline, departure/arrival times, duration, and price. |
| FR-19 | **AUDIO GUIDE:** User can play an audio guide for each destination (text-to-speech or pre-recorded audio). |
| FR-20 | **HELI TOURISM:** User can view helicopter tourism packages and book a heli-tour. |
| FR-21 | **SOUVENIRS:** User can browse and purchase Jharkhand handicrafts / souvenirs. |

---

## 6. Non-Functional Requirements

- **Platform:** Native Android, Kotlin 2.x, Jetpack Compose, Material 3.
- **Min SDK:** Android 8.0 (API 26). Target SDK: latest stable.
- **Architecture:** MVVM + Clean Architecture (presentation / domain / data). Single-activity, Compose Navigation.
- **Offline-first:** Core data cached in Room; AI, maps, and booking calls go remote.
- **AI integration:** Gemini via Google AI Studio for itinerary generation, conversational planning, and recommendation reasoning.
- **Maps:** Google Maps Compose SDK with live tiles, custom markers, and navigation intents.
- **Email:** Android Intent.ACTION_SEND with `message/rfc822` MIME type.
- **Performance:** Cold start under 2s; 60fps scrolling; no blocking I/O on main thread.
- **Design standard:** Apple HIG + Google Material 3. Premium aesthetic. See Section 8.
- **Security:** No raw card data in plaintext; payments tokenised; no secrets in code; Gemini key via BuildConfig.

---

## 7. UI / Screen Specification

### Design language
- **Style:** Apple-grade premium aesthetic merged with Material 3 components. Think Apple Travel app meets Google Maps.
- **Layout:** bento-box cards, high border radius (24dp+), soft shadows, generous whitespace.
- **Motion:** smooth fade/scale transitions on card tap, bottom-sheet modals, shared-element transitions where possible.
- **Two navigation systems coexist:**
  - **Sidebar** (left drawer, opened via hamburger icon or edge-swipe) — 7 Jharkhand-specific sections. THIS IS THE SAME AS THE PROTOTYPE. Do not change it.
  - **Bottom nav bar** — Home / Plan / Trips / Wallet — for primary app navigation.

---

### 7.0 SIDEBAR — COMPLETE SPECIFICATION (PRESERVE EXACTLY)

> **This sidebar is from the original Visily prototype. It MUST be preserved inch-to-inch. Do not rename, reorder, add, or remove items.**

**Visual design:**
- Full-height drawer, slides in from the left edge.
- Background: dark navy / charcoal (`#1A2332` or `#0F172A`).
- Each menu item: white icon (left) + white text label (right-aligned left).
- Item height: 56dp. Icon size: 24dp. Text size: 16sp, medium weight.
- Active item: subtle lighter background tint or lime accent bar on the left edge.
- Top of sidebar: optional TRU logo or "Jharkhand Tourism" header.
- Bottom of sidebar: version number / "Powered by TRU AI" text.

**The 7 sidebar items (in this exact order):**

#### Item 1: Jharkhand at a Glance
- **Icon:** Tower / Monument icon (Material: `Icons.Filled.AccountBalance` or `Icons.Filled.Tour`)
- **Label:** "Jharkhand at a Glance"
- **Navigates to:** `JharkhandGlanceScreen`
- **What it shows:** An overview dashboard of Jharkhand — state statistics (total districts, tourism spots, forests, waterfalls, wildlife sanctuaries), a hero image of a Jharkhand landscape (Dasam Falls or Patratu Valley), quick facts (capital: Ranchi, language: Hindi & tribal languages, best time to visit, currency: ₹), and a horizontal scroll of "Must Visit" destination cards with real images.
- **Data source:** Static curated content in `JharkhandData.kt` — real facts about Jharkhand (area: 79,716 km², 24 districts, 3 wildlife sanctuaries, 10+ major waterfalls, etc.)

#### Item 2: Events
- **Icon:** Calendar icon (`Icons.Filled.CalendarMonth` or `Icons.Filled.Event`)
- **Label:** "Events"
- **Navigates to:** `EventsScreen`
- **What it shows:** A chronological list of upcoming festivals and events in Jharkhand — Karma Festival, Sarhul, Tusu Parab, Bandna, Karam Puja, Sohrai, World Tribal Day, Jharkhand Foundation Day (Nov 15). Each event card: event name, date, location, short description, image, and "Add to Trip" button.
- **Data source:** `EventRepository` — hardcoded list of real Jharkhand festivals with dates.

#### Item 3: Hospitality Services
- **Icon:** Bed / Hotel icon (`Icons.Filled.Hotel` or `Icons.Filled.Bed`)
- **Label:** "Hospitality Services"
- **Navigates to:** `HospitalityScreen`
- **What it shows:** A searchable list of real hotels, lodges, and homestays in Ranchi and across Jharkhand. Each listing: hotel name, image, star rating, price per night (₹), amenities (WiFi, AC, parking, restaurant, EV charging), location with map link, "Book Now" button, and eco-certified badge where applicable. Filter by: city (Ranchi, Netarhat, Betla, Hazaribagh), price range, rating, eco-certified.
- **Data source:** `HotelRepository` — real hotels like:
  - Radisson Hotel Ranchi
  - Capitol Hill Resort, Ranchi
  - Hotel AVN Grand, Ranchi
  - Hotel Birsa Vihar, Netarhat
  - Hotel Vindravani, Betla
  - Treebo Trend hotels in Ranchi
  - Eco-lodges near Betla National Park
  - Homestays in Khunti / Chaibasa

#### Item 4: Jharkhand Souvenirs
- **Icon:** Shopping Bag icon (`Icons.Filled.ShoppingBag`)
- **Label:** "Jharkhand Souvenirs"
- **Navigates to:** `SouvenirsScreen`
- **What it shows:** A marketplace grid of authentic Jharkhand handicrafts and products. Each product card: product image, name, price (₹), artisan/community name, short description, "Add to Cart" / "Buy Now" button. Categories: Dokra metal craft, Sohrai paintings, Paitkar paintings, bamboo craft, terracotta, wooden toys, tribal jewellery, tussar silk, lac bangles.
- **Data source:** `SouvenirRepository` — real Jharkhand handicraft items with artisan details.
- **Payment:** Integrates with the Wallet screen for checkout.

#### Item 5: HelTourism (Helicopter Tourism)
- **Icon:** Helicopter icon (`Icons.Filled.Flight` or a custom helicopter vector)
- **Label:** "HelTourism"
- **Navigates to:** `HeliTourismScreen`
- **What it shows:** Helicopter tourism packages for Jharkhand — aerial sightseeing tours over Patratu Valley, Netarhat, Hundru Falls, Betla National Park. Each package: name, duration (e.g., 30 min), route, price (₹), image, description, availability calendar, "Book Helicopter Ride" button. Safety information section. (Note: Jharkhand Tourism has explored heli-tourism; model this as a real feature.)
- **Data source:** `HeliTourRepository` — curated helicopter tour packages with real Jharkhand routes.

#### Item 6: Audio Guide
- **Icon:** Headphones icon (`Icons.Filled.Headphones` or `Icons.Filled.GraphicEq`)
- **Label:** "Audio Guide"
- **Navigates to:** `AudioGuideScreen`
- **What it shows:** A list of all tourism destinations, each with a "Play Audio Guide" button. When tapped, an audio player bar appears at the bottom (like a mini music player) with play/pause, seek, and a progress bar. The audio is a narrated guide for that destination — its history, significance, what to see, best time to visit, tips. Each guide: destination name, duration, language selector (Hindi / English / Santhali), play button.
- **Implementation:** Use Android `MediaPlayer` or `ExoPlayer`. Audio content can be text-to-speech (TTS) generated or pre-recorded `.mp3` files bundled in assets or fetched from a URL. Each destination has an `audioGuideText` field that TTS reads aloud if no audio file is available.

#### Item 7: Share on Social Media
- **Icon:** Share / Network icon (`Icons.Filled.Share` or `Icons.Filled.Share`)
- **Label:** "Share on Social Media"
- **Navigates to:** `ShareScreen` (or triggers a share intent directly)
- **What it shows:** A share sheet with options: Share to WhatsApp, Share to Instagram, Share to Facebook, Share via Email, Copy Link. Content to share: the user's current trip itinerary or a specific destination card with image, name, and a "Planned with TRU AI" tagline. Uses Android `Intent.ACTION_SEND` with appropriate MIME types.
- **If sharing a specific destination:** generates a rich text snippet with destination name, description, image, and a deep link to the app.
- **If sharing an itinerary:** generates a formatted HTML email body with day-by-day breakdown, hotels, flights, total cost, and a map link.

---

### 7.1 Home / Discovery screen
- **Top bar:** hamburger icon (left, opens sidebar), "Your Location" label + "Ranchi, Jharkhand" (centre), circular search icon (right), circular profile avatar.
- **Hero card:** large image of a Jharkhand destination (e.g., Dasam Falls) with overlaid title.
- **"What's New?" section:** chevron header, featured card (e.g., a new eco-lodge or festival).
- **AI Assistant card:** "Build a trip to Ranchi with AI" — sparkle icon, tappable to open AI Planning.
- **Search bar:** "Enter your question" with sparkle + mic icon.
- **"Popular Places" section:** two-up cards — Rock Garden, Sun Temple, Hundru Falls, Jonha Falls, Patratu Valley. Each with real image, name, subtitle (Ranchi, Jharkhand), eco badge where applicable.
- **"Videos" section:** video cards for destinations (Patratu Valley, Netarhat sunset, etc.) — tappable to play.
- **Trip Planner widget:** "Your Ranchi Waterfall Circuit is 68% Ready", "5 Days Left" badge.
- **Bottom nav:** Home (active) / Plan / Trips / Wallet.

### 7.2 AI Trip Planning screen
- **Header:** back arrow, "AI Assistant" title, "Ready to help anytime" subtitle, profile icon.
- **Chat thread:**
  - AI banner: "Plan Your Perfect Trip In Seconds With TRU AI" + "Instant personalized itineraries powered by verified trust data."
  - AI prompt bubble: "Tell me about your dream trip."
  - User query example: "I want an eco-friendly 3-day trip to Ranchi covering waterfalls and temples under ₹15,000."
  - AI response: structured itinerary with day-by-day breakdown, carbon footprint (e.g., "2.1 kg CO₂/day"), fair-trade benefit %, EV shuttle options, eco-lodge suggestions. Renders as rich cards, not plain text.
- **Input bar:** "Ask TRU AI to plan or adjust your trip..." with send icon.
- **Bottom nav:** Plan (active).

### 7.3 Smart Decision Support / Booking Config screen
- Vertical form:
  - **Destination** — chip selector from real Jharkhand places (Ranchi, Netarhat, Betla, Hazaribagh, Deoghar, etc.)
  - **Dates & Duration** — date picker + "X nights"
  - **Budget** — segmented: Budget (₹3,000–5,000/day) | Standard (₹5,000–10,000/day) | Luxury (₹10,000+/day)
  - **Trip type** — One Way | Round Trip
  - **Passengers** — stepper
  - **Class** — dropdown
- Primary CTA: "Search" (lime accent).

### 7.4 Map-Integrated Discovery screen
- Full-bleed Google Maps Compose with live tiles.
- Custom markers for every Jharkhand tourism spot (see Section 9 for coordinates).
- Top search bar ("Search Destinations in Jharkhand").
- Floating info card on marker tap: destination name, thumbnail, rating, "Navigate" button (launches Google Maps turn-by-turn via `Intent.ACTION_VIEW, geo:lat,lng?q=...`).
- Bottom sheet with curated nearby cards.
- Clustering for markers when zoomed out.

### 7.5 Flight Search screen
- Form: From (major Indian cities: Delhi DEL, Kolkata CCU, Mumbai BOM, Bengaluru BLR) / To: Ranchi (IXR — Birsa Munda Airport) / Date / Passengers / Class / One Way | Round Trip.
- Results: airline (Air India, IndiGo, Vistara, SpiceJet), departure/arrival times, duration, price (₹), "Book" button.
- Use realistic flight data (routes and approximate prices; can be stubbed with real airline names and realistic timings).

### 7.6 Payments / Wallet screen
- Virtual card visual (lime/dark-green gradient, card brand logo).
- Fields: cardholder name, masked card number (`•••• 3874`), expiry, CVV (•••).
- Floating balance (₹).
- Transaction list: hotel booking, flight booking, souvenir purchase, heli-tour.
- "Add Money" and "Pay" buttons.

### 7.7 My Trips dashboard
- Header: "My Trips".
- Tabs: All | Upcoming | Ended | Past.
- Stats grid: "12 Places Visited", "8 Saved Places", "3 Upcoming".
- Trip cards: name (e.g., "Ranchi Waterfall Circuit", "Netarhat Hill Station Retreat"), dates, cover image, status badge, trip-readiness %.
- Each card tappable → opens Trip Timeline (7.8).
- **Share button** on each trip card → opens email/share intent with formatted itinerary.

### 7.8 Trip Timeline / Itinerary screen
- Header: "My Trip: Ranchi, Jharkhand".
- Chronological timeline: timestamped events (08:00 / 10:30 / 13:00 / 16:00) with images, destination names, descriptions.
- Discovery tabs: Trending | Top Picks | Nearby.
- Each event tappable → opens destination detail or offers "Swap with eco-alternative".
- **Email itinerary button** (top right) → sends formatted email with full itinerary.
- Editable: tap event to reschedule, swap, or replace.

### 7.9 Destination Detail screen
- Hero image (full-width, rounded bottom).
- Destination name, subtitle (e.g., "Ranchi, Jharkhand").
- Rating, eco-badge, footfall indicator.
- Description (history, significance, what to see).
- "Best Time to Visit" info.
- Entry fee (₹), timings.
- "Get Directions" button → Google Maps navigation intent.
- "Add to Trip" button.
- "Play Audio Guide" button → opens mini audio player.
- Gallery section: multiple real images.
- Nearby places carousel.

### 7.10 Trip-Readiness widget (shared component)
- Props: trip name, percent ready, days left.
- Visual: progress bar + badge.

---

## 8. Design System — Apple + Google Hybrid

### 8.1 Design principles
1. **Clarity** — content is king; UI recedes. Large images, legible type, clear hierarchy.
2. **Deference** — the UI is fluid, clean, and doesn't compete with content. No heavy borders, no visual noise.
3. **Depth** — layered views, bottom sheets, subtle shadows create a sense of hierarchy and navigation.
4. **Consistency** — every screen follows the same spacing, typography, and colour system.
5. **Feedback** — every tap has a response (ripple, scale, haptic, sheet).

### 8.2 Colour palette
| Token | Value | Use |
|-------|-------|-----|
| `ForestGreen` | `#0B3D2E` | Primary brand, dark surfaces |
| `LimeAccent` | `#C6F432` | Primary buttons, highlights, active states |
| `NavySidebar` | `#1A2332` | Sidebar background |
| `Surface` | `#F8F9FA` | App background (off-white, warm) |
| `SurfaceCard` | `#FFFFFF` | Card background |
| `SurfaceElevated` | `#FFFFFF` with shadow | Modal/sheet |
| `TextPrimary` | `#1A1A1A` | Primary text |
| `TextSecondary` | `#6B7280` | Secondary text |
| `TextOnDark` | `#FFFFFF` | Text on dark surfaces (sidebar, green cards) |
| `EcoBadge` | `#2E7D32` | Eco indicators |
| `EcoBadgeBg` | `#E8F5E9` | Eco badge background |
| `FootfallLow` | `#4CAF50` | Green — low crowd |
| `FootfallMedium` | `#FF9800` | Orange — moderate |
| `FootfallHigh` | `#F44336` | Red — overcrowded |
| `Error` | `#C62828` | Errors |
| `Success` | `#2E7D32` | Success states |

### 8.3 Typography
- **Font family:** Inter (or SF Pro Display fallback / Roboto Flex) — clean, geometric, Apple-like.
- **Display / H1:** 28sp, Bold — screen titles.
- **H2:** 22sp, Semibold — section headers.
- **H3:** 18sp, Semibold — card titles.
- **Body:** 16sp, Regular — descriptions.
- **Caption:** 13sp, Medium — metadata, badges.
- **Caption Small:** 11sp, Medium — timestamps, chips.
- **Line height:** 1.4× font size for body; 1.2× for headings.

### 8.4 Spacing & shape
- **Base unit:** 4dp grid.
- **Common paddings:** 8 / 12 / 16 / 20 / 24dp.
- **Card padding:** 16dp.
- **Card corner radius:** 24dp (large, Apple-style).
- **Button radius:** 16dp (full pill for chips).
- **Sheet radius:** 28dp (top corners only).
- **Elevation:** 0–1dp for flat cards; 4dp for elevated cards; 8dp for modals. Use soft, diffuse shadows (not hard lines).

### 8.5 Component spec
- **Buttons:** full-width primary (lime or forest green, white text, 52dp height, 16dp radius); secondary (outlined, transparent bg, forest green text).
- **Cards:** `SurfaceCard`, 24dp radius, 1dp diffuse shadow, 16dp inner padding.
- **Chips:** pill-shaped, 12dp vertical padding, 16dp horizontal, 13sp text.
- **Text fields:** outlined, 14sp label, 16sp input, 12dp radius, 16dp padding.
- **Bottom nav:** 64dp height, 4 items, active item = lime icon + lime label; inactive = grey.
- **Sidebar drawer:** 280dp width, full height, navy bg, slide animation 300ms.
- **Map markers:** custom icon (green pin with destination image thumbnail), 40×40dp.

### 8.6 Animation & motion
- Page transitions: slide + fade, 300ms, `FastOutSlowInEasing`.
- Card tap: scale to 0.97, 100ms, spring back.
- Bottom sheet: slide up from bottom, 350ms, `EaseInOut`.
- Sidebar drawer: slide from left, 300ms.
- Shimmer loading: skeleton shimmer on data-loading states.

---

## 9. Real Jharkhand / Ranchi Data — Complete Place List

> **Every destination, hotel, and flight in the app MUST use this real data. Do not use any non-Jharkhand placeholder.**

### 9.1 Tourism destinations (with real coordinates)

| # | Name | City/District | Type | Latitude | Longitude | Entry Fee (₹) | Best Time |
|---|------|---------------|------|----------|-----------|---------------|-----------|
| 1 | Dassam Falls | Ranchi | Waterfall | 23.3502 | 85.6601 | 0 | Oct–Mar |
| 2 | Hundru Falls | Ranchi | Waterfall | 23.4461 | 85.6600 | 20 | Oct–Mar |
| 3 | Jonha Falls (Ghaghri) | Ranchi | Waterfall | 23.3692 | 85.6147 | 0 | Oct–Mar |
| 4 | Panch Gagh Falls | Ranchi | Waterfall | 23.3170 | 85.4330 | 0 | Oct–Mar |
| 5 | Rock Garden | Ranchi | Garden | 23.3630 | 85.3100 | 10 | All year |
| 6 | Sun Temple | Ranchi | Temple | 23.3470 | 85.2760 | 0 | All year |
| 7 | Tagore Hill (Morhabadi) | Ranchi | Hill/Viewpoint | 23.3760 | 85.3120 | 0 | All year |
| 8 | Ranchi Lake (Bada Talab) | Ranchi | Lake | 23.3560 | 85.3350 | 0 | All year |
| 9 | Birsa Zoological Park | Ranchi | Wildlife | 23.3470 | 85.3160 | 40 | Oct–Mar |
| 10 | Muta Crocodile Centre | Ranchi | Wildlife | 23.4220 | 85.4500 | 15 | Oct–Mar |
| 11 | Gonda Hill | Ranchi | Hill | 23.3670 | 85.2980 | 0 | All year |
| 12 | Jagannath Temple | Ranchi | Temple | 23.3520 | 85.3280 | 0 | All year |
| 13 | Pahari Mandir | Ranchi | Temple/Hill | 23.3650 | 85.3140 | 0 | All year |
| 14 | Patratu Valley | Ramgarh | Valley/Dam | 23.5900 | 85.3000 | 0 | Oct–Mar |
| 15 | Netarhat | Latehar | Hill Station | 23.4700 | 84.2600 | 0 | Oct–Mar |
| 16 | Betla National Park | Latehar | Wildlife/National Park | 23.8870 | 84.1900 | 250 (Indians) | Oct–Mar |
| 17 | Lodh Falls | Latehar | Waterfall (tallest in Jharkhand) | 23.5500 | 84.0500 | 0 | Oct–Mar |
| 18 | Hirni Falls | West Singhbhum | Waterfall | 22.7500 | 85.6700 | 0 | Oct–Mar |
| 19 | Dasham Falls | Ranchi | Waterfall | 23.3502 | 85.6601 | 0 | Oct–Mar |
| 20 | Deoghar (Baidyanath Temple) | Deoghar | Pilgrimage | 24.4830 | 86.6960 | 0 | All year |
| 21 | Parasnath Hill | Giridih | Pilgrimage/Hill | 24.0000 | 86.3000 | 0 | Oct–Mar |
| 22 | Usri Falls | Giridih | Waterfall | 24.1830 | 86.3000 | 0 | Oct–Mar |
| 23 | Hazaribagh Lake | Hazaribagh | Lake | 24.0000 | 85.3660 | 0 | All year |
| 24 | Canary Hill | Hazaribagh | Hill | 23.9830 | 85.3500 | 0 | All year |
| 25 | McCluskieganj | Ranchi | Colonial heritage | 23.4500 | 85.3000 | 0 | Oct–Mar |
| 26 | Surya Temple (Bundu) | Ranchi | Temple | 23.1500 | 85.5830 | 0 | All year |
| 27 | Rajrappa Temple | Ramgarh | Temple/Pilgrimage | 23.7000 | 85.7000 | 0 | All year |
| 28 | Topchanchi Lake | Dhanbad | Lake | 23.9000 | 86.4660 | 0 | Oct–Mar |
| 29 | Maithon Dam | Dhanbad | Dam | 23.7660 | 86.8000 | 0 | Oct–Mar |
| 30 | Tilaiya Dam | Koderma | Dam | 24.4330 | 85.5160 | 0 | Oct–Mar |

### 9.2 Hotels in Ranchi / Jharkhand (real names)

| Hotel Name | City | Approx Price/Night (₹) | Rating | Amenities |
|------------|------|------------------------|--------|-----------|
| Radisson Hotel Ranchi | Ranchi | 6,500 | 4.5 | WiFi, Pool, Restaurant, Gym, Parking |
| Capitol Residency Hotel | Ranchi | 5,000 | 4.3 | WiFi, Restaurant, Bar, Parking |
| Hotel AVN Grand | Ranchi | 3,500 | 4.2 | WiFi, Restaurant, Parking |
| Hotel Capitol Hill | Ranchi | 4,000 | 4.1 | WiFi, Restaurant, Parking |
| Hotel Green Acres | Ranchi | 3,000 | 4.0 | WiFi, Restaurant, Parking, EV Charging |
| Hotel Birsa Vihar | Netarhat | 2,500 | 3.9 | WiFi, Restaurant |
| Hotel Vindravani | Betla | 2,000 | 3.8 | Restaurant, Parking |
| Hotel Cloud 9 | Ranchi | 3,800 | 4.1 | WiFi, Restaurant, Bar |
| Treebo Trend Radha Krishna | Ranchi | 2,200 | 4.0 | WiFi, Restaurant |
| FabHotel Prime Galaxy | Ranchi | 2,500 | 4.0 | WiFi, Restaurant |
| Hotel Akashdeep | Ranchi | 2,800 | 3.9 | WiFi, Restaurant |
| Eco-Lodge Betla | Betla | 1,800 | 4.3 | Eco-Certified, Solar Power, EV, Organic Food |
| Netarhat Tourist Lodge | Netarhat | 1,500 | 3.7 | Basic, Government-run |
| Hazaribagh Tourist Lodge | Hazaribagh | 1,500 | 3.7 | Basic, Government-run |

### 9.3 Flights to/from Ranchi (Birsa Munda Airport — IXR)

| From | To | Airlines | Approx Duration | Approx Price (₹) |
|------|-----|----------|----------------|-----------------|
| Delhi (DEL) | Ranchi (IXR) | IndiGo, Air India, Vistara | 1h 50m | 4,500–7,000 |
| Kolkata (CCU) | Ranchi (IXR) | IndiGo, SpiceJet | 1h 05m | 3,000–5,000 |
| Mumbai (BOM) | Ranchi (IXR) | IndiGo, Air India | 2h 15m | 5,000–8,000 |
| Bengaluru (BLR) | Ranchi (IXR) | IndiGo | 2h 30m | 5,500–9,000 |
| Patna (PAT) | Ranchi (IXR) | IndiGo | 1h 00m | 2,500–4,000 |

### 9.4 Jharkhand festivals & events

| Event | Month | Location | Description |
|-------|-------|----------|-------------|
| Sarhul | March–April | Statewide | Spring festival celebrating nature/Sal tree |
| Karma Festival | August | Statewide | Tribal festival of karma tree worship |
| Tusu Parab | January | Singhbhum | Harvest festival with colourful Tusu art |
| Sohrai | October–November | Statewide | Cattle festival, wall painting tradition |
| Bandna | November | Statewide | Festival honouring cattle and agriculture |
| World Tribal Day | August 9 | Ranchi | Celebration of tribal heritage |
| Jharkhand Foundation Day | November 15 | Statewide | State formation day celebrations |

### 9.5 Jharkhand souvenirs / handicrafts

| Product | Origin/Community | Approx Price (₹) |
|---------|-----------------|------------------|
| Dokra Metal Craft | Malhar, Bastar region | 500–5,000 |
| Sohrai Painting | Hazaribagh | 300–3,000 |
| Paitkar Painting | Amadubi | 500–5,000 |
| Bamboo Craft | Statewide | 100–1,500 |
| Terracotta | Statewide | 100–1,000 |
| Tussar Silk Saree | Kharsawan | 2,000–8,000 |
| Lac Bangles | Statewide | 50–500 |
| Wooden Toys | Statewide | 100–800 |
| Tribal Jewellery | Statewide | 200–2,000 |

### 9.6 HelTourism packages

| Package | Route | Duration | Price (₹) |
|---------|-------|----------|-----------|
| Ranchi Valley Skyview | Ranchi → Patratu Valley → return | 30 min | 4,500/person |
| Waterfall Circuit | Ranchi → Dassam → Hundru → Jonha → return | 45 min | 6,500/person |
| Netarhat Sunrise | Ranchi → Netarhat → return | 60 min | 8,000/person |
| Betla Wildlife Safari Air | Ranchi → Betla → return | 75 min | 10,000/person |

---

## 10. Responsible-Tourism Layer

### 10.1 Footfall-aware discovery
- Each destination carries `crowdLevel` (low/medium/high) and `predictedFootfall(date)`.
- Weekends and holidays → high footfall for popular spots (Hundru Falls, Dassam Falls).
- When `high`, show "Busy on these dates" hint and surface alternatives (e.g., suggest Panch Gagh Falls when Hundru is crowded).
- Colour-coded indicator: green (low), orange (medium), red (high).

### 10.2 Eco-certified inventory
- Hotels and transport carry `ecoCertified` flag + `ecoScore` (0–100).
- Eco-certified items get the green `EcoBadge`.
- Sort eco-certified items up when user profile indicates eco-preference.

### 10.3 Transparent pricing
- Booking breakdown: provider share, platform fee, taxes, local-community share %.
- Example: "₹2,000 → ₹1,600 (80%) goes directly to local homestay owner."

### 10.4 Carbon footprint
- Each trip leg shows estimated CO₂ (e.g., "2.1 kg CO₂").
- EV shuttle / train alternatives badged and offered as swaps.

### 10.5 Verified ratings
- Ratings tied to verified bookings only. "Verified" tag shown.
- No anonymous reviews.

---

## 11. Email Integration — Detailed Spec

### 11.1 Itinerary email
- **Trigger:** "Share Itinerary" button on My Trips card or Trip Timeline screen.
- **Method:** `Intent.ACTION_SEND` with MIME `message/rfc822`.
- **Subject:** "My TRU Trip: Ranchi Waterfall Circuit — planned with TRU AI"
- **Body (plain text + HTML):**
  ```
  Hi,

  I've planned a trip to Ranchi, Jharkhand using TRU AI!

  📍 Trip: Ranchi Waterfall Circuit
  📅 Dates: Oct 12 – Oct 14 (3 days)
  💰 Budget: ₹12,000 (Standard)
  🌿 Carbon Footprint: 6.3 kg CO₂ total

  Day 1 (Oct 12):
   • 09:00 — Visit Dassam Falls (Ranchi)
   • 12:00 — Lunch at local homestay
   • 15:00 — Rock Garden
   • Stay: Hotel AVN Grand, Ranchi

  Day 2 (Oct 13):
   • 08:00 — Hundru Falls
   • 12:00 — Jonha Falls
   • 16:00 — Sun Temple sunset
   • Stay: Hotel Green Acres (Eco-Certified)

  Day 3 (Oct 14):
   • 09:00 — Panch Gagh Falls
   • 13:00 — Birsa Zoological Park
   • 17:00 — Departure

  Total Cost: ₹11,850
  Fair-Trade Direct Benefit: 82% to local providers

  Planned with TRU AI — Jharkhand Tourism.
  ```
- **Extras:** optional map link (`https://maps.google.com/?q=Dassam+Falls+Ranchi`), image attachment if available.

### 11.2 Destination share
- **Trigger:** "Share" button on Destination Detail screen or sidebar "Share on Social Media".
- **Method:** `Intent.ACTION_SEND` with MIME `text/plain` (for WhatsApp, etc.) or `image/*` (with image).
- **Content:** destination name, short description, map link, "Discovered via TRU AI — Jharkhand Tourism".

### 11.3 Implementation
```kotlin
fun shareItinerary(context: Context, trip: Trip) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_SUBJECT, "My TRU Trip: ${trip.name}")
        putExtra(Intent.EXTRA_TEXT, formatItineraryEmail(trip))
    }
    context.startActivity(Intent.createChooser(intent, "Share Itinerary via"))
}
```

---

## 12. Maps Integration — Detailed Spec

### 12.1 Google Maps Compose setup
- Dependency: `com.google.maps.android:maps-compose`
- API key in `AndroidManifest.xml` meta-data (`com.google.android.geo.API_KEY`) — read from `local.properties` / `BuildConfig`.

### 12.2 Map screen
- `GoogleMap` composable fills the screen.
- Camera default: Ranchi (lat: 23.3441, lng: 85.3096), zoom 10.
- All destinations from Section 9.1 rendered as `Marker` with custom `MarkerState`.
- On marker click → show `InfoWindow` or custom floating card with: destination name, thumbnail, rating, "Navigate" button.

### 12.3 Turn-by-turn navigation
```kotlin
fun navigateTo(context: Context, lat: Double, lng: Double, label: String) {
    val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng($label)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
    }
    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    }
}
```

### 12.4 User location
- Request `ACCESS_FINE_LOCATION` permission.
- Show blue dot for user's current location.
- Default to Ranchi coordinates if permission denied.

### 12.5 Clustering
- When zoomed out, cluster markers to avoid clutter.
- Use `Clustering` from maps-compose-utils.

---

## 13. Technology Stack

- **Language:** Kotlin 2.x
- **UI:** Jetpack Compose + Material 3
- **Navigation:** Compose Navigation (single-activity) + drawer for sidebar
- **State:** StateFlow / ViewModel
- **Local persistence:** Room (trips, saved places, cached destinations, souvenirs cart)
- **Networking:** Retrofit + kotlinx.serialization
- **AI:** Google AI Studio / Gemini SDK for itinerary generation
- **Maps:** Google Maps Compose SDK
- **DI:** Hilt
- **Image loading:** Coil (load real destination images from URLs or bundled assets)
- **Audio:** ExoPlayer / MediaPlayer for audio guides; Android TTS as fallback
- **Email/Share:** Android Intent.ACTION_SEND
- **Payments:** tokenised stub (pluggable for real PSP)

### 13.1 Module/package structure
```
com.tru.jharkhand
├── MainActivity.kt
├── TRUApplication.kt
├── ui/
│   ├── theme/            (Color.kt, Type.kt, Shape.kt, Theme.kt)
│   ├── navigation/       (TRUNavHost.kt, DrawerState, BottomNav)
│   ├── home/             (HomeScreen.kt, HomeViewModel.kt)
│   ├── plan/             (AIPlanningScreen.kt, PlanningViewModel.kt)
│   ├── booking/          (BookingConfigScreen.kt, FlightSearchScreen.kt)
│   ├── discovery/        (MapDiscoveryScreen.kt, DestinationDetailScreen.kt)
│   ├── trips/            (MyTripsScreen.kt, TripTimelineScreen.kt)
│   ├── wallet/           (WalletScreen.kt)
│   ├── sidebar/
│   │   ├── JharkhandGlanceScreen.kt
│   │   ├── EventsScreen.kt
│   │   ├── HospitalityScreen.kt
│   │   ├── SouvenirsScreen.kt
│   │   ├── HeliTourismScreen.kt
│   │   ├── AudioGuideScreen.kt
│   │   └── ShareScreen.kt
│   └── components/       (DestinationCard.kt, TripPlannerWidget.kt, EcoBadge.kt,
│                          VirtualCard.kt, AudioPlayerBar.kt, FootfallIndicator.kt,
│                          SidebarDrawer.kt)
├── domain/
│   ├── model/            (Trip, Itinerary, Destination, Hotel, Flight, Event,
│   │                      Souvenir, HeliTourPackage, PaymentCard, EcoInfo, AudioGuide)
│   └── usecase/          (GenerateItineraryUseCase, SearchFlightsUseCase,
│                          GetEcoAlternativesUseCase, ShareItineraryUseCase,
│                          NavigateToDestinationUseCase, PlayAudioGuideUseCase)
└── data/
    ├── remote/           (GeminiService.kt, BookingApi.kt)
    ├── local/            (TripDao.kt, SavedPlaceDao.kt, AppDatabase.kt)
    ├── repository/       (TripRepositoryImpl.kt, DestinationRepositoryImpl.kt,
    │                      HotelRepositoryImpl.kt, EventRepositoryImpl.kt,
    │                      SouvenirRepositoryImpl.kt, HeliTourRepositoryImpl.kt)
    └── seed/             (JharkhandData.kt — all real places, hotels, flights, events,
                            souvenirs, heli-tours with coordinates and images)
```

---

## 14. AI Integration Plan (Gemini)

### 14.1 Itinerary generation
- **Input:** free-text dream trip + structured constraints (destination from Jharkhand, dates, duration, budget tier ₹, style).
- **Gemini task:** produce day-by-day itinerary (JSON: days[], each with morning/afternoon/evening slots, place name from the Jharkhand place list, reason, estimated cost ₹, eco-score, carbon kg CO₂).
- **Output:** rendered into Trip Timeline screen (7.8), editable.

### 14.2 Conversational planning
- Multi-turn chat (7.2). User refines ("make day 2 more relaxed", "swap hotel for eco-lodge").
- Gemini returns updated structured itinerary.

### 14.3 Recommendation reasoning
- For each curated card, Gemini supplies a one-line "why this for you" reason based on user profile.

### 14.4 Eco-alternative suggestions
- When a destination is high footfall, Gemini proposes 1–2 lesser-known Jharkhand alternatives (e.g., Panch Gagh instead of Hundru).

### 14.5 Smart defaults
- Pre-fill booking from user's location (Ranchi), past trips, budget tier.

---

## 15. Acceptance Criteria

The generated Kotlin app is "done" when:

1. It compiles and runs on an Android emulator (API 30+) with NO manual fixes.
2. **Sidebar drawer** opens via hamburger/swipe and shows all 7 items in the exact order with the exact labels from Section 7.0. Tapping each navigates to its screen with real Jharkhand data.
3. **Home screen** shows "Ranchi, Jharkhand" as location, real destination cards (Dassam Falls, Rock Garden, Sun Temple, etc.), and the AI trip builder card.
4. **AI Planning screen** calls Gemini and renders a real Jharkhand itinerary into the Trip Timeline.
5. **Map screen** renders live Google Maps with markers for all 30 destinations from Section 9.1, tappable info cards, and "Navigate" launching Google Maps.
6. **Bottom nav** (Home / Plan / Trips / Wallet) works on all main screens.
7. **Hospitality screen** shows real Ranchi/Jharkhand hotels with names, prices, ratings, and "Book Now".
8. **Flight search** shows real airlines and routes to/from Ranchi (IXR) with prices.
9. **Souvenirs screen** shows real Jharkhand handicrafts with prices and artisan info.
10. **Audio Guide** plays audio for each destination (TTS or audio file).
11. **HelTourism** shows real helicopter tour packages over Jharkhand destinations.
12. **Email sharing** opens an email intent with a formatted itinerary body.
13. **Eco overlay** shows: at least one eco badge, one footfall indicator, one carbon figure, one price breakdown.
14. **My Trips** persists trips in Room across restarts.
15. **Design** follows Apple/Google aesthetic: rounded cards, soft shadows, clean typography, smooth transitions.
16. **No hardcoded secrets** — Gemini key via BuildConfig.
17. **Every button works** — no dead ends, no "coming soon", no placeholders.

---

## 16. Out of Scope (v1)

- Real payment processing / PCI scope (tokenised stub only).
- iOS / web (Android only).
- Full blockchain backend (model the data shape only).
- Social / community feed.
- Offline AI generation (requires connectivity for Gemini).
- Real hotel/flight live inventory API (use realistic seed data; pluggable for real APIs later).

---

## 17. Image Assets — Instructions

> **All images in the app must be real photos of Ranchi / Jharkhand destinations.**

- Use Coil to load images from URLs where possible. Suggested sources:
  - Wikimedia Commons (free, real photos of Jharkhand destinations)
  - Jharkhand Tourism official website (tourism.jharkhand.gov.in)
  - Unsplash (search: "Ranchi", "Jharkhand", "Dassam Falls", "Hundru Falls", "Patratu Valley", "Netarhat", "Betla")
- For each destination in `JharkhandData.kt`, include an `imageUrl` field pointing to a real image URL.
- If network images fail, bundle a local placeholder image in `res/drawable/` for each major destination.
- Hotel images: use real hotel exterior/room photos from the hotel's official website or booking platforms.
- Souvenir images: use real photos of Dokra craft, Sohrai paintings, tussar silk, etc. from Wikimedia Commons.

### Image URL examples (real, public):
- Dassam Falls: `https://commons.wikimedia.org/wiki/File:Dassam_Falls_Ranchi.jpg` (verify and use real URLs)
- Hundru Falls: `https://commons.wikimedia.org/wiki/File:Hundru_Falls.jpg`
- Sun Temple Ranchi: `https://commons.wikimedia.org/wiki/File:Sun_Temple_Ranchi.jpg`
- Patratu Valley: search Wikimedia for "Patratu Valley"
- Netarhat: search Wikimedia for "Netarhat sunset"

> **Note to AI Studio:** Please search for and use real, working image URLs for each destination. Verify URLs are accessible. If a URL is broken, fall back to a bundled asset.

---

## 18. Sources & References

- TRU app UI design — original Visily prototype (sidebar with 7 items, Ranchi home screen with Dassam Falls, Rock Garden, Sun Temple, Patratu Valley).
- Google AI Studio output — generated Home screen and AI Assistant screen (forest green + lime palette, destination cards, trip planner widget).
- "Regenerative Tourism in the Era of AI and Green Innovation: A Pathway to Sustainable Destination Management in Jharkhand, India" — research paper (BIT Mesra, 2025). Concepts of AI-driven visitor management, eco-certified inventory, fair-trade pricing, carbon footprint visibility, and verified ratings adapted from this paper.
- Global case studies: Costa Rica (AI wildlife conservation), Norway (zero-emission fjord tourism), Bhutan (high-value, low-impact tourism).
- Apple Human Interface Guidelines (HIG) — design principles applied to the UI system.
- Google Material 3 Design System — component patterns and colour system.
- Jharkhand Tourism official website: tourism.jharkhand.gov.in
- Google Maps Platform — Maps SDK for Android, Maps Compose.

---

*End of problem statement v2. Hand this document to Google AI Studio and instruct: "Generate the complete, fully-working native Android Kotlin + Jetpack Compose project per all sections. The sidebar MUST match Section 7.0 exactly. All data MUST be real Ranchi/Jharkhand data from Section 9. Maps must be fully integrated. Email must work. UI must follow Apple/Google design standards from Section 8. Every screen must be functional — no placeholders."*
