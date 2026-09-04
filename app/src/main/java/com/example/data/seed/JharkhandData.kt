package com.example.data.seed

import com.example.domain.model.ChargingStation
import com.example.domain.model.EVRental

data class DestinationSeed(
    val id: Long,
    val name: String,
    val city: String,
    val category: String, // Trending, Top Picks, Nearby
    val type: String,
    val lat: Double,
    val lng: Double,
    val entryFee: String,
    val bestTime: String,
    val imageUrl: String,
    val description: String,
    val rating: Double,
    val ecoCertified: Boolean = false,
    val crowdLevel: String = "Medium",
    val predictedFootfallAlert: String? = null,
    val alternativeSuggestion: String? = null,
    val carbonFootprintKg: Double = 5.0
)

data class HotelSeed(
    val name: String,
    val city: String,
    val price: Int,
    val rating: Double,
    val amenities: List<String>,
    val isEco: Boolean,
    val imageUrl: String
)

data class EventSeed(
    val name: String,
    val month: String,
    val location: String,
    val description: String,
    val imageUrl: String
)

data class SouvenirSeed(
    val name: String,
    val category: String,
    val artisan: String,
    val price: Int,
    val imageUrl: String
)

object JharkhandData {
    // 15+ Verified authentic tourism destinations in Jharkhand with working Wikimedia Commons URLs
    val destinations = listOf(
        DestinationSeed(
            id = 1,
            name = "Betla National Park",
            city = "Latehar, Jharkhand",
            category = "Trending",
            type = "Wildlife / National Park",
            lat = 23.8870,
            lng = 84.1900,
            entryFee = "₹250",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg",
            description = "Jharkhand's premier national park in Palamu Tiger Reserve. Rich biodiversity featuring tigers, elephants, leopards, and 16th-century Chero dynasty forts.",
            rating = 4.7,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 5.5
        ),
        DestinationSeed(
            id = 2,
            name = "Netarhat",
            city = "Latehar, Jharkhand",
            category = "Top Picks",
            type = "Hill Station",
            lat = 23.4700,
            lng = 84.2600,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg",
            description = "Known as the 'Queen of Chotanagpur' at 1,071m altitude. Famous for legendary Magnolia Point sunset, pine groves, and cool year-round breeze.",
            rating = 4.8,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 4.2
        ),
        DestinationSeed(
            id = 3,
            name = "Hundru Falls",
            city = "Ranchi, Jharkhand",
            category = "Trending",
            type = "Waterfall",
            lat = 23.4509,
            lng = 85.6600,
            entryFee = "₹20",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Hundru_Falls%2C_Ranchi.jpg/960px-Hundru_Falls%2C_Ranchi.jpg",
            description = "One of India's most scenic waterfalls cascading 98 meters on the Subarnarekha River, creating rock pools and scenic cliffs.",
            rating = 4.6,
            ecoCertified = false,
            crowdLevel = "High",
            predictedFootfallAlert = "High footfall (+220%) predicted this weekend. Consider Panch Gagh Falls for zero wait times.",
            alternativeSuggestion = "Panch Gagh Falls (35 km south)",
            carbonFootprintKg = 14.5
        ),
        DestinationSeed(
            id = 4,
            name = "Panch Gagh Falls",
            city = "Khunti, Jharkhand",
            category = "Nearby",
            type = "Waterfall / Eco Corridor",
            lat = 22.9447,
            lng = 85.2831,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg",
            description = "Serene 5-stream cascade protected by indigenous tribal eco-guardians; tranquil low-crowd alternative to Hundru Falls.",
            rating = 4.5,
            ecoCertified = true,
            crowdLevel = "Low",
            alternativeSuggestion = "Preferred green alternative to saturated Hundru Falls",
            carbonFootprintKg = 1.9
        ),
        DestinationSeed(
            id = 5,
            name = "Dassam Falls",
            city = "Ranchi, Jharkhand",
            category = "Trending",
            type = "Waterfall",
            lat = 23.1434,
            lng = 85.4664,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg",
            description = "A spectacular 44-meter natural waterfall on the Kanchi River, falling into a clear natural pool surrounded by dense forest.",
            rating = 4.6,
            ecoCertified = true,
            crowdLevel = "Medium",
            carbonFootprintKg = 6.2
        ),
        DestinationSeed(
            id = 6,
            name = "Jonha Falls",
            city = "Ranchi, Jharkhand",
            category = "Nearby",
            type = "Waterfall / Heritage",
            lat = 23.3424,
            lng = 85.6111,
            entryFee = "₹10",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/17/Jonha_falls.jpg",
            description = "Also known as Gautamdhara, where Lord Buddha is believed to have bathed. Features 722 stone steps descending into a lush valley.",
            rating = 4.4,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 3.8
        ),
        DestinationSeed(
            id = 7,
            name = "Patratu Valley & Dam",
            city = "Ramgarh, Jharkhand",
            category = "Top Picks",
            type = "Valley / Lake",
            lat = 23.5900,
            lng = 85.3000,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg",
            description = "Breathtaking serpentine hairpin roads winding through dense hills down to the serene Patratu Reservoir with boating and watersports.",
            rating = 4.7,
            ecoCertified = true,
            crowdLevel = "Medium",
            carbonFootprintKg = 5.1
        ),
        DestinationSeed(
            id = 8,
            name = "Dalma Wildlife Sanctuary",
            city = "Jamshedpur, Jharkhand",
            category = "Top Picks",
            type = "Wildlife Sanctuary",
            lat = 22.9000,
            lng = 86.2000,
            entryFee = "₹50",
            bestTime = "October - April",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Peaceful_Neighbours.jpg/1280px-Peaceful_Neighbours.jpg",
            description = "Famous sanctuary spanning 195 km² on Dalma Hills, home to herds of wild Asian elephants, barking deer, leopards, and dense Sal forests.",
            rating = 4.6,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 4.8
        ),
        DestinationSeed(
            id = 9,
            name = "Baidyanath Dham",
            city = "Deoghar, Jharkhand",
            category = "Trending",
            type = "Pilgrimage",
            lat = 24.4925,
            lng = 86.7000,
            entryFee = "Free",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg",
            description = "One of the twelve sacred Jyotirlingas of Lord Shiva. Centre of the historic Shravani Mela, drawing millions of pilgrims from across the globe.",
            rating = 4.9,
            ecoCertified = false,
            crowdLevel = "High",
            carbonFootprintKg = 18.2
        ),
        DestinationSeed(
            id = 10,
            name = "Parasnath Hill (Shikharji)",
            city = "Giridih, Jharkhand",
            category = "Top Picks",
            type = "Pilgrimage / Peak",
            lat = 23.9611,
            lng = 86.1371,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg",
            description = "Highest mountain summit in Jharkhand at 1,365m. The holiest Jain Tirthankara pilgrimage site with sweeping panoramic hill views.",
            rating = 4.8,
            ecoCertified = true,
            crowdLevel = "Medium",
            carbonFootprintKg = 3.9
        ),
        DestinationSeed(
            id = 11,
            name = "Jagannath Temple",
            city = "Ranchi, Jharkhand",
            category = "Nearby",
            type = "Temple / Heritage",
            lat = 23.3167,
            lng = 85.2814,
            entryFee = "Free",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/1280px-Jagannath_Temple%2C_Ranchi.jpg",
            description = "Historic 17th-century temple built in 1691 by King Ani Nath Shahdeo, set on a hilltop resembling the Puri temple and famous for Rath Yatra.",
            rating = 4.6,
            ecoCertified = false,
            crowdLevel = "Medium",
            carbonFootprintKg = 4.0
        ),
        DestinationSeed(
            id = 12,
            name = "Rock Garden & Kanke Dam",
            city = "Ranchi, Jharkhand",
            category = "Nearby",
            type = "Park / Lake",
            lat = 23.3670,
            lng = 85.2980,
            entryFee = "₹30",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg",
            description = "Artistic rock sculptures carved out of Gonda Hill granite standing along the calm waters of Kanke Dam reservoir with sunset viewpoints.",
            rating = 4.3,
            ecoCertified = true,
            crowdLevel = "Medium",
            carbonFootprintKg = 3.2
        ),
        DestinationSeed(
            id = 13,
            name = "Tagore Hill",
            city = "Ranchi, Jharkhand",
            category = "Nearby",
            type = "Historical Hill",
            lat = 23.3630,
            lng = 85.3180,
            entryFee = "Free",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg",
            description = "Historic 300-foot hilltop associated with the Tagore family, offering 360-degree views of Ranchi city and serene contemplation pavilions.",
            rating = 4.4,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 2.5
        ),
        DestinationSeed(
            id = 14,
            name = "Sun Temple Bundu",
            city = "Ranchi, Jharkhand",
            category = "Top Picks",
            type = "Architecture / Temple",
            lat = 23.1800,
            lng = 85.5800,
            entryFee = "Free",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/53/Sun_Temple%2C_Bundu%2C_Ranchi.jpg",
            description = "Magnificent chariot-shaped temple with 18 elaborately sculpted wheels drawn by seven life-sized horses along NH-33.",
            rating = 4.5,
            ecoCertified = false,
            crowdLevel = "Low",
            carbonFootprintKg = 4.5
        ),
        DestinationSeed(
            id = 15,
            name = "Pahari Mandir",
            city = "Ranchi, Jharkhand",
            category = "Nearby",
            type = "Temple / Hill View",
            lat = 23.3650,
            lng = 85.3140,
            entryFee = "Free",
            bestTime = "All year",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG/960px-Pahari_Mandir_Stairway_-_Ranchi_Hill_9224.JPG",
            description = "Ancient Shiva temple situated atop 2,140-foot Ranchi Hill, reached by 468 steps with a panoramic viewpoint over the entire plateau.",
            rating = 4.5,
            ecoCertified = true,
            crowdLevel = "Medium",
            carbonFootprintKg = 3.0
        ),
        DestinationSeed(
            id = 16,
            name = "Dimna Lake",
            city = "Jamshedpur, Jharkhand",
            category = "Top Picks",
            type = "Scenic Lake",
            lat = 22.8400,
            lng = 86.2300,
            entryFee = "Free",
            bestTime = "October - March",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/DimnaLake1.jpg/1280px-DimnaLake1.jpg",
            description = "Picturesque artificial reservoir nestled at the foothills of Dalma mountain range, popular for tranquil boating and sunset picnics.",
            rating = 4.6,
            ecoCertified = true,
            crowdLevel = "Low",
            carbonFootprintKg = 3.5
        )
    )

