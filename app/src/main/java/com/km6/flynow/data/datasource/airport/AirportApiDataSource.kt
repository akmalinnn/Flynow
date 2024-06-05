package com.km6.flynow.data.datasource.airport

import com.km6.flynow.data.source.network.model.airport.SearchAirportResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

class AirportApiDataSource (private val service: FlynowApiService) : AirportDataSource {
    override suspend fun searchAirport(token: String, keyword: String?): SearchAirportResponse {
        return  service.searchAirport(token, keyword)
    }
}