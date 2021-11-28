package com.skl.weathertestapp.data.datasources.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDayResponse(

    @SerializedName("date_epoch")
    val date_epoch: Long,
    @SerializedName("day")
    val day: Day,
){
    data class Day(
        @SerializedName("maxtemp_c")
        val max_temp: Float,
        @SerializedName("mintemp_c")
        val min_temp: Float,
        @SerializedName("avgtemp_c")
        val avg_temp: Float,
        @SerializedName("condition")
        val condition: ConditionResponse,
    )
}