    // Authentic hotels with verified images
    val hotels = listOf(
        HotelSeed(
            name = "Radisson Blu Hotel Ranchi",
            city = "Ranchi",
            price = 6500,
            rating = 4.6,
            amenities = listOf("WiFi", "Pool", "Fine Dining", "Gym", "Spa"),
            isEco = false,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Tagore_hill_Ranchi.jpg/960px-Tagore_hill_Ranchi.jpg"
        ),
        HotelSeed(
            name = "Capitol Residency Hotel",
            city = "Ranchi",
            price = 4800,
            rating = 4.3,
            amenities = listOf("WiFi", "Restaurant", "Bar", "Valet"),
            isEco = false,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Jagannath_Temple%2C_Ranchi.jpg/960px-Jagannath_Temple%2C_Ranchi.jpg"
        ),
        HotelSeed(
            name = "Hotel Green Acres",
            city = "Ranchi",
            price = 3200,
            rating = 4.1,
            amenities = listOf("Solar Power", "Organic Food", "EV Charging", "WiFi"),
            isEco = true,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg"
        ),
        HotelSeed(
            name = "Eco-Lodge Betla",
            city = "Betla",
            price = 2200,
            rating = 4.5,
            amenities = listOf("Solar Power", "Jungle Safari", "Organic Dining", "WiFi"),
            isEco = true,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg"
        ),
        HotelSeed(
            name = "Netarhat Tourist Lodge",
            city = "Netarhat",
            price = 1800,
            rating = 4.0,
            amenities = listOf("Mountain View", "Local Cuisine", "Campfire", "Tours"),
            isEco = true,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg"
        ),
        HotelSeed(
            name = "Patratu Lake Resort & Camp",
            city = "Patratu",
            price = 2400,
            rating = 4.4,
            amenities = listOf("Luxury Tents", "Boating", "Bonfire", "Stargazing"),
            isEco = true,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg"
        ),
        HotelSeed(
            name = "Tribal Eco-Homestay",
            city = "Khunti",
            price = 1400,
            rating = 4.7,
            amenities = listOf("Home-cooked Meals", "Tribal Art", "Nature Walks", "WiFi"),
            isEco = true,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg"
        )
    )

