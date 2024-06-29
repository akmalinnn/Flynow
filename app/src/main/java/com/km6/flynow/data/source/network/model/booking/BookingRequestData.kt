package com.km6.flynow.data.source.network.model.booking

data class BookingRequest(
    val departureFlightId: String,
    val returnFlightId: String?,
    val numAdults: Int,
    val numChildren: Int,
    val numBabies: Int,
    val passengerPayloads: List<PassengerPayload>,
    val seatPayloads: SeatPayloads
)

data class PassengerPayload(
    val name: String,
    val dateOfBirth: String,
    val nationality: String,
    val docType: String,
    val docNumber: String,
    val issuingCountry: String,
    val expiryDate: String,
    val passengerType: String
)

data class SeatPayloads(
    val departureSeats: List<String>,
    val returnSeats: List<String>?
)
