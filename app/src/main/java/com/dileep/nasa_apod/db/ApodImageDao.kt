package com.dileep.nasa_apod.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApodImageDao {
    @Query("SELECT * FROM apodImage")
    fun getApodImage(): List<ApodImageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApodImage(apodImageData: ApodImageData)

    @Query("DELETE FROM apodImage")
    suspend fun deleteAll()
}