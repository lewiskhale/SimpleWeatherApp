package com.skl.weathertestapp.data.datasources.network

object ApiConstants {

    const val API_KEY1 = "b28e6ecabe924830936121907212811"   //for weatherapi.com
    //const val API_KEY2 = "cc186e899496ae6c36ebd383d4245878"   for openweather

    private const val BASE_URL1 = "https://api.weatherapi.com/v1"
    //private const val BASE_URL2 = "https://api.openweathermap.org"

    //const val CURRENT_WEATHER_ENDPOINT = "$BASE_URL/current.json"

    const val FORECAST_ENDPOINT = "$BASE_URL1/forecast.json"

    //openweather
    //const val CURRENT_ENDPOINT = "$BASE_URL2/data/2.5/weather"

}