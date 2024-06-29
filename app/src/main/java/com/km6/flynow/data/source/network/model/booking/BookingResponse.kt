package com.km6.flynow.data.source.network.model.booking

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BookingResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: BookingData
)

@Keep
data class BookingData(
    @SerializedName("passengers")
    val passengers: List<Passenger>,
    @SerializedName("booking")
    val booking: Booking,
    @SerializedName("bookingDetails")
    val bookingDetails: List<BookingDetail>
)

@Keep
data class Passenger(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("docType")
    val docType: String,
    @SerializedName("docNumber")
    val docNumber: String,
    @SerializedName("issuingCountry")
    val issuingCountry: String,
    @SerializedName("expiryDate")
    val expiryDate: String,
    @SerializedName("passengerType")
    val passengerType: String,
)

@Keep
data class Booking(
    @SerializedName("id")
    val id: Int,
    @SerializedName("departureFlightId")
    val departureFlightId: String,
    @SerializedName("returnFlightId")
    val returnFlightId: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("numAdults")
    val numAdults: Int,
    @SerializedName("numChildren")
    val numChildren: Int,
    @SerializedName("numBabies")
    val numBabies: Int,
    @SerializedName("bookingCode")
    val bookingCode: String,
)

@Keep
data class BookingDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("bookingId")
    val bookingId: Int,
    @SerializedName("passengerId")
    val passengerId: Int,
    @SerializedName("seatId")
    val seatId: Int,

)
