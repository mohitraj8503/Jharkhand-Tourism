package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ItineraryEventEntity
import com.example.data.model.SavedPlaceEntity
import com.example.data.model.TripEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TruDao {
    @Query("SELECT * FROM trips ORDER BY id DESC")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE status = :status ORDER BY id DESC")
    fun getTripsByStatus(status: String): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE id = :id LIMIT 1")
    suspend fun getTripById(id: Long): TripEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: List<TripEntity>)

    @Update
    suspend fun updateTrip(trip: TripEntity)

    // Saved Places
    @Query("SELECT * FROM saved_places ORDER BY id ASC")
    fun getAllPlaces(): Flow<List<SavedPlaceEntity>>

    @Query("SELECT * FROM saved_places WHERE category = :category ORDER BY id ASC")
    fun getPlacesByCategory(category: String): Flow<List<SavedPlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<SavedPlaceEntity>)

    @Query("DELETE FROM saved_places")
    suspend fun deleteAllPlaces()

    @Query("UPDATE saved_places SET isSaved = :isSaved WHERE id = :id")
    suspend fun updatePlaceSavedStatus(id: Long, isSaved: Boolean)

    // Itinerary Events
    @Query("SELECT * FROM itinerary_events WHERE tripId = :tripId ORDER BY dayNumber ASC, id ASC")
    fun getItineraryForTrip(tripId: Long): Flow<List<ItineraryEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItineraryEvents(events: List<ItineraryEventEntity>)

    @Query("UPDATE itinerary_events SET isEcoFriendly = :isEco, title = :title, carbonKg = :carbon WHERE id = :id")
    suspend fun updateEventEcoSwap(id: Long, isEco: Boolean, title: String, carbon: Double)
}
