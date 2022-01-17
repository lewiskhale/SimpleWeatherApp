package com.skl.weathertestapp.data.datasources.network.mapping

import com.skl.weathertestapp.data.datasources.network.dto.ConditionResponse
import com.skl.weathertestapp.domain.Forecast

class ConditionMapper: Mapper<Forecast.Condition, ConditionResponse> {

    override fun toDomain(dto: ConditionResponse): Forecast.Condition {
        return Forecast.Condition(
            text = dto.text.orEmpty(),
            img = dto.img.orEmpty(),
            code = dto.code ?: -1
        )
    }
}