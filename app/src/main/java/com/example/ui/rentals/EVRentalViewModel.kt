package com.example.ui.rentals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.TruRepository
import com.example.domain.model.ChargingStation
import com.example.domain.model.EVBookingConfirmation
import com.example.domain.model.EVBookingRequest
import com.example.domain.model.EVRental
import com.example.domain.model.RangeCheckResult
import com.example.domain.usecase.CheckEVRangeUseCase
import com.example.domain.usecase.SearchEVRentalsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class RentalTab {
    RIDES,
    CHARGING
}

data class EVRentalUiState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedCity: String = "All",
    val selectedPriceFilter: String = "All",
    val selectedRangeFilter: String = "All",
    val selectedEcoFilter: String = "All",
    val activeTab: RentalTab = RentalTab.RIDES,
    val filteredRentals: List<EVRental> = emptyList(),
    val chargingStations: List<ChargingStation> = emptyList(),
    val selectedRentalForBooking: EVRental? = null,
    val bookingConfirmation: EVBookingConfirmation? = null,
    val isBookingInProgress: Boolean = false,
    val bookingErrorMessage: String? = null,
    val rangeCheckResult: RangeCheckResult? = null
)

class EVRentalViewModel(
    private val repository: TruRepository,
    private val searchUseCase: SearchEVRentalsUseCase = SearchEVRentalsUseCase(),
    private val checkRangeUseCase: CheckEVRangeUseCase = CheckEVRangeUseCase()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("All")
    private val _selectedCity = MutableStateFlow("All")
    private val _selectedPriceFilter = MutableStateFlow("All")
    private val _selectedRangeFilter = MutableStateFlow("All")
    private val _selectedEcoFilter = MutableStateFlow("All")
    private val _activeTab = MutableStateFlow(RentalTab.RIDES)
    private val _selectedRentalForBooking = MutableStateFlow<EVRental?>(null)
    private val _bookingConfirmation = MutableStateFlow<EVBookingConfirmation?>(null)
    private val _isBookingInProgress = MutableStateFlow(false)
    private val _bookingErrorMessage = MutableStateFlow<String?>(null)
    private val _rangeCheckResult = MutableStateFlow<RangeCheckResult?>(null)

    private val allRentals = repository.getEVRentals()
    private val allStations = repository.getChargingStations()

    val uiState: StateFlow<EVRentalUiState> = combine(
        combine(
            _searchQuery,
            _selectedCategory,
            _selectedCity,
            _selectedPriceFilter,
            _selectedRangeFilter
        ) { query, category, city, price, range ->
            FiltersTuple(query, category, city, price, range)
        },
        _selectedEcoFilter,
        _activeTab,
        _selectedRentalForBooking,
        _bookingConfirmation
    ) { filters, eco, tab, bookingRental, confirmation ->
        val filtered = searchUseCase(
            rentals = allRentals,
            query = filters.query,
            category = filters.category,
            city = filters.city,
            priceFilter = filters.price,
            rangeFilter = filters.range,
            ecoScoreFilter = eco,
            availableOnly = false
        )

        val filteredStations = if (filters.city == "All") {
            allStations
        } else {
            allStations.filter { it.city.equals(filters.city, ignoreCase = true) }
        }

        EVRentalUiState(
            searchQuery = filters.query,
            selectedCategory = filters.category,
            selectedCity = filters.city,
            selectedPriceFilter = filters.price,
            selectedRangeFilter = filters.range,
            selectedEcoFilter = eco,
            activeTab = tab,
            filteredRentals = filtered,
            chargingStations = filteredStations,
            selectedRentalForBooking = bookingRental,
            bookingConfirmation = confirmation,
            isBookingInProgress = _isBookingInProgress.value,
            bookingErrorMessage = _bookingErrorMessage.value,
            rangeCheckResult = _rangeCheckResult.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EVRentalUiState(
            filteredRentals = allRentals,
            chargingStations = allStations
        )
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun onCitySelected(city: String) {
        _selectedCity.value = city
    }

    fun onPriceFilterSelected(price: String) {
        _selectedPriceFilter.value = price
    }

    fun onRangeFilterSelected(range: String) {
        _selectedRangeFilter.value = range
    }

    fun onEcoFilterSelected(eco: String) {
        _selectedEcoFilter.value = eco
    }

    fun onTabSelected(tab: RentalTab) {
        _activeTab.value = tab
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _selectedCategory.value = "All"
        _selectedCity.value = "All"
        _selectedPriceFilter.value = "All"
        _selectedRangeFilter.value = "All"
        _selectedEcoFilter.value = "All"
    }

    fun openBookingSheet(rental: EVRental) {
        _selectedRentalForBooking.value = rental
        _bookingErrorMessage.value = null
    }

    fun dismissBookingSheet() {
        _selectedRentalForBooking.value = null
        _bookingErrorMessage.value = null
    }

    fun dismissBookingConfirmation() {
        _bookingConfirmation.value = null
    }

    fun checkRouteRange(vehicle: EVRental, routeName: String, distanceKm: Double) {
        val result = checkRangeUseCase(
            vehicle = vehicle,
            routeName = routeName,
            distanceKm = distanceKm,
            availableVehicles = allRentals
        )
        _rangeCheckResult.value = result
    }

    fun clearRangeCheck() {
        _rangeCheckResult.value = null
    }

    fun bookRental(request: EVBookingRequest, tripId: Long? = null) {
        viewModelScope.launch {
            _isBookingInProgress.value = true
            _bookingErrorMessage.value = null
            try {
                val result = repository.bookEVRentalForTrip(tripId, request)
                if (result.isSuccess) {
                    _bookingConfirmation.value = result.getOrNull()
                    _selectedRentalForBooking.value = null
                } else {
                    _bookingErrorMessage.value = result.exceptionOrNull()?.message ?: "Booking failed"
                }
            } catch (e: Exception) {
                _bookingErrorMessage.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isBookingInProgress.value = false
            }
        }
    }

    private data class FiltersTuple(
        val query: String,
        val category: String,
        val city: String,
        val price: String,
        val range: String
    )
}
