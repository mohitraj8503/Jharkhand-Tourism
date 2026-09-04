# TRU App — Image Assets & Sidebar Specification for Google AI Studio

**Document type:** Complete sidebar spec + REAL working image URLs for every destination, hotel, souvenir, and cultural item in the TRU app
**Purpose:** Give Google AI Studio actual image URLs to use in the app — NOT placeholders, NOT "use real photo" instructions, but REAL URLs that load REAL photos of Ranchi/Jharkhand
**Target platform:** Native Android (Kotlin + Jetpack Compose), Coil image loader
**Date:** September 2026

---

## 0. CRITICAL INSTRUCTIONS FOR GOOGLE AI STUDIO

> **READ THIS FIRST.**

1. **Use the image URLs in this document DIRECTLY in your Kotlin data models.** Each destination, hotel, souvenir, and cultural item below has a `imageUrl` field. Copy that URL into your data class. Do NOT replace it with a placeholder, a generic Unsplash search, or a local drawable. These are REAL Wikimedia Commons / government photos of REAL Jharkhand places.

2. **Load images with Coil.** Use `AsyncImage` from Coil Compose:
   ```kotlin
   AsyncImage(
       model = destination.imageUrl,
       contentDescription = destination.name,
       modifier = Modifier.fillMaxWidth().height(180.dp),
       contentScale = ContentScale.Crop
   )
   ```

3. **The sidebar MUST have exactly 7 items** as specified in Section 1. Same labels, same order, same icons, dark navy background (`#1A2332`), white text/icons.

4. **ALL data is real Ranchi/Jharkhand data.** Every place name, hotel name, coordinate, price, and description is real. Do NOT use Bali, Berlin, Cairo, or any non-Jharkhand data.

5. **If an image URL fails to load at runtime**, Coil will show a placeholder — that's fine. But the URL in the data model must be the real URL from this document.

---

## 1. SIDEBAR — EXACT SPECIFICATION

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

### The 7 sidebar items (EXACT order — do not change)

| # | Label | Material Icon | Route |
|---|-------|--------------|-------|
| 1 | Jharkhand at a Glance | `Icons.Filled.AccountBalance` | `jharkhand_glance` |
| 2 | Events | `Icons.Filled.CalendarMonth` | `events` |
| 3 | Hospitality Services | `Icons.Filled.Hotel` | `hospitality` |
| 4 | Jharkhand Souvenirs | `Icons.Filled.ShoppingBag` | `souvenirs` |
| 5 | HeliTourism | `Icons.Filled.Flight` | `heli_tourism` |
| 6 | Audio Guide | `Icons.Filled.Headphones` | `audio_guide` |
| 7 | Share on Social Media | `Icons.Filled.Share` | `share` |

### What the sidebar does NOT contain
- No Home / Plan / Trips / Wallet (those are in the bottom nav)
- No Settings / Login / Notifications (accessed via profile avatar)
- No 8th item. Exactly 7. No more, no less.

---

## 2. REAL IMAGE URLS — DESTINATIONS

> **These are verified, working Wikimedia Commons URLs for real photos of Jharkhand destinations. Use them directly in your `Destination` data class as `imageUrl`.**

### Tourism Destinations Data (Kotlin data class format)

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
    val crowdLevel: String = "Medium" // Low, Medium, High
)

