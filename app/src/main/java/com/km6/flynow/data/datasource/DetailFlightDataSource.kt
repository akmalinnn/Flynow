package com.km6.flynow.data.datasource

import com.km6.flynow.data.source.network.model.flight.FlightResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

interface DetailFlightDataSource {
    suspend fun detailFlight(id: Int): FlightResponse
}

class DetailFlightDataSourceImpl(private val service: FlynowApiService): DetailFlightDataSource {
    override suspend fun detailFlight(id: Int): FlightResponse {
        return service.getFlight(id)
    }
}