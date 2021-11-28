package com.skl.weathertestapp.data.datasources.network.mapping

interface Mapper<Domain, DTO> {

    fun toDomain(dto: DTO): Domain
}