package com.km6.flynow.data.source.network.model.airport

data class SearchAirportResponse (
    val message: String,
    val data: List<AirportItemResponse>
)