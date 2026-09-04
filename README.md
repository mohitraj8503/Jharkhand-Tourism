<p align="center">
  <a href="https://github.com/mohitraj8503/Jharkhand-Tourism">
    <img src="public/jharvista_banner.png" alt="JharVista — Tourism in Jharkhand" width="820" style="max-width: 100%; border-radius: 24px;" />
  </a>
</p>

<p align="center">
  <b>Explore Jharkhand Without a Carbon Trail</b><br>
  <i>An AI-powered, eco-conscious travel companion unifying authentic discovery, EV mobility, live itineraries, and regenerative tourism for the State of Jharkhand.</i>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android%20(Native)-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Kotlin%202.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose%20%7C%20Apple%20Aesthetic-007AFF?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Build-Gradle%209.5-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle" />
  <img src="https://img.shields.io/badge/Unit%20Tests-100%25%20Passing-brightgreen?style=for-the-badge" alt="Tests" />
</p>

<p align="center">
  <a href="https://github.com/mohitraj8503/Jharkhand-Tourism/raw/main/JharVista.apk">
    <img src="https://img.shields.io/badge/Download_APK-v1.1_(Direct_Download)-2ea44f?style=for-the-badge&logo=android&logoColor=white" alt="Download APK" />
  </a>
  <a href="https://github.com/mohitraj8503/Jharkhand-Tourism/releases/latest">
    <img src="https://img.shields.io/badge/GitHub_Releases-v1.1_Latest-blue?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Releases" />
  </a>
</p>