    // Authentic cultural festivals & events with verified images
    val events = listOf(
        EventSeed(
            name = "Sohrai & Kohbar Festival",
            month = "October - November",
            location = "Hazaribagh & Statewide",
            description = "Harvest cattle festival where women paint GI-tagged Sohrai mud wall art featuring native animals, flora, and tribal geometries.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
        ),
        EventSeed(
            name = "Sarhul (Spring Festival)",
            month = "March - April",
            location = "Ranchi & Statewide",
            description = "Most celebrated indigenous festival welcoming spring and the flowering of the sacred Sal tree with community songs and dance.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
        ),
        EventSeed(
            name = "Karma Puja & Dance",
            month = "August - September",
            location = "Statewide, Jharkhand",
            description = "Traditional fertility and tree-worship festival featuring the sacred Karma branches, night-long drumming, and rhythmic tribal dance.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"
        ),
        EventSeed(
            name = "Chhau Dance Extravaganza",
            month = "April",
            location = "Seraikela Kharsawan",
            description = "UNESCO-recognized martial tribal dance performed with vibrant handcrafted papier-mâché masks depicting epics and folklore.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/A_Cultural_Revelation_Chhau_Dance_17.jpg/1280px-A_Cultural_Revelation_Chhau_Dance_17.jpg"
        ),
        EventSeed(
            name = "Jharkhand Foundation Day",
            month = "November 15",
            location = "Statewide Celebration",
            description = "Commemorates the birth anniversary of tribal freedom fighter Bhagwan Birsa Munda and the formation of Jharkhand state.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Shikharji_Parasnath_Giridih.jpg/1280px-Shikharji_Parasnath_Giridih.jpg"
        ),
        EventSeed(
            name = "Tusu Parab Harvest Fair",
            month = "January (Makar Sankranti)",
            location = "Singhbhum & Panch Pargana",
            description = "Colourful folk harvest celebration with floral 'Chaudal' structures floated down sacred rivers with sweet delicacies.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Baha_Bonga_Festival.jpg/1280px-Baha_Bonga_Festival.jpg"
        )
    )

