package com.skl.weathertestapp.data.datasources.network.mapping

import com.skl.weathertestapp.data.datasources.network.dto.ForecastApiResponse.*
import com.skl.weathertestapp.data.datasources.network.dto.ForecastDayResponse
import com.skl.weathertestapp.data.datasources.network.dto.ForecastDayResponse.*
import com.skl.weathertestapp.data.datasources.network.util.Utils
import com.skl.weathertestapp.domain.Forecast.*
import com.skl.weathertestapp.domain.Forecast.WeeksForecast.*
import com.skl.weathertestapp.domain.Forecast.WeeksForecast.WeekdayForecast.*

class WeeklyForecastMapper(private val conditionMapper: ConditionMapper): Mapper<WeeksForecast, ForecastList> {

    override fun toDomain(dto: ForecastList): WeeksForecast {
        return WeeksForecast(
            forecast_list = parseForecastList(dto.forecast_day)
        )
    }

    private fun parseForecastList(
        list: List<ForecastDayResponse>
    ): List<WeekdayForecast>{
        return list.map { parseWeekday(it) }
    }

    private fun parseWeekday(
        forecast_day: ForecastDayResponse
    ): WeekdayForecast{
        return WeekdayForecast(
            date_epoch = forecast_day.date_epoch,
            date_info = Utils().dateFormatter(forecast_day.date_epoch),
            day_of_the_week_info = parseDay(forecast_day.day),
        )
    }

    private fun parseDay(day: Day): WeekdayInfo {
        return WeekdayInfo(
            max = day.max_temp,
            min = day.min_temp,
            avg = day.avg_temp,
            condition = conditionMapper.toDomain(day.condition)
        )
    }

}