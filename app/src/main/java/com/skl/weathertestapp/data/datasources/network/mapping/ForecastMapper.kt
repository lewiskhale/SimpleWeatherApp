package com.skl.weathertestapp.data.datasources.network.mapping

import com.skl.weathertestapp.data.datasources.network.dto.ForecastApiResponse
import com.skl.weathertestapp.domain.Forecast

class ForecastMapper(
    private val currentMapper: CurrentMapper,
    private val forecastMapper: WeeklyForecastMapper)
    : Mapper<Forecast, ForecastApiResponse> {

    override fun toDomain(dto: ForecastApiResponse): Forecast {
        return Forecast(
            current = currentMapper.toDomain(dto.current),
            forecast = forecastMapper.toDomain(dto.forecast)
        )
    }
}