package com.dileep.nasa_apod.repository

import android.content.Context
import com.dileep.nasa_apod.BuildConfig
import com.dileep.nasa_apod.db.ApodImageDao
import com.dileep.nasa_apod.db.ApodImageData
import com.dileep.nasa_apod.network.ApodApiNetworkHelper
import com.dileep.nasa_apod.util.NetworkManager.isNetworkAvailable
import com.dileep.nasa_apod.util.PrefsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodImageRepository(val apodImageDao: ApodImageDao, val context: Context) {

    enum class ResponseStatus() {
        API_ERROR,
        OFFLINE_DATA_NA,
        LAST_AVAILABLE_DATA,
        NONE
    }

    suspend fun getApodImage(): ApodImageRepositoryModel {
        if (isNetworkAvailable(context)) {
            if (PrefsHelper.getUpdatedDate(context)
                    .contentEquals(PrefsHelper.getCurrentFormattedDate())
            ) {
                return fetchApodFromDb()
            } else {
                return fetchApodFromApi()
            }
        } else {
            return fetchApodFromDb()
        }
    }

    suspend fun fetchApodFromApi(): ApodImageRepositoryModel {
        val response = ApodApiNetworkHelper.retrofitService.getAPod(BuildConfig.API_KEY, "true")
        response.body()?.let {
            if (response.isSuccessful) {
                val apodImageData = ApodImageData(it.date, it.title, it.explanation, it.imgUrl)
                if (!it.mediaType.contentEquals("image")) {
                    apodImageData.imageUrl = it.thumbnailUrl
                }
                apodImageDao.deleteAll()
                apodImageDao.insertApodImage(apodImageData)
                PrefsHelper.setUpdatedDate(context, PrefsHelper.getCurrentFormattedDate())
                return ApodImageRepositoryModel(ResponseStatus.NONE, apodImageData)
            } else {
                return ApodImageRepositoryModel(ResponseStatus.API_ERROR, ApodImageData())
            }
        } ?: return ApodImageRepositoryModel(ResponseStatus.API_ERROR, ApodImageData())
    }

    suspend fun fetchApodFromDb(): ApodImageRepositoryModel {
        lateinit var apodImageData: List<ApodImageData>
        withContext(Dispatchers.IO) {
            apodImageData = apodImageDao.getApodImage()
        }
        if (apodImageData.isEmpty()) {
            return ApodImageRepositoryModel(ResponseStatus.OFFLINE_DATA_NA, ApodImageData())
        } else {
            if (PrefsHelper.getUpdatedDate(context)
                    .contentEquals(PrefsHelper.getCurrentFormattedDate())
            ) {
                return ApodImageRepositoryModel(ResponseStatus.NONE, apodImageData.get(0))
            } else {
                return ApodImageRepositoryModel(
                    ResponseStatus.LAST_AVAILABLE_DATA,
                    apodImageData.get(0)
                )
            }
        }
    }

}