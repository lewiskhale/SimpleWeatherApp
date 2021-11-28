package com.skl.weathertestapp.data.datasources.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastApiResponse(

    @SerializedName("current")
    val current: CurrentApiResponse,
    @SerializedName("forecast")
    val forecast: ForecastList
){
    data class ForecastList(
        @SerializedName("forecastday")
        val forecast_day: List<ForecastDayResponse>
    )
}
