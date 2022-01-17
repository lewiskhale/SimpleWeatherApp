package com.skl.weathertestapp.data.datasources.network.dto

import com.google.gson.annotations.SerializedName

data class ConditionResponse(
    @SerializedName("text")
    val text: String,
    @SerializedName("icon")
    val img: String,
    @SerializedName("code")
    val code: Int
)