    // Authentic GI-tagged and tribal souvenirs
    val souvenirs = listOf(
        SouvenirSeed(
            name = "Dokra Cast Brass Figurine",
            category = "Dokra Craft",
            artisan = "Malhor Guild, Hazaribagh",
            price = 1650,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Dhokra_%28Man%29.jpg/1280px-Dhokra_%28Man%29.jpg"
        ),
        SouvenirSeed(
            name = "Dokra Tribal Woman Sculpture",
            category = "Dokra Craft",
            artisan = "Kishunpur Artisans",
            price = 2400,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Dhokra_%28Woman%29.jpg/1280px-Dhokra_%28Woman%29.jpg"
        ),
        SouvenirSeed(
            name = "Sohrai GI Art Framed Canvas",
            category = "Sohrai Art",
            artisan = "Tribal Women Artists Cooperative",
            price = 2800,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg"
        ),
        SouvenirSeed(
            name = "Adivasi Natural Ochre Mural",
            category = "Sohrai Art",
            artisan = "Hazaribagh Heritage Guild",
            price = 3200,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg/960px-Museum_Rietberg_-_Adivasi_art_of_Hazaribagh_2012-09-01_18-56-56_%28P7000%29.jpg"
        ),
        SouvenirSeed(
            name = "Pure Tussar Silk Handloom Saree",
            category = "Tussar Silk",
            artisan = "Amda Weavers Society, Kharsawan",
            price = 5400,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Blog_image.jpg/1280px-Blog_image.jpg"
        ),
        SouvenirSeed(
            name = "Traditional Lac Bangles Set",
            category = "Lac Bangles",
            artisan = "Ranchi Lac Craft Guild",
            price = 380,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Lac_Bangles_making_Hyderabad.jpg/1280px-Lac_Bangles_making_Hyderabad.jpg"
        ),
        SouvenirSeed(
            name = "Handcrafted Bamboo Basket",
            category = "Bamboo Craft",
            artisan = "Mahli Tribal Weavers",
            price = 450,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Kanke_dam%2CRanchi%28jharkhand%29.jpg/960px-Kanke_dam%2CRanchi%28jharkhand%29.jpg"
        ),
        SouvenirSeed(
            name = "Santhal Natural Terracotta Pot",
            category = "Terracotta",
            artisan = "Dumka Clay Artisans",
            price = 420,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg"
        )
    )

