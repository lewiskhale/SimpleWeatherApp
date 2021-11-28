package com.skl.weathertestapp.data.datasources.network.util

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun dateFormatter(long: Long): String{
        val date = Date(long * 1000L)
        val sdf = SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss")
        return sdf.format(date)
    }
}