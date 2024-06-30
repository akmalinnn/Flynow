package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Booking(
    val departureFlightId: Int?,
    var returnFlightId: Int?,
    val numAdults: Int,
    val numChildren: Int,
    val numBabies: Int,
    val passengerPayloads: List<Passenger>,
    val seatPayloads: SeatPayloads
) : Parcelable

@Parcelize
data class Passenger(
    val name: String = "",
    val dateOfBirth: String = "",
    val nationality: String = "",
    val docType: String = "",
    val docNumber: String = "",
    val issuingCountry: String = "",
    val expiryDate: String = "",
    val passengerType: String = "",
): Parcelable

@Parcelize
data class SeatPayloads(
    var departureSeats: List<String>?,
    var returnSeats: List<String>?
) : Parcelable