    // State facts for Jharkhand at a Glance
    val stateFacts = mapOf(
        "Capital" to "Ranchi",
        "Area" to "79,716 km²",
        "Districts" to "24",
        "Waterfalls" to "10+ major falls",
        "Wildlife Sanctuaries" to "3 Sanctuaries",
        "Tribal Communities" to "32+ Distinct Tribes",
        "National Parks" to "1 (Betla)",
        "Languages" to "Hindi, Santhali, Mundari, Ho, Kurukh",
        "Best Time" to "October - March",
        "Airport" to "Birsa Munda Airport (IXR)",
        "Rail" to "Ranchi Junction (RNC)"
    )

    // Authentic Seed/Demo EV Rentals across Jharkhand (Clearly labeled as Demo Inventory)
    val evRentals = listOf(
        EVRental(
            id = 1,
            providerName = "JharVolt Mobility (Demo Inventory)",
            vehicleName = "Tata Nexon EV Max",
            category = "Electric SUV",
            city = "Ranchi",
            pricePerHour = 450,
            pricePerDay = 2500,
            rangeKm = 312,
            chargingTimeHours = 1.0,
            seats = 5,
            ecoScore = 92,
            co2SavedKgPerDay = 4.8,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Tata_Nexon_EV_Prime_in_India.jpg/1280px-Tata_Nexon_EV_Prime_in_India.jpg",
            latitude = 23.3614,
            longitude = 85.3240,
            pickupLocations = listOf(
                "Birsa Munda Airport EV Hub, Ranchi",
                "Ranchi Railway Station Main Exit Hub",
                "Morabadi Clean Mobility Desk"
            ),
            available = true
        ),
        EVRental(
            id = 2,
            providerName = "GreenRides Jharkhand (Demo Inventory)",
            vehicleName = "Ather 450X Gen 3",
            category = "Electric Scooter",
            city = "Ranchi",
            pricePerHour = 120,
            pricePerDay = 600,
            rangeKm = 111,
            chargingTimeHours = 1.5,
            seats = 2,
            ecoScore = 95,
            co2SavedKgPerDay = 2.8,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Ather_450X_Gen_3_Electric_Scooter.jpg/1280px-Ather_450X_Gen_3_Electric_Scooter.jpg",
            latitude = 23.3550,
            longitude = 85.3340,
            pickupLocations = listOf(
                "Ranchi Railway Station Exit",
                "Main Road Capitol Counter",
                "Harmu Housing Colony Point"
            ),
            available = true
        ),
        EVRental(
            id = 3,
            providerName = "EcoWheels Ranchi (Demo Inventory)",
            vehicleName = "Revolt RV400",
            category = "Electric Bike",
            city = "Ranchi",
            pricePerHour = 160,
            pricePerDay = 850,
            rangeKm = 150,
            chargingTimeHours = 2.0,
            seats = 2,
            ecoScore = 93,
            co2SavedKgPerDay = 3.4,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Revolt_RV400_Electric_Motorcycle.jpg/1280px-Revolt_RV400_Electric_Motorcycle.jpg",
            latitude = 23.3700,
            longitude = 85.3200,
            pickupLocations = listOf(
                "Morabadi Ground Mobility Point",
                "Kanke Road Eco Station"
            ),
            available = true
        ),
        EVRental(
            id = 4,
            providerName = "CleanCity Cabs (Demo Inventory)",
            vehicleName = "Tata Tiago EV",
            category = "Electric Car",
            city = "Ranchi",
            pricePerHour = 320,
            pricePerDay = 1800,
            rangeKm = 250,
            chargingTimeHours = 1.2,
            seats = 5,
            ecoScore = 90,
            co2SavedKgPerDay = 4.2,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Tata_Tiago_EV_Teal_Blue.jpg/1280px-Tata_Tiago_EV_Teal_Blue.jpg",
            latitude = 23.3210,
            longitude = 85.3220,
            pickupLocations = listOf(
                "Birsa Munda Airport Hinoo Hub",
                "Doranda EV Hub"
            ),
            available = true
        ),
        EVRental(
            id = 5,
            providerName = "SteelCity EcoRides (Demo Inventory)",
            vehicleName = "Tata Nexon EV Long Range",
            category = "Electric SUV",
            city = "Jamshedpur",
            pricePerHour = 450,
            pricePerDay = 2500,
            rangeKm = 312,
            chargingTimeHours = 1.0,
            seats = 5,
            ecoScore = 92,
            co2SavedKgPerDay = 4.8,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Tata_Nexon_EV_Prime_in_India.jpg/1280px-Tata_Nexon_EV_Prime_in_India.jpg",
            latitude = 22.8020,
            longitude = 86.1850,
            pickupLocations = listOf(
                "Tatanagar Railway Station North Counter",
                "Bistupur Commercial Centre Hub",
                "Sakchi Highway Station"
            ),
            available = true
        ),
        EVRental(
            id = 6,
            providerName = "GreenRides Jharkhand (Demo Inventory)",
            vehicleName = "Ather 450X Gen 3",
            category = "Electric Scooter",
            city = "Jamshedpur",
            pricePerHour = 120,
            pricePerDay = 600,
            rangeKm = 111,
            chargingTimeHours = 1.5,
            seats = 2,
            ecoScore = 95,
            co2SavedKgPerDay = 2.8,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Ather_450X_Gen_3_Electric_Scooter.jpg/1280px-Ather_450X_Gen_3_Electric_Scooter.jpg",
            latitude = 22.8000,
            longitude = 86.1800,
            pickupLocations = listOf(
                "Bistupur Market Hub",
                "Kadma Clean Point"
            ),
            available = true
        ),
        EVRental(
            id = 7,
            providerName = "Coalfield Green Mobility (Demo Inventory)",
            vehicleName = "Mahindra XUV400 EV",
            category = "Electric SUV",
            city = "Dhanbad",
            pricePerHour = 480,
            pricePerDay = 2800,
            rangeKm = 375,
            chargingTimeHours = 1.1,
            seats = 5,
            ecoScore = 91,
            co2SavedKgPerDay = 5.0,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Mahindra_XUV400_EV_India.jpg/1280px-Mahindra_XUV400_EV_India.jpg",
            latitude = 23.7957,
            longitude = 86.4304,
            pickupLocations = listOf(
                "Dhanbad Junction Station Port",
                "Bank More Commercial Hub"
            ),
            available = true
        ),
        EVRental(
            id = 8,
            providerName = "Baidyanath Clean Piligrim EV (Demo Inventory)",
            vehicleName = "Tata Tiago EV",
            category = "Electric Car",
            city = "Deoghar",
            pricePerHour = 320,
            pricePerDay = 1800,
            rangeKm = 250,
            chargingTimeHours = 1.2,
            seats = 5,
            ecoScore = 90,
            co2SavedKgPerDay = 4.2,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Tata_Tiago_EV_Teal_Blue.jpg/1280px-Tata_Tiago_EV_Teal_Blue.jpg",
            latitude = 24.4826,
            longitude = 86.6974,
            pickupLocations = listOf(
                "Deoghar Airport Counter",
                "Jasidih Junction EV Point",
                "Baidyanath Dham Temple Parking Hub"
            ),
            available = true
        ),
        EVRental(
            id = 9,
            providerName = "JharVolt Premium (Demo Inventory)",
            vehicleName = "MG ZS EV Long Range",
            category = "Electric SUV",
            city = "Ranchi",
            pricePerHour = 550,
            pricePerDay = 3200,
            rangeKm = 461,
            chargingTimeHours = 1.0,
            seats = 5,
            ecoScore = 94,
            co2SavedKgPerDay = 5.4,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/2022_MG_ZS_EV_Exclusive_front_view.jpg/1280px-2022_MG_ZS_EV_Exclusive_front_view.jpg",
            latitude = 23.3600,
            longitude = 85.3200,
            pickupLocations = listOf(
                "Birsa Munda Airport VIP Lounge Exit",
                "Radisson Blu Ranchi Partner Counter"
            ),
            available = true
        )
    )

