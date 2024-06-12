package com.km6.flynow.data.datasource.airport

import com.km6.flynow.data.source.network.model.airport.SearchAirportResponse

interface AirportDataSource {
    suspend fun searchAirport(token: String, keyword: String?): SearchAirportResponse

}