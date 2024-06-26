package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteFlight(
    val id: Int,
    val flightId: Int,
    val airlineId: Int,
    val airline: String,
    val departureAirportId: Int,
    val departureAirport: String,
    val departureCity: String,
    val arrivalAirportId: Int,
    val arrivalAirport: String,
    val arrivalCity: String,
    val departureTime: String,
    val price: Int,
    val discount: String,
    val description: String,
    val isFavorite: Boolean,
    val image: String,
) : Parcelable
