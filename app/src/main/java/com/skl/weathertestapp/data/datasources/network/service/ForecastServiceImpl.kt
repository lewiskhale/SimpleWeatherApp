package com.skl.weathertestapp.data.datasources.network.service

import com.skl.weathertestapp.data.datasources.network.ApiConstants
import com.skl.weathertestapp.data.datasources.network.mapping.ForecastMapper
import com.skl.weathertestapp.domain.Forecast
import io.ktor.client.*
import io.ktor.client.request.*

class ForecastServiceImpl(
    private val client: HttpClient,
    private val mapper: ForecastMapper
    ): ForecastService {

    override suspend fun getForecastForTheWeek(): Forecast {
        return try {
            val forecast = mapper.toDomain(
                    client.get{
                    url(ApiConstants.FORECAST_ENDPOINT)
                    parameter("key",ApiConstants.API_KEY)
                    parameter("q", "Cape Town")
                }
            )
            forecast
        } catch (e: Exception){
            throw Exception("Exception has been thrown: ${e.message}")
        }
        /*catch (e: ClientRequestException){
            val msg = e.response.status.description
            null

        } catch (e: ServerResponseException){
            val msg = e.response.status.description
            null
        }*/
    }
}