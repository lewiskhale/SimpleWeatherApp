package com.skl.weathertestapp.data.repository

import com.skl.weathertestapp.domain.Forecast

interface ForecastRepository {

    suspend fun getForecast(): Forecast?
}