# TRU — Problem Statement & Build Brief for Google AI Studio

**Document type:** Problem statement / build brief for generating a native Android (Kotlin) app inside Google AI Studio
**App name:** TRU — AI-Powered Travel Companion
**Target platform:** Native Android (Kotlin + Jetpack Compose)
**Generation target:** Google AI Studio (Gemini) should use this document to generate the complete UI and app structure
**Date:** September 2026

---

## 1. Executive Summary

TRU is an AI-powered travel companion that unifies **discovery, planning, booking, and trip management** into one seamless native Android experience. Today's traveller juggles six to eight disconnected apps — one for flights, one for hotels, one for maps, one for itineraries, one for payments — and still ends up with a fragmented, stressful trip. TRU replaces that fragmentation with a single, AI-first surface where a traveller describes their dream trip in plain language and the app builds, books, and manages the entire journey.

This document defines the problem TRU solves, the user it serves, the functional and non-functional requirements, the full screen-by-screen UI specification, the technology stack, and the AI integration plan — so that Google AI Studio can generate a complete, production-shaped Kotlin codebase from it.

---

## 2. The Problem

### 2.1 Fragmented travel experience
Modern travel is broken across too many tools. A typical trip involves:

- A search engine for destination inspiration
- A flight aggregator for tickets
- A hotel/OTA platform for stays
- A maps app for navigation
- A notes app or spreadsheet for the itinerary
- A payments app for settling bills
- A photo app for memories

No single tool connects the **discovery → planning → booking → on-trip → memory** loop. Context is lost at every handoff: the place a user saved in the discovery app never shows up when they are booking a hotel; the itinerary built in a notes app is not live during the trip.

### 2.2 Generic, not personal
Existing travel apps return the same "top 10 things to do" list to every user. They ignore budget, travel style, pace, dietary needs, accessibility, and the traveller's own past trips. Recommendation quality is low because it is driven by popularity, not by fit.

### 2.3 Booking is high-friction
Configuring a booking — destination, dates, duration, budget tier, passengers, class — still requires jumping across multiple forms, tabs, and screens with no smart defaults and no conversational fallback.

### 2.4 No responsible-tourism layer
Popular destinations are being loved to death. There is no mechanism in consumer travel apps to distribute footfall toward lesser-known spots, surface eco-certified stays, or let a traveller see the carbon cost of their choices. Travel apps today optimise for conversion, not for the destination's long-term health.

### 2.5 Trips are not managed, only booked
Once a booking is made, the app's job is usually over. The traveller is left to manage the day-to-day timeline, live changes, payments, and memories across other tools. The "full journey" — the part where a traveller actually needs help — is unsupported.

---

## 3. Who TRU Is For

**Primary persona — "The Intentional Traveller" (age 22–40)**
- Travels 3–8 times a year, mix of leisure and work
- Comfortable with AI assistants; prefers describing what they want over filling forms
- Cares about budget control and, increasingly, about the environmental and cultural impact of their travel
- Frustrated by app-hopping and by generic "top 10" recommendations

**Secondary persona — "The Planner"**
- Plans trips for a family or group
- Needs shared itineraries, clear cost breakdowns, and a single payments surface
- Values the trip-timeline view and the "My Trips" dashboard

**Tertiary persona — "The Eco-Conscious Explorer"**
- Actively seeks sustainable stays, local experiences, and low-carbon transport
- Wants transparency on where their money goes and the footprint of their choices

---

## 4. What TRU Does — Core Capabilities

TRU is built around seven capabilities. Each maps to one or more screens in the UI (specified in Section 7).

1. **AI-First Trip Planning** — A conversational surface where the traveller describes their dream trip ("7 nights in Bali in October, mid-budget, love beaches and local food") and TRU generates a complete, editable itinerary in seconds.

2. **Smart Decision Support** — Structured booking configuration (destination, dates, duration, budget tier: Budget / Standard / Luxury) with AI-driven smart defaults, alternatives, and trade-off nudges.

3. **Map-Integrated Discovery** — An interactive map with custom markers, a search bar, and floating info cards, so discovery and geography are never separated.