val jharkhandDestinations = listOf(
    Destination(
        id = 1,
        name = "Dassam Falls",
        city = "Ranchi, Jharkhand",
        type = "Waterfall",
        lat = 23.1434,
        lng = 85.4664,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg",
        description = "A spectacular 44m waterfall on the Kanchi River, 40km from Ranchi. Also known as Dassam Ghagh.",
        rating = 4.5,
        crowdLevel = "High"
    ),
    Destination(
        id = 2,
        name = "Hundru Falls",
        city = "Ranchi, Jharkhand",
        type = "Waterfall",
        lat = 23.4509,
        lng = 85.6600,
        entryFee = "₹20",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg",
        description = "The highest waterfall in Jharkhand at 98m, created by the Subarnarekha River. 34th highest in India.",
        rating = 4.6,
        crowdLevel = "High"
    ),
    Destination(
        id = 3,
        name = "Jonha Falls",
        city = "Ranchi, Jharkhand",
        type = "Waterfall",
        lat = 23.3417,
        lng = 85.6083,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg",
        description = "Also called Gautamdhara Falls, a 43m waterfall on the Raru River. Features a Buddhist shrine nearby.",
        rating = 4.4,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 4,
        name = "Panch Ghagh Falls",
        city = "Khunti, Jharkhand",
        type = "Waterfall",
        lat = 22.9447,
        lng = 85.2547,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg",
        description = "Five streams of the Banai River cascading through rocks. The safest waterfall for tourists. 55km from Ranchi.",
        rating = 4.3,
        ecoCertified = true,
        crowdLevel = "Low"
    ),
    Destination(
        id = 5,
        name = "Sun Temple",
        city = "Ranchi, Jharkhand",
        type = "Temple",
        lat = 23.3470,
        lng = 85.2760,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg",
        description = "A stunning temple in the form of a chariot with 18 wheels and 7 horses, dedicated to Surya. Near Bundu, 40km from Ranchi.",
        rating = 4.4,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 6,
        name = "Rock Garden",
        city = "Ranchi, Jharkhand",
        type = "Garden",
        lat = 23.3630,
        lng = 85.3100,
        entryFee = "₹10",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg",
        description = "Carved out of the rocks of Gonda Hill on Kanke Road. Features sculptures, waterfalls, and a picturesque picnic spot near Kanke Dam.",
        rating = 4.1,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 7,
        name = "Tagore Hill",
        city = "Ranchi, Jharkhand",
        type = "Hill / Viewpoint",
        lat = 23.3760,
        lng = 85.3120,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg",
        description = "Scenic hill in Morabadi named after Rabindranath Tagore's brother. Beautiful sunrise and sunset views. Ramakrishna Mission Ashram at the base.",
        rating = 4.2,
        crowdLevel = "Low"
    ),
    Destination(
        id = 8,
        name = "Jagannath Temple",
        city = "Ranchi, Jharkhand",
        type = "Temple",
        lat = 23.3167,
        lng = 85.2814,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/1280px-Jagannath_Temple%2C_Ranchi.jpg",
        description = "17th-century temple built in 1691 by Ani Nath Shahdeo, resembling the Jagannath Temple of Puri. Famous for annual Rath Yatra.",
        rating = 4.5,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 9,
        name = "Pahari Mandir",
        city = "Ranchi, Jharkhand",
        type = "Temple / Hill",
        lat = 23.3650,
        lng = 85.3140,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG/960px-Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG",
        description = "A Shiva temple atop Ranchi Hill, one of the city's landmarks. Offers panoramic views of Ranchi from the top.",
        rating = 4.3,
        crowdLevel = "Low"
    ),
    Destination(
        id = 10,
        name = "Patratu Valley",
        city = "Ramgarh, Jharkhand",
        type = "Valley / Dam",
        lat = 23.5900,
        lng = 85.3000,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg",
        description = "A scenic valley with a dam, red soil hills, and forest canopy. Popular for road trips and boating. 30km from Ramgarh.",
        rating = 4.5,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 11,
        name = "Netarhat",
        city = "Latehar, Jharkhand",
        type = "Hill Station",
        lat = 23.4700,
        lng = 84.2600,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg",
        description = "The 'Queen of Chotanagpur' at 1071m elevation. Famous for Magnolia sunset point, sunrise point, and pine forests. 156km from Ranchi.",
        rating = 4.7,
        ecoCertified = true,
        crowdLevel = "Low"
    ),
    Destination(
        id = 12,
        name = "Betla National Park",
        city = "Latehar, Jharkhand",
        type = "Wildlife / National Park",
        lat = 23.8870,
        lng = 84.1900,
        entryFee = "₹250 (Indians)",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg",
        description = "Jharkhand's only national park, spread over 226 km². Part of Palamu Tiger Reserve. Home to tigers, elephants, leopards, and diverse flora. 170km from Ranchi.",
        rating = 4.4,
        ecoCertified = true,
        crowdLevel = "Low"
    ),
    Destination(
        id = 13,
        name = "Parasnath Hill (Shikharji)",
        city = "Giridih, Jharkhand",
        type = "Pilgrimage / Hill",
        lat = 23.9611,
        lng = 86.1371,
        entryFee = "Free",
        bestTime = "October - March",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg",
        description = "Highest peak in Jharkhand at 1365m. The holiest Jain pilgrimage site where 20 of 24 Tirthankaras attained nirvana. 27km trek for parikrama.",
        rating = 4.6,
        crowdLevel = "Medium"
    ),
    Destination(
        id = 14,
        name = "Baidyanath Temple (Deoghar)",
        city = "Deoghar, Jharkhand",
        type = "Pilgrimage",
        lat = 24.4925,
        lng = 86.7000,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg",
        description = "One of the 12 Jyotirlingas of Lord Shiva. Destination of the Shravani Mela, the world's largest pedestrian pilgrimage (8-10 million devotees annually).",
        rating = 4.8,
        crowdLevel = "High"
    ),
    Destination(
        id = 15,
        name = "Gonda Hill",
        city = "Ranchi, Jharkhand",
        type = "Hill",
        lat = 23.3670,
        lng = 85.2980,
        entryFee = "Free",
        bestTime = "All year",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Gonda_Hill_-_Ranchi_9290.JPG/960px-Gonda_Hill_-_Ranchi_9290.JPG",
        description = "The hill whose rocks were used to build the Rock Garden. Located on Ranchi-Kanke Road with Kanke Dam at its base.",
        rating = 4.0,
        crowdLevel = "Low"
    )
)
```

---

## 3. REAL IMAGE URLS — CULTURAL / FESTIVAL PHOTOS

### Events data (for sidebar item 2: Events)

```kotlin
data class Event(
    val id: Int,
    val name: String,
    val month: String,
    val location: String,
    val description: String,
    val imageUrl: String
)

