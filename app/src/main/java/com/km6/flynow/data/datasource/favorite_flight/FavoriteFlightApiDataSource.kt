package com.km6.flynow.data.datasource.favorite_flight


import com.km6.flynow.data.source.network.model.favorite_flight.FavoriteFlightResponse
import com.km6.flynow.data.source.network.model.history.HistoryItemResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


class FavoriteFlightApiDataSource (private val service: FlynowApiService) : FavoriteFlightDataSource {
    override suspend fun getFavoriteFlight(): FavoriteFlightResponse {
        return service.getFavoriteFlights()
    }
}