4. **Curated, Not Generic** — Recommendations driven by the traveller's budget, style, and history — not by global popularity. Every card surfaces a reason ("Timeless Heritage — Cairo, Egypt" / "Luxury Escape — Bali, Indonesia").

5. **Seamless Payments** — An in-app virtual card / wallet surface (cardholder, number, expiry, CVV, brand, balance) that handles booking and on-trip payments in one place.

6. **Trip Memory & Dashboard** — A "My Trips" dashboard with tabs (All / Upcoming / Ended / Past) and stats (Countries visited, Saved places, Upcoming trips) that turns each trip into a lasting record.

7. **Built for the Full Journey** — A live trip timeline (timestamped events, images, tabs like Trending / Top Picks / Nearby) that stays useful from departure to return, not just until the booking is made.

### 7.5 Responsible-tourism overlay (differentiator)
Layered across the above, TRU carries an optional **regenerative-tourism layer** inspired by current research on technology-driven sustainable destination management:

- **Footfall-aware suggestions** — when a destination is predicted to be overcrowded on chosen dates, TRU nudges the traveller toward equally beautiful, less-visited alternatives (e.g., suggest Panch Gagh Falls when Hundru Falls is saturated).
- **Eco-certified stays & EV transport** — preferentially surface net-zero accommodations and EV-based transport circuits.
- **Transparent, fair-trade pricing** — show where the traveller's money goes, with direct-to-local-provider payments that cut out exploitative middlemen.
- **Carbon footprint visibility** — let the traveller see the carbon cost of each leg and choose lower-impact options.
- **Decentralised, honest ratings** — blockchain-style verified ratings for stays, guides, and experiences to prevent fake reviews.

This overlay is what separates TRU from every generic booking app and aligns the product with the emerging "leave the destination better than you found it" philosophy.

---

## 5. Functional Requirements

| ID | Requirement |
|----|-------------|
| FR-1 | User can start a trip via a conversational AI prompt ("Tell me about your dream trip") and receive a full itinerary. |
| FR-2 | User can configure a booking via structured fields: destination, dates, duration, budget tier (Budget / Standard / Luxury), passengers, class, trip type (One Way / Round Trip). |
| FR-3 | User can discover destinations on an interactive map with custom markers and floating info cards. |
| FR-4 | User can browse curated destination cards filtered by Trending / Top Picks / Nearby, each with a title, subtitle (city, country), image, and a detail-action. |
| FR-5 | User can view and manage a virtual payment card (number, holder, expiry, CVV, brand) and a current balance / payment amount. |
| FR-6 | User can view a "My Trips" dashboard with tabs (All / Upcoming / Ended / Past) and lifetime stats (countries, saved places, upcoming). |
| FR-7 | User can open a live trip timeline with timestamped events, images, and discovery tabs (Trending / Top Picks / Nearby). |
| FR-8 | User can see a trip-readiness widget ("Your Japan Spring Trip is 68% Ready", "5 Days Left"). |
| FR-9 | User can see eco/footfall indicators on destinations and choose lower-impact alternatives. |
| FR-10 | User can see transparent price breakdowns showing the local-provider share. |
| FR-11 | User can view carbon footprint per trip leg and opt for greener options. |
| FR-12 | User can search flights (From / To / Date / Passengers / Class) and see results. |
| FR-13 | App remembers the user's location ("Your Location: Berlin, Germany") and uses it as a default. |

---

## 6. Non-Functional Requirements

- **Platform:** Native Android, Kotlin, Jetpack Compose, Material 3.
- **Min SDK:** Android 8.0 (API 26). Target SDK: latest stable.
- **Architecture:** MVVM + Clean Architecture layers (presentation / domain / data). Single-activity, Compose-navigation.
- **Offline-first:** Core itinerary and trip data cached locally (Room); AI and booking calls go remote.
- **AI integration:** Gemini via Google AI Studio for itinerary generation, conversational planning, and recommendation reasoning.
- **Performance:** Cold start under 2s; map and lists at 60fps; no blocking I/O on the main thread.
- **Accessibility:** WCAG-minded contrast, large-touch-target list/map items, screen-reader labels on all interactive elements.
- **Security:** No raw card data stored in plaintext; payments tokenised; no secrets in code.

