package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.ItineraryEventEntity
import com.example.data.model.SavedPlaceEntity
import com.example.data.model.TripEntity

@Database(
    entities = [
        TripEntity::class,
        SavedPlaceEntity::class,
        ItineraryEventEntity::class,
        EmergencyContactEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class TruDatabase : RoomDatabase() {
    abstract fun truDao(): TruDao
    abstract fun emergencyContactDao(): EmergencyContactDao

    companion object {
        @Volatile
        private var INSTANCE: TruDatabase? = null

        fun getInstance(context: Context): TruDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TruDatabase::class.java,
                    "tru_travel.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
