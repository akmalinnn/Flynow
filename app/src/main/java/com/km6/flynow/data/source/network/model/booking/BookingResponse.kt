package com.km6.flynow.data.source.network.model.booking

data class Passenger(
    val id: Int?,
    val name: String,
    val dateOfBirth: String,
    val nationality: String,
    val docType: String,
    val docNumber: String,
    val issuingCountry: String,
    val expiryDate: String,
    val passengerType: String,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)

data class Booking(
    val id: Int?,
    val departureFlightId: String,
    val returnFlightId: String?,
    val userId: String,
    val numAdults: Int,
    val numChildren: Int,
    val numBabies: Int,
    val bookingCode: String,
    val updatedAt: String?,
    val createdAt: String?,
    val deletedAt: String?
)

data class BookingDetail(
    val id: Int?,
    val bookingId: Int,
    val passengerId: Int,
    val seatId: Int,
    val updatedAt: String?,
    val createdAt: String?,
    val deletedAt: String?
)

data class BookingResponseData(
    val passengers: List<Passenger>,
    val booking: Booking,
    val bookingDetails: List<BookingDetail>
)

data class BookingResponse(
    val message: String,
    val data: BookingResponseData
)