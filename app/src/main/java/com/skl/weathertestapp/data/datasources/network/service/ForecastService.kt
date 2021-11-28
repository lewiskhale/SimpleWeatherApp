package com.skl.weathertestapp.data.datasources.network.service

import com.skl.weathertestapp.domain.Forecast

interface ForecastService {

    suspend fun getForecastForTheWeek(): Forecast?
}