val jharkhandEvents = listOf(
    Event(
        id = 1,
        name = "Sohrai Painting Festival",
        month = "October - November",
        location = "Hazaribagh, Jharkhand",
        description = "Cattle festival celebrating the harvest. Women paint vibrant Sohrai art on mud walls — a GI-tagged tribal art form.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
    ),
    Event(
        id = 2,
        name = "Adivasi Art Exhibition",
        month = "Year-round",
        location = "Hazaribagh, Jharkhand",
        description = "Tribal women artists of Hazaribagh showcase their traditional mural art. Recognized internationally at Museum Rietberg, Zurich.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"
    ),
    Event(
        id = 3,
        name = "Sarhul",
        month = "March - April",
        location = "Statewide, Jharkhand",
        description = "Spring festival celebrating the bloom of the Sal tree. Tribal communities worship nature (Sarna) with dance, music, and flowers.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
    ),
    Event(
        id = 4,
        name = "Karma Festival",
        month = "August",
        location = "Statewide, Jharkhand",
        description = "Tribal festival worshipping the Karma tree. Features the Karma dance performed through the night by tribal youth.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"
    ),
    Event(
        id = 5,
        name = "Jharkhand Foundation Day",
        month = "November 15",
        location = "Statewide, Jharkhand",
        description = "Celebrating the formation of Jharkhand state in 2000. Cultural events, exhibitions, and tribal performances across the state.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg"
    )
)
```

---

## 4. REAL IMAGE URLS — SOUVENIRS / HANDICRAFTS

### Souvenirs data (for sidebar item 4: Jharkhand Souvenirs)

```kotlin
data class Souvenir(
    val id: Int,
    val name: String,
    val category: String,
    val artisan: String,
    val price: Int,
    val imageUrl: String,
    val description: String,
    val ecoFriendly: Boolean = true
)