---

## 7. UI / Screen Specification

This section is the direct spec Google AI Studio should generate Kotlin + Compose code against. The design language is a modern "bento" card layout, rounded corners (squircle shapes), soft shadows, generous whitespace, and the colour system in Section 8.

### Design language
- **Layout style:** bento-box — rounded rectangular tiles of varying sizes on a light off-white/grey background.
- **Shapes:** high border radius on all containers and buttons; squircle where possible.
- **Motion:** subtle scale/fade on card tap; bottom-sheet transitions for booking config.
- **Density:** comfortable; never cramped.

### 7.1 Home / Discovery screen
- **Top bar:** circular profile avatar (left), "Your Location" label + selected location (e.g., "Berlin, Germany") (centre-left), circular search icon (right).
- **Category segmented control:** pill toggle — `Trending` | `Top Picks` | `Nearby`. Active tab highlighted with the lime accent.
- **Destination cards:** horizontal scroll, two-up. Each card: image, title (e.g., "Timeless Heritage"), subtitle (e.g., "Cairo, Egypt"), and a small lime square detail-action button (diagonal arrow). Optionally an eco badge.
- **Trip Planner widget (bottom):** wide card — label "Trip Planner", a lime status badge ("5 Days Left"), progress text ("Your Japan Spring Trip is 68% Ready").
- **Bottom nav:** Home / Plan / Trips / Wallet / Profile.

### 7.2 AI Trip Planning screen
- **Chat thread:** conversational surface.
  - AI prompt bubble: "Tell me about your dream trip."
  - AI suggestion card (lime accent): "Plan Your Perfect Trip In Seconds With TRU AI" with explanatory subtext.
  - Primary action button: "Generate Itinerary" with a spark icon.
  - Secondary action: "Detailed Itinerary".
- **Header context:** "AI Assistant: Ready to help anytime" and a "Start Planning →" affordance.

### 7.3 Smart Decision Support / Booking config screen
- Vertical form list:
  - **Destination** — selectable chip (e.g., "Bali, Indonesia").
  - **Dates & Duration** — "Oct 12 – Oct 19 (7 nights)".
  - **Budget** — segmented: `Budget` | `Standard` | `Luxury`.
  - **Trip type** — segmented: `One Way` | `Round Trip`.
  - **Passengers** — stepper.
  - **Class** — dropdown (Economy / Premium / Business).
- Primary CTA: "Search Flights" (lime).

### 7.4 Map-Integrated Discovery screen
- Full-bleed interactive map with custom markers.
- Top search bar ("Search Destinations").
- Floating info card on marker tap (e.g., "Menlo Park, Palo Alto").
- Bottom sheet with curated nearby cards.

### 7.5 Flight booking screen
- Structured flight search form: From (e.g., London LHR) / To (e.g., Bali DPS) / Date (e.g., 12 Aug) / Passengers / Class / One Way | Round Trip.
- Results list: airline, departure–arrival times, duration, price (e.g., British Airways 11:00 → 12:25, $546).
- Recommendation cards with destination imagery (e.g., "Timeless Heritage — Cairo, Egypt").

### 7.6 Payments / Wallet screen
- Virtual card visual (lime/yellow gradient, Mastercard brand).
- Fields: cardholder name (e.g., "Paul Steven"), card number (masked, e.g., `•••• 3874` in UI; full only on reveal), expiry (06/27), CVV (•••).
- Floating balance / payment amount (e.g., "$26.50").
- Transaction list below.

### 7.7 My Trips dashboard
- Header: "My Trips".
- Tabs: `All` | `Upcoming` | `Ended` | `Past`.
- Stats grid: "12 Countries", "45 Saved Places", "8 Upcoming".
- Trip cards: name (e.g., "Berlin", "Paris"), dates, cover image, status badge.

