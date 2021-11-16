package com.dileep.nasa_apod.network

import com.dileep.nasa_apod.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    interface ApodApiService {
        @GET("/planetary/apod")
        suspend fun getAPod(@Query("api_key") api_key : String, @Query("thumbs") thumbs : String): Response<ApodApiData>
    }


    object ApodApiNetworkHelper {
        val retrofitService: ApodApiService by lazy { retrofit.create(ApodApiService::class.java) }
    }