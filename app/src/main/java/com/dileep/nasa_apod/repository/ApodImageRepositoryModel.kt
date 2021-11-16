package com.dileep.nasa_apod.repository

import com.dileep.nasa_apod.db.ApodImageData


data class ApodImageRepositoryModel(
        val responseStatus: ApodImageRepository.ResponseStatus,
        val response: ApodImageData
)