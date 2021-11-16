package com.dileep.nasa_apod.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ApodImageData::class], version = 1, exportSchema = false)
abstract class ApodImageDatabase: RoomDatabase() {
    abstract fun apodImageDao(): ApodImageDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ApodImageDatabase? = null

        fun getDatabase(context: Context): ApodImageDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApodImageDatabase::class.java,
                    "apodImage_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