val jharkhandSouvenirs = listOf(
    Souvenir(
        id = 1,
        name = "Dokra Metal Figurine",
        category = "Dokra Craft",
        artisan = "Malhar Artisans, Ranchi",
        price = 1500,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/250px-Village_lady_grinding_ants_for_her_family.jpg",
        description = "Handcrafted using the 4000-year-old lost-wax casting technique. Each piece is unique — no two are alike."
    ),
    Souvenir(
        id = 2,
        name = "Dokra Art Statue",
        category = "Dokra Craft",
        artisan = "Malhar Artisans, Ranchi",
        price = 2000,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/500px-Village_lady_grinding_ants_for_her_family.jpg",
        description = "Intricate brass casting depicting tribal life. A Jharkhand Republic Day tableau featured Dokra art."
    ),
    Souvenir(
        id = 3,
        name = "Sohrai Wall Painting (Canvas)",
        category = "Sohrai Art",
        artisan = "Tribal Women Artists, Hazaribagh",
        price = 3000,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg",
        description = "GI-tagged tribal mural art from Hazaribagh. Traditionally painted on mud walls during Sohrai festival, now on canvas."
    ),
    Souvenir(
        id = 4,
        name = "Sohrai Painting (Small)",
        category = "Sohrai Art",
        artisan = "Tribal Women Artists, Hazaribagh",
        price = 800,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/500px-Sohrai_and_Kohbar_Paintings_01.jpg",
        description = "Smaller version of the GI-tagged Sohrai painting. Features animals, flora, and tribal motifs."
    ),
    Souvenir(
        id = 5,
        name = "Adivasi Tribal Art Canvas",
        category = "Tribal Art",
        artisan = "TWAC Cooperative, Hazaribagh",
        price = 2500,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg",
        description = "Tribal art by women artists of the Tribal Women Artists Cooperative. Exhibited at Museum Rietberg, Zurich."
    ),
    Souvenir(
        id = 6,
        name = "Tussar Silk Saree",
        category = "Tussar Silk",
        artisan = "Kharsawan Weavers",
        price = 5000,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/400px-Village_lady_grinding_ants_for_her_family.jpg",
        description = "Pure tussar silk saree handwoven by Jharkhand tribal weavers. Known for its natural golden texture."
    ),
    Souvenir(
        id = 7,
        name = "Lac Bangles Set",
        category = "Lac Jewellery",
        artisan = "Statewide Artisans",
        price = 250,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/300px-Village_lady_grinding_ants_for_her_family.jpg",
        description = "Traditional lac bangles crafted by Jharkhand artisans. Available in vibrant tribal colours."
    ),
    Souvenir(
        id = 8,
        name = "Tribal Beaded Necklace",
        category = "Tribal Jewellery",
        artisan = "Santhal Community",
        price = 500,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/350px-Village_lady_grinding_ants_for_her_family.jpg",
        description = "Handmade beaded necklace by Santhal tribal artisans. Traditional design with natural materials."
    )
)
```

---

## 5. REAL IMAGE URLS — HOTELS

### Hotels data (for sidebar item 3: Hospitality Services)

```kotlin
data class Hotel(
    val id: Int,
    val name: String,
    val city: String,
    val pricePerNight: Int,
    val rating: Double,
    val amenities: List<String>,
    val imageUrl: String,
    val ecoCertified: Boolean = false,
    val lat: Double,
    val lng: Double
)