### 7.8 Trip timeline / Itinerary screen
- Header: "My Trip: Berlin, Germany".
- Chronological timeline: timestamped events (e.g., 10:00 / 12:00 / 15:00) with images and details.
- Discovery tabs: `Trending` | `Top Picks` | `Nearby`.
- Editable: tap an event to swap, reschedule, or replace with an eco-alternative.

### 7.9 Trip-readiness widget (shared component)
- Used on Home and on trip detail.
- Props: trip name, percent ready, days left.

---

## 8. Design System

### 8.1 Colour palette
| Token | Value | Use |
|-------|-------|-----|
| `ForestGreen` | `#0B3D2E` (dark) | Presentation / dark surfaces, primary brand |
| `LimeAccent` | `#C6F432` (or similar lime) | Primary buttons, highlights, active states |
| `Surface` | `#F5F5F0` (off-white) | App background |
| `SurfaceCard` | `#FFFFFF` | Card background |
| `TextPrimary` | `#1A1A1A` | Primary text |
| `TextSecondary` | `#5A5A5A` | Secondary text |
| `EcoBadge` | `#2E7D32` | Eco / responsible indicators |
| `Error` | `#C62828` | Errors |

### 8.2 Typography
- Sans-serif system font (Inter or Roboto Flex).
- Headings: semibold, 20–28sp.
- Body: regular, 14–16sp.
- Captions / chips: medium, 12sp.

### 8.3 Spacing & shape
- Base spacing 4dp grid; common paddings 12 / 16 / 24dp.
- Card corner radius: 24dp; button radius: 16dp; chips: full pill.
- Elevations: low (1–2dp) for cards; none for flat lime buttons.

---

## 9. Technology Stack (for Google AI Studio codegen)

- **Language:** Kotlin 2.x
- **UI:** Jetpack Compose + Material 3
- **Navigation:** Compose Navigation (single-activity)
- **State:** StateFlow / ViewModel
- **Local persistence:** Room (itineraries, trips, saved places, cached destinations)
- **Networking:** Retrofit + kotlinx.serialization; Google AI Studio / Gemini SDK for generative features
- **Maps:** Google Maps Compose
- **DI:** Hilt
- **Image loading:** Coil
- **Payments:** tokenised payments stub (pluggable for a real PSP)
- **Async:** Coroutines + Flow

### 9.1 Suggested module/package structure
```
com.tru.app
├── MainActivity.kt
├── ui/
│   ├── theme/            (Color.kt, Type.kt, Shape.kt, Theme.kt)
│   ├── navigation/       (TRUNavHost.kt, Destinations.kt)
│   ├── home/             (HomeScreen.kt, HomeViewModel.kt)
│   ├── plan/             (AIPanningScreen.kt, PlanningViewModel.kt)
│   ├── booking/          (BookingConfigScreen.kt, FlightSearchScreen.kt)
│   ├── discovery/        (MapDiscoveryScreen.kt)
│   ├── trips/            (MyTripsScreen.kt, TripTimelineScreen.kt)
│   ├── wallet/           (WalletScreen.kt)
│   └── components/       (DestinationCard.kt, TripPlannerWidget.kt, EcoBadge.kt, VirtualCard.kt)
├── domain/
│   ├── model/            (Trip, Itinerary, Destination, Booking, PaymentCard, EcoInfo)
│   └── usecase/          (GenerateItineraryUseCase, SearchFlightsUseCase, GetEcoAlternativesUseCase)
└── data/
    ├── remote/           (GeminiService.kt, BookingApi.kt)
    ├── local/            (TripDao.kt, SavedPlaceDao.kt, AppDatabase.kt)
    └── repository/       (TripRepositoryImpl.kt, DestinationRepositoryImpl.kt)
```

---

## 10. AI Integration Plan (Google AI Studio / Gemini)

TRU's intelligence is powered by Gemini, accessed through Google AI Studio. The AI surface in TRU is the entry point, not a side feature.

