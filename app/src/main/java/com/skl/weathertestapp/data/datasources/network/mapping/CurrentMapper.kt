package com.skl.weathertestapp.data.datasources.network.mapping

import com.skl.weathertestapp.data.datasources.network.dto.CurrentApiResponse
import com.skl.weathertestapp.data.datasources.network.util.Utils
import com.skl.weathertestapp.domain.Forecast.*
import java.text.SimpleDateFormat
import java.util.*

class CurrentMapper(val conditionMapper: ConditionMapper): Mapper<Current, CurrentApiResponse>{

    override fun toDomain(dto: CurrentApiResponse): Current {
        return Current(
            current_temp = dto.temp,
            last_updated_epoch = dto.last_updated_epoch,
            last_updated = Utils().dateFormatter(dto.last_updated_epoch),
            condition = conditionMapper.toDomain(dto.condition),
            wind_speed = dto.wind_speed
        )
    }
}