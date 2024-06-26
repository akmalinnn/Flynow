package com.km6.flynow.data.datasource.favorite_flight

import com.km6.flynow.data.source.network.model.favorite_flight.FavoriteFlightResponse


interface FavoriteFlightDataSource {
    suspend fun getFavoriteFlight(): FavoriteFlightResponse
}
