package com.km6.flynow.data.source.network.model.history

data class FlightHistory(
    val flightNumber: String,
    val departureCity: String,
    val arrivalCity: String,
    val departureTime: String,
    val arrivalTime: String
)
