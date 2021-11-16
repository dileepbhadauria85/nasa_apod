package com.dileep.nasa_apod.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apodImage")
class ApodImageData (
    @PrimaryKey() @NonNull val date:String = "",
    val title:String? = "",
    val explanation:String? = "",
    var imageUrl:String? = ""
    )