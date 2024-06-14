package com.km6.flynow.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.km6.flynow.data.model.User

@Keep
data class Booking(
    @SerializedName("bookingCode") val bookingCode: String,
    val status: String,
    val departure: FlightInfo,
    val arrival: FlightInfo,
    val airline: AirlineInfo,
    val passenger: PassengerInfo,
    val price: PriceInfo
)

data class FlightInfo(
    val time: String,
    val date: String,
    val airport: String
)

data class AirlineInfo(
    val name: String,
    val classType: String,
    val code: String
)

data class PassengerInfo(
    val name: String,
    val id: String
)

data class PriceInfo(
    val adult: String,
    val baby: String,
    val tax: String,
    val totalPerPax: String
)