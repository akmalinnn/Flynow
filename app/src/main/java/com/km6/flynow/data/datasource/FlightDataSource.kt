package com.km6.flynow.data.datasource

import com.km6.flynow.data.source.network.model.flight.FlightResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


interface FlightDataSource {
    suspend fun getFlight() : FlightResponse
}

class FlightApiDataSource(
    private val service: FlynowApiService,
) : FlightDataSource{
    override suspend fun getFlight(): FlightResponse {
        return service.getFlight()
    }
}