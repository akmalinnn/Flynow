package com.km6.flynow.data.source.network.model.favorite_flight

import com.google.gson.annotations.SerializedName

data class FavoriteFlightItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("flightId")
    val flightId: Int,
    @SerializedName("airlineId")
    val airlineId: Int,
    @SerializedName("airline")
    val airline: String,
    @SerializedName("departureAirportId")
    val departureAirportId: Int,
    @SerializedName("departureAirport")
    val departureAirport: String,
    @SerializedName("departureCity")
    val departureCity: String,
    @SerializedName("arrivalAirportId")
    val arrivalAirportId: Int,
    @SerializedName("arrivalAirport")
    val arrivalAirport: String,
    @SerializedName("arrivalCity")
    val arrivalCity: String,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("image")
    val image: String,
)
