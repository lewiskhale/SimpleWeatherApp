package com.skl.weathertestapp.data.repository

import com.skl.weathertestapp.data.datasources.network.service.ForecastService
import com.skl.weathertestapp.domain.Forecast
import com.skl.weathertestapp.utils.Resource

class ForecastRepositoryImpl(private val api: ForecastService): ForecastRepository {

    override suspend fun getForecast(lat: Double, long: Double): Resource<Forecast> {
        return api.getForecastForTheWeek(lat, long)
    }
}