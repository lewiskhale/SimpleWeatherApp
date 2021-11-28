package com.skl.weathertestapp.data.datasources.network.dto

import com.google.gson.annotations.SerializedName

data class CurrentApiResponse(
    @SerializedName("temp_c")
    val temp: Float,
    @SerializedName("last_updated_epoch")
    val last_updated_epoch: Long,
    @SerializedName("condition")
    val condition: ConditionResponse,
    @SerializedName("wind_kph")
    val wind_speed: Float
)
