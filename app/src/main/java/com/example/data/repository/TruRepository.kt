package com.example.data.repository

import com.example.data.local.TruDao
import com.example.data.model.FlightItem
import com.example.data.model.ItineraryEventEntity
import com.example.data.model.SavedPlaceEntity
import com.example.data.model.TripEntity
import com.example.data.model.WalletTransaction
import com.example.data.remote.GeminiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TruRepository(
    private val dao: TruDao,
    private val geminiService: GeminiService = GeminiService()
) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            seedInitialDataIfNeeded()
        }
    }

    val allTrips: Flow<List<TripEntity>> = dao.getAllTrips()
    val allPlaces: Flow<List<SavedPlaceEntity>> = dao.getAllPlaces()

    fun getTripsByStatus(status: String): Flow<List<TripEntity>> = dao.getTripsByStatus(status)

    fun getPlacesByCategory(category: String): Flow<List<SavedPlaceEntity>> = dao.getPlacesByCategory(category)

    fun getItineraryForTrip(tripId: Long): Flow<List<ItineraryEventEntity>> = dao.getItineraryForTrip(tripId)

    suspend fun getTripById(id: Long): TripEntity? = dao.getTripById(id)

    suspend fun createTrip(trip: TripEntity): Long = dao.insertTrip(trip)

    suspend fun updateTrip(trip: TripEntity) = dao.updateTrip(trip)

    suspend fun toggleSavePlace(id: Long, isSaved: Boolean) = dao.updatePlaceSavedStatus(id, isSaved)

    suspend fun swapEventToEco(id: Long, newTitle: String, newCarbon: Double) {
        dao.updateEventEcoSwap(id, true, newTitle, newCarbon)
    }

    fun getDestinations(): List<com.example.data.model.Destination> {
        return com.example.data.seed.JharkhandData.destinations.map { seed ->
            com.example.data.model.Destination(
                id = seed.id,
                name = seed.name,
                city = seed.city,
                type = seed.type,
                lat = seed.lat,
                lng = seed.lng,
                entryFee = seed.entryFee,
                bestTime = seed.bestTime,
                imageUrl = seed.imageUrl,
                description = seed.description,
                rating = seed.rating,
                ecoCertified = seed.ecoCertified,
                crowdLevel = seed.crowdLevel,
                predictedFootfallAlert = seed.predictedFootfallAlert,
                alternativeSuggestion = seed.alternativeSuggestion
            )
        }
    }

    fun getDestinationById(id: Long): com.example.data.model.Destination? {
        return getDestinations().firstOrNull { it.id == id }
    }

    val evRentalRepository: EVRentalRepository = EVRentalRepositoryImpl()

    fun getEVRentals(): List<com.example.domain.model.EVRental> {
        return com.example.data.seed.JharkhandData.evRentals
    }

    fun getEVRentalById(id: Int): com.example.domain.model.EVRental? {
        return com.example.data.seed.JharkhandData.evRentals.firstOrNull { it.id == id }
    }

    fun getChargingStations(): List<com.example.domain.model.ChargingStation> {
        return com.example.data.seed.JharkhandData.chargingStations
    }

    suspend fun bookEVRentalForTrip(
        tripId: Long?,
        request: com.example.domain.model.EVBookingRequest
    ): Result<com.example.domain.model.EVBookingConfirmation> {
        val result = evRentalRepository.bookRental(request)
        if (result.isSuccess) {
            val booking = result.getOrThrow()
            val targetTripId = tripId ?: allTrips.first().firstOrNull()?.id ?: 1L
            val events = listOf(
                ItineraryEventEntity(
                    tripId = targetTripId,
                    dayNumber = 1,
                    timeSlot = "08:00",
                    title = "🚗 EV Pickup: ${booking.vehicle.vehicleName}",
                    description = "Pickup at ${booking.pickupLocation}. Range: ${booking.vehicle.rangeKm} km. Zero tailpipe emission.",
                    location = booking.pickupLocation,
                    category = "Transport",
                    costUsd = booking.costBreakdown.total,
                    carbonKg = 0.0,
                    isEcoFriendly = true
                ),
                ItineraryEventEntity(
                    tripId = targetTripId,
                    dayNumber = booking.durationDays,
                    timeSlot = "17:00",
                    title = "🔌 EV Return / Fast Charging",
                    description = "Drop-off at ${booking.dropLocation}. Saved ≈ ${booking.carbonSavings.co2AvoidedKg} kg CO₂ on this trip.",
                    location = booking.dropLocation,
                    category = "Transport",
                    costUsd = 0.0,
                    carbonKg = 0.0,
                    isEcoFriendly = true
                )
            )
            dao.insertItineraryEvents(events)
        }
        return result
    }

    suspend fun generateAiItinerary(prompt: String): String {
        return geminiService.generateTravelPlan(prompt)
    }

    fun getMockFlights(
        from: String = "London (LHR)",
        to: String = "Bali (DPS)",
        date: String = "12 Aug",
        passengers: Int = 1,
        travelClass: String = "Economy",
        tripType: String = "One Way"
    ): List<FlightItem> {
        val multiplier = if (travelClass == "Business") 2.4 else if (travelClass == "Premium") 1.5 else 1.0
        val basePass = passengers.coerceAtLeast(1)

        return listOf(
            FlightItem(
                id = "BA-101",
                airline = "British Airways",
                flightNumber = "BA 1102",
                departureTime = "11:00",
                arrivalTime = "12:25",
                duration = "1h 25m",
                fromCode = from.substringBefore(" ").ifBlank { "LHR" },
                toCode = to.substringBefore(" ").ifBlank { "DPS" },
                travelClass = travelClass,
                priceUsd = (546.0 * multiplier * basePass),
                co2Kg = 72.4,
                isEcoOption = true,
                localProviderPercent = 78,
                ecoFundPercent = 10,
                taxesPercent = 12
            ),
            FlightItem(
                id = "SQ-204",
                airline = "Singapore Airlines (SAF Green)",
                flightNumber = "SQ 328",
                departureTime = "14:15",
                arrivalTime = "16:40",
                duration = "2h 25m",
                fromCode = from.substringBefore(" ").ifBlank { "LHR" },
                toCode = to.substringBefore(" ").ifBlank { "DPS" },
                travelClass = travelClass,
                priceUsd = (610.0 * multiplier * basePass),
                co2Kg = 48.0, // Low CO2 with Sustainable Aviation Fuel
                isEcoOption = true,
                localProviderPercent = 82,
                ecoFundPercent = 12,
                taxesPercent = 6
            ),
            FlightItem(
                id = "LH-789",
                airline = "Lufthansa Express",
                flightNumber = "LH 402",
                departureTime = "18:00",
                arrivalTime = "19:45",
                duration = "1h 45m",
                fromCode = from.substringBefore(" ").ifBlank { "LHR" },
                toCode = to.substringBefore(" ").ifBlank { "DPS" },
                travelClass = travelClass,
                priceUsd = (485.0 * multiplier * basePass),
                co2Kg = 89.0,
                isEcoOption = false,
                localProviderPercent = 70,
                ecoFundPercent = 8,
                taxesPercent = 22
            )
        )
    }

    fun getWalletTransactions(): List<WalletTransaction> {
        return listOf(
            WalletTransaction(
                id = "TX-901",
                title = "Netarhat Eco-Lodge Direct",
                category = "Net-Zero Stay",
                timestamp = "Today, 10:24 AM",
                amountUsd = 145.00,
                isDebit = true,
                localProviderDirect = true,
                ecoTokenEarned = 25
            ),
            WalletTransaction(
                id = "TX-902",
                title = "Dalma EV Shuttle",
                category = "Clean Transport",
                timestamp = "Yesterday, 3:15 PM",
                amountUsd = 26.50,
                isDebit = true,
                localProviderDirect = true,
                ecoTokenEarned = 15
            ),
            WalletTransaction(
                id = "TX-903",
                title = "Tribal Handicraft Guild",
                category = "Handcrafted Souvenirs",
                timestamp = "24 Aug, 11:00 AM",
                amountUsd = 68.00,
                isDebit = true,
                localProviderDirect = true,
                ecoTokenEarned = 10
            ),
            WalletTransaction(
                id = "TX-904",
                title = "Eco-Token Community Dividend",
                category = "Reward Pool",
                timestamp = "20 Aug, 09:00 AM",
                amountUsd = 50.00,
                isDebit = false,
                localProviderDirect = false,
                ecoTokenEarned = 50
            )
        )
    }

    private suspend fun seedInitialDataIfNeeded() {
        val existingTrips = dao.getAllTrips().first()
        if (existingTrips.isEmpty()) {
            val seededTrips = listOf(
                TripEntity(
                    title = "Netarhat Sunrise Retreat",
                    destination = "Netarhat",
                    country = "Jharkhand",
                    dates = "Oct 12 – Oct 14 (3 days)",
                    durationDays = 3,
                    status = "upcoming",
                    percentReady = 68,
                    daysLeft = 5,
                    budgetTier = "Standard",
                    ecoScore = 92,
                    carbonKg = 12.5,
                    coverStyle = "emerald"
                ),
                TripEntity(
                    title = "Betla Safari Adventure",
                    destination = "Betla National Park",
                    country = "Jharkhand",
                    dates = "Nov 10 – Nov 12 (3 days)",
                    durationDays = 3,
                    status = "upcoming",
                    percentReady = 85,
                    daysLeft = 18,
                    budgetTier = "Standard",
                    ecoScore = 88,
                    carbonKg = 22.0,
                    coverStyle = "lime"
                ),
                TripEntity(
                    title = "Deoghar Spiritual Journey",
                    destination = "Deoghar",
                    country = "Jharkhand",
                    dates = "Dec 20 - 22 (3 Days)",
                    durationDays = 3,
                    status = "saved",
                    percentReady = 40,
                    daysLeft = 106,
                    budgetTier = "Standard",
                    ecoScore = 96,
                    carbonKg = 14.2,
                    coverStyle = "dark"
                ),
                TripEntity(
                    title = "Dalma Elephant Corridor",
                    destination = "Dalma Hills",
                    country = "Jharkhand",
                    dates = "Jan 15 – Jan 17 (3 days)",
                    durationDays = 3,
                    status = "saved",
                    percentReady = 90,
                    daysLeft = 132,
                    budgetTier = "Standard",
                    ecoScore = 94,
                    carbonKg = 19.0,
                    coverStyle = "emerald"
                )
            )
            dao.insertTrips(seededTrips)

            // Seed Itinerary Events for Netarhat Trip (Trip ID = 1)
            val events = listOf(
                ItineraryEventEntity(
                    tripId = 1,
                    dayNumber = 1,
                    timeSlot = "05:30",
                    title = "Sunrise Point Viewing",
                    description = "Witness the spectacular sunrise over the hills of Netarhat",
                    location = "Sunrise Point, Netarhat",
                    category = "Sightseeing",
                    costUsd = 0.0,
                    carbonKg = 0.5,
                    isEcoFriendly = true,
                    ecoAlternative = null
                ),
                ItineraryEventEntity(
                    tripId = 1,
                    dayNumber = 1,
                    timeSlot = "09:00",
                    title = "Local Breakfast at Pine Valley",
                    description = "Traditional local breakfast with fresh ingredients",
                    location = "Pine Valley Restaurant",
                    category = "Dining",
                    costUsd = 8.0,
                    carbonKg = 1.1,
                    isEcoFriendly = true,
                    ecoAlternative = null
                ),
                ItineraryEventEntity(
                    tripId = 1,
                    dayNumber = 1,
                    timeSlot = "11:30",
                    title = "Upper Ghaghri Waterfalls",
                    description = "A short trek to the beautiful waterfall surrounded by dense forests",
                    location = "Upper Ghaghri",
                    category = "Trekking",
                    costUsd = 5.0,
                    carbonKg = 1.2,
                    isEcoFriendly = true,
                    ecoAlternative = null
                ),
                ItineraryEventEntity(
                    tripId = 1,
                    dayNumber = 1,
                    timeSlot = "15:00",
                    title = "Magnolia Point Sunset",
                    description = "Enjoy the breathtaking sunset views across the Vindhya hills",
                    location = "Magnolia Point",
                    category = "Sightseeing",
                    costUsd = 0.0,
                    carbonKg = 0.5,
                    isEcoFriendly = true,
                    ecoAlternative = null
                )
            )
            dao.insertItineraryEvents(events)
        }

        val existingPlaces = dao.getAllPlaces().first()
        if (existingPlaces.size < com.example.data.seed.JharkhandData.destinations.size) {
            dao.deleteAllPlaces()
            val seededPlaces = com.example.data.seed.JharkhandData.destinations.map { seed ->
                SavedPlaceEntity(
                    id = seed.id,
                    title = seed.name,
                    subtitle = seed.city,
                    location = seed.type,
                    category = seed.category,
                    reasonWhy = seed.description,
                    ecoCertified = seed.ecoCertified,
                    crowdLevel = seed.crowdLevel,
                    imageUrl = seed.imageUrl,
                    predictedFootfallAlert = seed.predictedFootfallAlert,
                    alternativeSuggestion = seed.alternativeSuggestion,
                    carbonFootprintKg = seed.carbonFootprintKg,
                    isSaved = (seed.id == 1L || seed.id == 2L)
                )
            }
            dao.insertPlaces(seededPlaces)
        }
    }
}