### 10.1 Itinerary generation
- **Input:** free-text dream-trip description + structured constraints (destination, dates, duration, budget tier, style).
- **Gemini task:** produce a structured, day-by-day itinerary (JSON: days[], each with morning/afternoon/evening slots, place, reason, estimated cost, eco-score).
- **Output rendering:** parsed into the Trip Timeline screen (7.8) and made editable.

### 10.2 Conversational planning
- Multi-turn chat surface (7.2) where the traveller refines the trip ("make day 3 more relaxed", "swap the second hotel for something cheaper").
- Gemini maintains trip context and returns updated structured itinerary.

### 10.3 Recommendation reasoning
- For each curated card (7.1), Gemini supplies the one-line "why this for you" reason based on the user's profile and history — not generic marketing copy.

### 10.4 Eco-alternative suggestions
- When a destination is predicted to be over-visited on the chosen dates, Gemini proposes 1–2 lesser-known alternatives with comparable appeal and a lower predicted footfall.

### 10.5 Smart defaults
- Pre-fill booking config (7.3) from the user's home location, past trips, and stated budget tier.

---

## 11. Responsible-Tourism Layer — Detailed Spec

This layer is TRU's differentiator and should be woven through the codebase, not bolted on.

### 11.1 Footfall-aware discovery
- Each destination carries a `crowdLevel` (low/medium/high) and a `predictedFootfall(date)`.
- When `high`, the UI shows a "Busy on these dates" hint and surfaces alternatives.
- Data source: historical footfall + seasonality model (stubbed initially; real model later).

### 11.2 Eco-certified inventory
- Stays and transport options carry an `ecoCertified` flag and an `ecoScore`.
- Eco-certified items get the `EcoBadge` and are sorted up when the user's profile indicates eco-preference.

### 11.3 Transparent pricing
- Booking breakdown shows: provider share, platform fee, taxes, and local-community share.
- Goal: the traveller can see that a meaningful percentage reaches the local provider.

### 11.4 Carbon footprint
- Each trip leg shows estimated CO₂; greener alternatives (EV shuttle, train) are badged and offered as swaps.

### 11.5 Verified ratings
- Ratings are tied to verified bookings only; UI shows "verified" tags; no anonymous star-bombing possible.

---

## 12. Acceptance Criteria (for the generated app)

The Google-AI-Studio-generated Kotlin app is "done" when:

1. It compiles and runs on an Android emulator (API 30+) with no manual code fixes.
2. All eight screens in Section 7 render with the design system in Section 8.
3. The AI Planning screen (7.2) calls Gemini and renders a real generated itinerary into the Trip Timeline (7.8).
4. Bottom navigation moves between Home / Plan / Trips / Wallet.
5. Destination cards are data-driven (not hardcoded images) from a repository.
6. The eco overlay shows at least: one eco badge, one footfall hint, one carbon figure, and one price breakdown.
7. The My Trips dashboard shows persisted (Room) trips across app restarts.
8. No secrets are hardcoded; the Gemini key is read from `local.properties` / `BuildConfig`.

---

## 13. Out of Scope (v1)

- Real payment processing / PCI scope (tokenised stub only).
- Multi-platform (iOS / web) — Android only for this build.
- Full blockchain backend (model the data shape; do not implement a chain).
- Social / community feed features.
- Offline AI generation (requires connectivity for Gemini).

---

## 14. Sources & References

- TRU app UI design — provided mockups (bento layout, lime/forest-green palette, screen-by-screen feature tiles).
- "Regenerative Tourism in the Era of AI and Green Innovation: A Pathway to Sustainable Destination Management in Jharkhand, India" — research paper (BIT Mesra, 2025). Concepts of AI-driven visitor management, eco-certified inventory, fair-trade pricing, carbon footprint visibility, and verified ratings are adapted from this paper.
- Global case studies referenced in the research: Costa Rica (AI wildlife conservation), Norway (zero-emission fjord tourism), Bhutan (high-value, low-impact tourism).

---

*End of problem statement. Hand this document to Google AI Studio and ask it to generate the full Kotlin + Jetpack Compose project per Sections 6–12.*
