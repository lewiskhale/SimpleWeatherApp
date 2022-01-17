package com.skl.weathertestapp.domain

data class Forecast(

    val current: Current,
    val forecast: WeeksForecast
){
    data class Current(
        val current_temp: Float,
        val last_updated_epoch: Long,
        val last_updated: String,
        val condition: Condition,
        val wind_speed: Float
    )

    data class WeeksForecast(
        val forecast_list: List<WeekdayForecast>
    ){
        data class WeekdayForecast(
            val date_epoch: Long,
            val date_info: String,
            val day_of_the_week_info: WeekdayInfo
        ){
            data class WeekdayInfo(
                val max: Float,
                val min: Float,
                val avg: Float,
                val condition: Condition
            )
        }
    }

    data class Condition(
        val text: String,
        val img: String,
        val code: Int
    )
}
