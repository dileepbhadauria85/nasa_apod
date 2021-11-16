package com.dileep.nasa_apod.network

import com.squareup.moshi.Json

data class ApodApiData(
    @Json(name = "date") val date: String,
    @Json(name = "explanation") val explanation: String,
    @Json(name = "title") val title: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "thumbnail_url") val thumbnailUrl: String?,
    @Json(name = "url") val imgUrl: String,
)