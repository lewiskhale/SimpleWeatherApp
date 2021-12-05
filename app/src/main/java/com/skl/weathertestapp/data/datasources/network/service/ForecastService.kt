package com.skl.weathertestapp.data.datasources.network.service

import com.skl.weathertestapp.domain.Forecast
import com.skl.weathertestapp.utils.Resource

interface ForecastService {

    suspend fun getForecastForTheWeek(lat: Double, long: Double): Resource<Forecast>
}