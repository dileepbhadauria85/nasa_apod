package com.dileep.nasa_apod.util

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

const val ITEM_TYPE_KEY = "last_updated_date"

class PrefsHelper {

    companion object {
        private fun preferences(context: Context): SharedPreferences =
            context.getSharedPreferences("lastUpdatedDate", Context.MODE_PRIVATE)

        fun setUpdatedDate(context: Context, type: String) {
            preferences(context).edit()
                .putString(ITEM_TYPE_KEY, type)
                .apply()
        }

        fun getUpdatedDate(context: Context): String =
            preferences(context).getString(ITEM_TYPE_KEY, "")!!

        fun getCurrentFormattedDate(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            return sdf.format(Date())
        }


    }
}