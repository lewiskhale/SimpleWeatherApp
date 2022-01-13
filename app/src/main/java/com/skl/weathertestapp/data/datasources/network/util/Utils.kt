package com.skl.weathertestapp.data.datasources.network.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Utils {

    fun dateFormatter(long: Long): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(long), TimeZone.getDefault().toZoneId())
            val formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM, FormatStyle.SHORT)
            localDateTime.format(formatter)
        } else {
            val date = Date(long * 1000L)
            val sdf = SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss", Locale.ROOT)
            sdf.format(date)
        }
    }
}