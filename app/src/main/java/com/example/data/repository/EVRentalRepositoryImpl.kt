package com.example.data.repository

import com.example.data.seed.JharkhandData
import com.example.domain.model.ChargingStation
import com.example.domain.model.EVBookingConfirmation
import com.example.domain.model.EVBookingRequest
import com.example.domain.model.EVRental
import com.example.domain.usecase.CalculateCarbonSavingsUseCase
import com.example.domain.usecase.CalculateRentalCostUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class EVRentalRepositoryImpl(
    private val calculateCostUseCase: CalculateRentalCostUseCase = CalculateRentalCostUseCase(),
    private val calculateCarbonUseCase: CalculateCarbonSavingsUseCase = CalculateCarbonSavingsUseCase()
) : EVRentalRepository {

    private val rentalsFlow = MutableStateFlow(JharkhandData.evRentals)
    private val chargingStationsFlow = MutableStateFlow(JharkhandData.chargingStations)
    private val confirmedBookings = mutableListOf<EVBookingConfirmation>()

    override fun getEVRentals(): Flow<List<EVRental>> = rentalsFlow.asStateFlow()

    override fun getEVRentalById(id: Int): EVRental? {
        return rentalsFlow.value.firstOrNull { it.id == id }
    }

    override fun getChargingStations(): Flow<List<ChargingStation>> = chargingStationsFlow.asStateFlow()

    override suspend fun bookRental(request: EVBookingRequest): Result<EVBookingConfirmation> {
        val vehicle = getEVRentalById(request.vehicleId)
            ?: return Result.failure(IllegalArgumentException("Vehicle not found with ID ${request.vehicleId}"))

        val costBreakdown = calculateCostUseCase(
            vehicle = vehicle,
            durationDays = request.durationDays,
            quantity = request.quantity,
            includePortableCharger = request.includePortableCharger,
            includeChildSeat = request.includeChildSeat
        )

        val carbonSavings = calculateCarbonUseCase(
            vehicle = vehicle,
            durationDays = request.durationDays
        )

        val bookingConfirmation = EVBookingConfirmation(
            bookingId = "JV-EV-${UUID.randomUUID().toString().take(8).uppercase()}",
            vehicle = vehicle,
            pickupLocation = request.pickupLocation,
            dropLocation = request.dropLocation,
            startDate = request.startDate,
            endDate = request.endDate,
            durationDays = request.durationDays,
            costBreakdown = costBreakdown,
            carbonSavings = carbonSavings
        )

        confirmedBookings.add(bookingConfirmation)
        return Result.success(bookingConfirmation)
    }

    fun getConfirmedBookings(): List<EVBookingConfirmation> = confirmedBookings.toList()
}
