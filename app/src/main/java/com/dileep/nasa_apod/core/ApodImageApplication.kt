package com.dileep.nasa_apod.core

import android.app.Application
import com.dileep.nasa_apod.db.ApodImageDatabase
import com.dileep.nasa_apod.repository.ApodImageRepository

class ApodImageApplication : Application () {
    val database by lazy { ApodImageDatabase.getDatabase(this) }
    val repository by lazy { ApodImageRepository(database.apodImageDao(),this) }
}