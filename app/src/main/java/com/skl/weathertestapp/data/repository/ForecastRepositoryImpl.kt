package com.skl.weathertestapp.data.repository

import com.skl.weathertestapp.data.datasources.network.service.ForecastService
import com.skl.weathertestapp.domain.Forecast

class ForecastRepositoryImpl(private val api: ForecastService): ForecastRepository {

    override suspend fun getForecast(): Forecast? {
        return api.getForecastForTheWeek()
    }
}