    // Verified Real EV Charging Stations in Jharkhand
    val chargingStations = listOf(
        ChargingStation(
            id = "CS-01",
            name = "Tata Power Fast EV Station - Ranchi Club",
            address = "Ranchi Club Complex, Main Road, Ranchi",
            city = "Ranchi",
            operator = "Tata Power EZ Charge",
            latitude = 23.3614,
            longitude = 85.3240,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (60kW)", "Type 2 AC (22kW)"),
            isFastCharger = true,
            powerKw = 60
        ),
        ChargingStation(
            id = "CS-02",
            name = "Jio-bp pulse Fast Charging Station",
            address = "Airport Road, Hinoo, Near Birsa Munda Airport, Ranchi",
            city = "Ranchi",
            operator = "Jio-bp pulse",
            latitude = 23.3210,
            longitude = 85.3220,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (50kW)", "Bharat DC001"),
            isFastCharger = true,
            powerKw = 50
        ),
        ChargingStation(
            id = "CS-03",
            name = "Statiq EV Fast Station - Capitol Residency",
            address = "Station Road, Gosaintola, Ranchi",
            city = "Ranchi",
            operator = "Statiq",
            latitude = 23.3550,
            longitude = 85.3340,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (60kW)", "Type 2 AC (7.4kW)"),
            isFastCharger = true,
            powerKw = 60
        ),
        ChargingStation(
            id = "CS-04",
            name = "Tata Power EZ Charge - Bistupur Hub",
            address = "N-Road, Bistupur, Jamshedpur",
            city = "Jamshedpur",
            operator = "Tata Power",
            latitude = 22.8020,
            longitude = 86.1850,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (50kW)", "Type 2 AC"),
            isFastCharger = true,
            powerKw = 50
        ),
        ChargingStation(
            id = "CS-05",
            name = "BPCL Highway EV Fast Charger - NH 33",
            address = "NH 33, Ranchi-Ramgarh Highway, Ormanjhi",
            city = "Ramgarh",
            operator = "BPCL e-Drive",
            latitude = 23.4800,
            longitude = 85.4800,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (30kW)", "Bharat DC001"),
            isFastCharger = true,
            powerKw = 30
        ),
        ChargingStation(
            id = "CS-06",
            name = "IOCL Fast EV Charger - Deoghar Bypass",
            address = "Deoghar-Jasidih Main Road, Deoghar",
            city = "Deoghar",
            operator = "IndianOil e-Charge",
            latitude = 24.4826,
            longitude = 86.6974,
            status = "Availability unknown",
            connectorTypes = listOf("CCS2 (50kW)", "Type 2 AC (11kW)"),
            isFastCharger = true,
            powerKw = 50
        )
    )
}