> [!TIP]
> **Direct APK Download**: Download [JharVista.apk](https://github.com/mohitraj8503/Jharkhand-Tourism/raw/main/JharVista.apk) (~26 MB) directly to install and test on your phone immediately!

---

## 📖 Overview

**JharVista** is a native Android application built specifically for Jharkhand Tourism. It transcends standard travel apps by fusing **authentic destination discovery**, **responsible and regenerative tourism**, **electric mobility rental networks**, and **AI-powered itinerary generation** inside a clean, premium **Apple-inspired iOS design system**.

From the dense sal canopies of **Betla National Park** and mist-shrouded sunrises of **Netarhat**, to the hairpin serpentines of **Patratu Valley** and the sacred spires of **Baidyanath Dham**, JharVista is built from the ground up with authentic, verified Jharkhand data and Wikimedia photo assets.

---

## ✨ Key Features

### 📍 1. "Where to Go" (Authentic Discovery)
- **Verified Jharkhand Landmarks**: 15+ curated destinations across Latehar, Ranchi, Deoghar, Giridih, and Hazaribagh.
- **Smart Categorization**: Trending, Top Picks, Eco-Hotspots, Sacred Circuits, and Nearby Wonders.
- **Dynamic Footfall & Eco-Metrics**: Real-time crowd level indicators (`Low`, `Medium`, `High`), predicted peak footfall alerts, and alternative offbeat recommendations.
- **Comprehensive Details**: Timings, entry fees, optimal seasons, historical context, and high-resolution photo galleries.

### 🚗 2. "Rentals" (Explore Without a Carbon Trail)
- **Electric Mobility Fleet**: High-performance EVs across Jharkhand (Tata Nexon EV Max, MG ZS EV Long Range, Tata Tiago EV, Ather 450X, Revolt RV400).
- **Route Range Verification Tool**: Evaluates trip distances against battery capacities using an 85% safety buffer for ghat elevation and hill terrains. Suggests optimal fast-charging stops along NH-33, Latehar Ghats, and Deoghar Bypass.
- **Charging Station Network**: Real-time locator for verified charging stations (Tata Power, Jio-bp Pulse, Statiq, BPCL) with connector types and power outputs (30kW – 60kW).
- **Environmental Impact Tracker**: Live CO₂ emission savings and fuel avoidance calculations on every rental.

### 🤖 3. AI Planning & Smart Concierge
- **TRU / JharVista AI Assistant**: Powered by Gemini API to curate custom day-by-day travel schedules matching budget, party size, and travel interests.
- **Interactive Multi-Day Itineraries**: Auto-populated activities, route suggestions, and EV pickup/drop-off sync.

### 🧳 4. Live Trips & Timelines
- **Unified Trip Dashboard**: Active bookings, flight details, hotel reservations, and rental receipts in one place.
- **Chronological Itinerary Timeline**: Step-by-step milestone cards with pickup, sightseeing, dining, and return checkpoints.

### 🏛️ 5. Authentic Culture, Hospitality & Souvenirs
- **Jharkhand at a Glance**: Geography, tribal heritage, cuisine (Dhuska, Rugda, Thekua), and biodiversity overview.
- **Tribal Handicrafts & Souvenirs**: Showcase of authentic Sohrai & Khovar wall paintings, Dokra brass castings, and terracotta crafts directly linked to local artisans.
- **HeliTourism & Aerial Tours**: Scenic joyrides and temple air shuttles across Patratu and Deoghar.
- **Cultural Calendar**: Schedule for Sarhul, Karma, Tusu, and Chhau Dance festivals.

---

## 🎨 Apple-Style UI Design System

JharVista strictly implements an Apple iOS design language crafted with Jetpack Compose:

- **Color Palette**:
  - Background: `#F2F2F7` (Apple System Grey 6 / Grouped Background)
  - Cards & Containers: `#FFFFFF` (Pure White) with 16dp rounded corners and subtle hairline borders
  - Primary Accent: Deep Forest Green (`#1E392A`) & Emerald Glow (`#2E7D32`)
  - Accent Highlight: Lime Eco Accent (`#A3E635`)
- **Typography**: San Francisco style proportions (TitleLarge, HeadlineMedium, BodyLarge) with high-contrast legibility.
- **Components**: Floating segmented controllers, pill filters, modal bottom sheets (`EVRentalBookingSheet`), and micro-interactions.

---

## 🏗️ Architecture & Technology Stack

JharVista follows Google's recommended **Clean Architecture** and **MVVM** pattern:

```
┌────────────────────────────────────────────────────────┐
│               UI Layer (Jetpack Compose)               │
│  Navigation, Screens (WhereToGo, Rentals, Plan, Trips)  │
│                   ViewModels & StateFlow               │
└───────────────────────────┬────────────────────────────┘
                            │
┌───────────────────────────▼────────────────────────────┐
│                      Domain Layer                      │
│   Use Cases: CheckEVRange, SearchRentals, CalculateCost│
│                Models (EVRental, Destination)          │
└───────────────────────────┬────────────────────────────┘
                            │
┌───────────────────────────▼────────────────────────────┐
│                       Data Layer                       │
│    Room SQLite Database (TruDatabase & TruDao)         │
│     Repositories, Seed Data (JharkhandData.kt)         │
│    Remote API: Retrofit, OkHttp, Moshi, Gemini AI      │
└────────────────────────────────────────────────────────┘
```

### Core Libraries
- **Language**: Kotlin 2.0+
- **UI Toolkit**: Jetpack Compose with Material 3 foundation
- **Async & Reactive**: Kotlin Coroutines & StateFlow
- **Local Persistence**: Android Room with KSP
- **Image Loading**: Coil Compose with memory caching
- **AI Integration**: Google Generative AI (Gemini) SDK
- **Testing**:
  - Robolectric 4.14+ (Local Android Unit Testing on host)
  - Roborazzi (Automated Screenshot Regression Testing)
  - JUnit 4 & AndroidX Test

---

## 🧪 Testing & Code Quality

The project features automated unit, domain logic, and screenshot tests:

```bash
./gradlew testDebugUnitTest
```

### Test Suites (100% Passed)
| Test Suite | Purpose | Status |
|---|---|---|
| `JharkhandDataTest` | Validates authentic destinations, coordinates, EV specifications, and charging hubs | ✅ Passed |
| `CheckEVRangeUseCaseTest` | Tests range calculation, 15% terrain buffer, and charging stop recommendations | ✅ Passed |
| `ExampleRobolectricTest` | Context verification and application resources validation | ✅ Passed |
| `ExampleUnitTest` | Core arithmetic and baseline assertions | ✅ Passed |
| `GreetingScreenshotTest` | Native Roborazzi compose screenshot verification | ✅ Passed |

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Ladybug (2024.2.1+) or newer
- **JDK**: Java 17, 21, or 25 (JBR recommended)
- **Android SDK**: Platform 36 (Android 15/16 preview) with `minSdk = 24`

### Setup & Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/mohitraj8503/Jharkhand-Tourism.git
   cd Jharkhand-Tourism
   ```

2. **Open in Android Studio**:
   - Select **Open an Existing Project** and choose the cloned folder.
   - Allow Gradle to sync dependencies.

3. **Build the Debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```
   The APK will be generated at:
   `app/build/outputs/apk/debug/app-debug.apk`

4. **Run Unit Tests**:
   ```bash
   ./gradlew testDebugUnitTest
   ```

---

## 📱 Application Details

- **App Name**: JharVista
- **Package ID**: `com.jharvista.tourism`
- **Version**: 1.1 (VersionCode: 2)
- **Target OS**: Android 7.0 (API 24) through Android 16 (API 36)

---

## 🤝 Acknowledgements

- **Department of Tourism, Govt. of Jharkhand** for inspiration, cultural heritage, and geographical data.
- **Wikimedia Commons** for public domain, authentic documentary imagery of Jharkhand's natural landscapes and cultural heritage.

---

<p align="center">
  Crafted with ❤️ for <b>Jharkhand — The Land of Forests and Waterfalls</b>
</p>
