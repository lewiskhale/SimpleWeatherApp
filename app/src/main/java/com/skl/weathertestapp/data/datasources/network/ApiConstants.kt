package com.skl.weathertestapp.data.datasources.network

object ApiConstants {

    const val API_KEY = "b28e6ecabe924830936121907212811"

    private const val BASE_URL = "https://api.weatherapi.com/v1"

    const val CURRENT_WEATHER_ENDPOINT = "$BASE_URL/current.json"

    const val FORECAST_ENDPOINT = "$BASE_URL/forecast.json"

}