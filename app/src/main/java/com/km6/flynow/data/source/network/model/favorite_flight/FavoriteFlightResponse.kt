package com.km6.flynow.data.source.network.model.favorite_flight

data class FavoriteFlightResponse(
    val message: String,
    val data: List<FavoriteFlightItem>
)