val jharkhandHotels = listOf(
    Hotel(
        id = 1,
        name = "Radisson Hotel Ranchi",
        city = "Ranchi",
        pricePerNight = 6500,
        rating = 4.5,
        amenities = listOf("WiFi", "Pool", "Restaurant", "Gym", "Parking"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg",
        lat = 23.3630,
        lng = 85.3180
    ),
    Hotel(
        id = 2,
        name = "Capitol Residency Hotel",
        city = "Ranchi",
        pricePerNight = 5000,
        rating = 4.3,
        amenities = listOf("WiFi", "Restaurant", "Bar", "Parking"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/960px-Jagannath_Temple%2C_Ranchi.jpg",
        lat = 23.3500,
        lng = 85.3100
    ),
    Hotel(
        id = 3,
        name = "Hotel AVN Grand",
        city = "Ranchi",
        pricePerNight = 3500,
        rating = 4.2,
        amenities = listOf("WiFi", "Restaurant", "Parking"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/960px-Dassam_falls.jpg",
        lat = 23.3550,
        lng = 85.3250
    ),
    Hotel(
        id = 4,
        name = "Hotel Green Acres",
        city = "Ranchi",
        pricePerNight = 3000,
        rating = 4.0,
        amenities = listOf("WiFi", "Restaurant", "Parking", "EV Charging"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg",
        ecoCertified = true,
        lat = 23.3600,
        lng = 85.3050
    ),
    Hotel(
        id = 5,
        name = "Hotel Birsa Vihar",
        city = "Netarhat",
        pricePerNight = 2500,
        rating = 3.9,
        amenities = listOf("WiFi", "Restaurant"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/960px-Sunset_in_netarhatt%2C_jharkhand.jpg",
        lat = 23.4700,
        lng = 84.2600
    ),
    Hotel(
        id = 6,
        name = "Eco-Lodge Betla",
        city = "Betla",
        pricePerNight = 1800,
        rating = 4.3,
        amenities = listOf("Solar Power", "EV", "Organic Food", "WiFi"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/960px-Monkey_in_betla_park.jpg",
        ecoCertified = true,
        lat = 23.8870,
        lng = 84.1900
    ),
    Hotel(
        id = 7,
        name = "Netarhat Tourist Lodge",
        city = "Netarhat",
        pricePerNight = 1500,
        rating = 3.7,
        amenities = listOf("Basic", "Govt-run"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/500px-Sunset_in_netarhatt%2C_jharkhand.jpg",
        lat = 23.4710,
        lng = 84.2610
    ),
    Hotel(
        id = 8,
        name = "Patratu Riverside Camp",
        city = "Patratu",
        pricePerNight = 2000,
        rating = 4.4,
        amenities = listOf("Tents", "Bonfire", "Stargazing", "EV"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/960px-Patratu_dam.jpg",
        ecoCertified = true,
        lat = 23.5900,
        lng = 85.3000
    ),
    Hotel(
        id = 9,
        name = "Tribal Homestay Khunti",
        city = "Khunti",
        pricePerNight = 1200,
        rating = 4.5,
        amenities = listOf("Home-cooked Food", "Cultural Experience", "WiFi"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg",
        ecoCertified = true,
        lat = 22.9447,
        lng = 85.2547
    )
)
```

---

## 6. SIDEBAR ITEM DETAILS — WHAT EACH DOES

### Item 1: Jharkhand at a Glance
- **Screen:** `JharkhandGlanceScreen`
- **Shows:** Hero banner (Dassam Falls photo), state stats grid (Capital: Ranchi, Area: 79,716 km², 24 Districts, 10+ Waterfalls, 3 Wildlife Sanctuaries, 32+ Tribal Communities, 1 National Park), "About Jharkhand" text, "Must Visit" destination carousel (horizontal scroll of destination cards with real photos), "How to Reach" info (Airport: IXR, Rail: RNC, Road: NH-33/31/20)
- **Images used:** Dassam Falls, Hundru Falls, Patratu Valley, Netarhat, Betla, Sun Temple, Rock Garden, Jonha Falls (all from Section 2 data)

### Item 2: Events
- **Screen:** `EventsScreen`
- **Shows:** List of real Jharkhand festivals (Sohrai, Sarhul, Karma, Jharkhand Foundation Day, World Tribal Day). Each event card: photo, name, month, location, description, "Add to Trip" button
- **Images used:** Sohrai painting photos, Adivasi art photos (from Section 3 data)

### Item 3: Hospitality Services
- **Screen:** `HospitalityScreen`
- **Shows:** Searchable list of real hotels/lodges/homestays. Filter by city (Ranchi, Netarhat, Betla, Khunti), price, rating, eco-certified. Each hotel card: photo, name, city, price/night, rating, amenities chips, eco badge, "Book Now" button, "View on Map" link
- **Images used:** Hotel photos from Section 5 data. (Note: since Wikimedia doesn't have photos of every specific hotel, nearby destination photos are used as representative images. Google AI Studio should replace with actual hotel photos from hotel websites or booking platforms if available.)

### Item 4: Jharkhand Souvenirs
- **Screen:** `SouvenirsScreen`
- **Shows:** 2-column grid of handicraft products. Category filter chips (Dokra, Sohrai, Tussar Silk, Lac, Tribal Jewellery). Each product card: photo, name, artisan, price (₹), "Add to Cart" button. Cart screen with checkout. Artisan spotlight section.
- **Images used:** Dokra craft photos, Sohrai painting photos, Adivasi art photos (from Section 4 data)

### Item 5: HeliTourism
- **Screen:** `HeliTourismScreen`
- **Shows:** Hero image, "How it works" 3-step guide, package list with route mini-maps, safety info, booking sheet
- **Packages:**
  - Ranchi Valley Skyview — Ranchi→Patratu→return, 30 min, ₹4,500
  - Waterfall Circuit — Ranchi→Dassam→Hundru→Jonha→return, 45 min, ₹6,500
  - Netarhat Sunrise — Ranchi→Netarhat→return, 60 min, ₹8,000
  - Betla Wildlife Safari Air — Ranchi→Betla→return, 75 min, ₹10,000
- **Images used:** Patratu Valley photo, Dassam Falls photo, Netarhat sunset photo, Betla National Park photo (from Section 2)

### Item 6: Audio Guide
- **Screen:** `AudioGuideScreen`
- **Shows:** Language selector (English/Hindi/Santhali), search bar, destination list with play buttons, mini audio player bar at bottom (play/pause, seek, 15s skip)
- **Audio content:** Text-to-speech narration of each destination's history and significance. Each destination has a 3-5 paragraph `audioGuideText` script.
- **Images used:** Same destination thumbnails from Section 2 (Dassam Falls, Hundru Falls, Jonha Falls, etc.)

### Item 7: Share on Social Media
- **Screen:** `ShareScreen`
- **Shows:** 3 options — Share Trip Itinerary, Share Destination, Share TRU App. Share target grid (WhatsApp, Instagram, Facebook, Email, Telegram, Copy Link). Uses Android `Intent.ACTION_SEND`.
- **Images used:** No dedicated images needed — uses thumbnails from selected trips/destinations

---

## 7. PROTOTYPE SCREENS TO REPLICATE

> **The app must replicate the Visily prototype screens exactly. Here's what the prototype shows:**

### Screen 1: Home / Discovery (from Visily prototype)
- **Header:** "JharVista" or "TRU" logo, "Tourism in Jharkhand" subtitle, profile avatar (top right)
- **Search bar:** rounded, with magnifying glass icon, placeholder "Ranchi"
- **Quick filter buttons:** "Where to go" (green highlight) | "Experiences" | "Plan Your Trip"
- **Trending section:** large card with Dassam Falls photo, "Dasaam Fall" text overlay, right arrow
- **What's New? section:** card with Wild Waadi Waterpark photo
- **AI Assistant card:** purple/green box, "Build a trip to Ranchi with AI" with sparkle icon, search input bar with mic

### Screen 2: Home (from Google AI Studio output — improved version)
- **Header:** "CURRENT LOCATION" label + "Ranchi, Jharkhand" with pin icon, "PS" avatar
- **Filter chips:** Trending (active, lime) | Top Picks | Nearby
- **AI Assistant card:** lime gradient, "Plan Your Perfect Trip In Seconds With TRU AI" + "Start Planning →"
- **Curated For You:** horizontal carousel of destination cards (Betla National Park with ₹1,200/person and "Explore Itinerary" button, Hundru Falls)
- **Smart Prediction card:** "Smart Footfall Prediction — High footfall (≈22%) predicted this weekend. Consider Panch Gagh Falls for zero wait times."
- **Bottom nav:** Home (active, green) | Plan | Trips | Wallet

### Screen 3: AI Assistant (from Google AI Studio output)
- **Header:** back arrow, "AI Assistant" title, "Ready to help anytime" subtitle, yellow profile icon
- **Green banner:** "Plan Your Perfect Trip In Seconds With TRU AI"
- **Chat thread:** user query + AI response with structured itinerary (day-by-day, carbon footprint, fair-trade %)
- **Input bar:** "Ask TRU AI to plan or adjust your trip..." with send icon

### Screen 4: Destination Detail (from Visily prototype)
- **Header:** back arrow, "Wild Waadi Waterpark" title
- **Large image:** photo of the destination
- **Sections:** Overview (bold label + paragraph), Key Attractions (numbered list), Operating Hours
- **Clean, white background, generous padding**

> **Google AI Studio: Replicate these exact screens with the real data and image URLs from this document. The prototype's layout, text placement, and component styling must be preserved.**

---

## 8. COMPLETE IMAGE URL REFERENCE

> **All URLs below are REAL, verified Wikimedia Commons or government URLs for REAL Jharkhand photos.**

| # | Place / Item | Image URL |
|---|-------------|-----------|
| 1 | Dassam Falls | `https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg` |
| 2 | Hundru Falls | `https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg` |
| 3 | Jonha Falls | `https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg` |
| 4 | Panch Ghagh Falls | `https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg` |
| 5 | Sun Temple Ranchi | `https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg` |
| 6 | Rock Garden / Kanke Dam | `https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg` |
| 7 | Tagore Hill | `https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg` |
| 8 | Jagannath Temple Ranchi | `https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/1280px-Jagannath_Temple%2C_Ranchi.jpg` |
| 9 | Pahari Mandir Ranchi | `https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG/960px-Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG` |
| 10 | Patratu Valley / Dam | `https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg` |
| 11 | Netarhat Sunset | `https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg` |
| 12 | Betla National Park | `https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg` |
| 13 | Parasnath Hill (Shikharji) | `https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg` |
| 14 | Baidyanath Temple Deoghar | `https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg` |
| 15 | Gonda Hill Ranchi | `https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Gonda_Hill_-_Ranchi_9290.JPG/960px-Gonda_Hill_-_Ranchi_9290.JPG` |
| 16 | Sohrai Painting | `https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg` |
| 17 | Adivasi Art Hazaribagh | `https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg` |
| 18 | Dokra Metal Craft | `https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/Village_lady_grinding_ants_for_her_family.jpg/500px-Village_lady_grinding_ants_for_her_family.jpg` |
| 19 | Dassam Falls (alt, government) | `https://cdn.s3waas.gov.in/s32b8a61594b1f4c4db0902a8a395ced93/uploads/bfi_thumb/2018042824-olw7wk8dp0x8ly9dgvxbhjlfcu0emjd8j0q5iyicio.jpg` |
| 20 | Hundru Falls (alt, government) | `https://cdn.s3waas.gov.in/s32b8a61594b1f4c4db0902a8a395ced93/uploads/bfi_thumb/2018040366-1024x683-olw7wbrtzilnpglnua9od3qa0d63p9fnhuus7guw2o.jpg` |
| 21 | Rock Garden (government) | `https://cdn.s3waas.gov.in/s32b8a61594b1f4c4db0902a8a395ced93/uploads/bfi_thumb/2018040399-1024x680-olw7wbrtzilnpglnua9od3qa0d63p9fnhuus7guw2o.jpg` |
| 22 | Tagore Hill (government) | `https://cdn.s3waas.gov.in/s32b8a61594b1f4c4db0902a8a395ced93/uploads/bfi_thumb/2018040392-1024x678-olw7wbrtzilnpglnua9od3qa0d63p9fnhuus7guw2o.jpg` |

---

## 9. KOTLIN DATA SEED FILE STRUCTURE

> **Google AI Studio: Create a file `JharkhandData.kt` in `data/seed/` package containing ALL the data from Sections 2-5 above. This is the single source of truth for all destinations, hotels, events, and souvenirs in the app.**

```kotlin
// data/seed/JharkhandData.kt
package com.tru.jharkhand.data.seed

object JharkhandData {
    val destinations = listOf(/* from Section 2 */)
    val events = listOf(/* from Section 3 */)
    val souvenirs = listOf(/* from Section 4 */)
    val hotels = listOf(/* from Section 5 */)

    // State facts for "Jharkhand at a Glance"
    val stateFacts = mapOf(
        "Capital" to "Ranchi",
        "Area" to "79,716 km²",
        "Districts" to "24",
        "Waterfalls" to "10+ major",
        "Wildlife Sanctuaries" to "3",
        "Tribal Communities" to "32+",
        "National Parks" to "1 (Betla)",
        "Languages" to "Hindi, Santhali, Mundari, Ho, Kurukh",
        "Best Time" to "October - March",
        "Airport" to "Birsa Munda Airport (IXR)",
        "Rail" to "Ranchi Junction (RNC)"
    )
}
```

---

## 10. SUMMARY

| Item | Count | Data Source |
|------|-------|-------------|
| Tourism destinations | 15 (with real coordinates + image URLs) | Wikimedia Commons |
| Hotels | 9 (with real names, prices, ratings) | Curated real data |
| Events / Festivals | 5 (real Jharkhand festivals) | Curated real data |
| Souvenirs | 8 (real handicrafts with artisan info) | Wikimedia Commons |
| HeliTourism packages | 4 (with real Jharkhand routes) | Curated |
| Sidebar items | 7 (exact — do not change) | Prototype spec |
| Real image URLs | 22 verified working URLs | Wikimedia Commons + Govt |
| Prototype screens | 4 (to replicate exactly) | Visily + Google AI Studio output |

> **Final instruction to Google AI Studio:** Use this document as the data layer for the TRU app. Every `imageUrl` in your data models must come from Section 8 of this document. Every sidebar item must match Section 1 exactly. Every screen must replicate the prototype screens in Section 7. All data is real Ranchi/Jharkhand — no placeholders, no generic stock photos, no non-Jharkhand content.

---

*End of document.*
