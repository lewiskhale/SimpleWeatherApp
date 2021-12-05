package com.skl.weathertestapp.data.repository

import com.skl.weathertestapp.domain.Forecast
import com.skl.weathertestapp.utils.Resource

interface ForecastRepository {

    suspend fun getForecast(lat: Double, long: Double): Resource<Forecast>
}