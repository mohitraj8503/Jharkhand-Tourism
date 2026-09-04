package com.example.data.repository

import com.example.domain.model.ChargingStation
import com.example.domain.model.EVBookingConfirmation
import com.example.domain.model.EVBookingRequest
import com.example.domain.model.EVRental
import kotlinx.coroutines.flow.Flow

interface EVRentalRepository {
    fun getEVRentals(): Flow<List<EVRental>>
    fun getEVRentalById(id: Int): EVRental?
    fun getChargingStations(): Flow<List<ChargingStation>>
    suspend fun bookRental(request: EVBookingRequest): Result<EVBookingConfirmation>
}
