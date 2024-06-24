package com.km6.flynow.data.source.network.model.history

import com.google.gson.annotations.SerializedName

data class HistoryItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("bookingCode")
    val bookingCode: String,
    @SerializedName("departureFlightId")
    val departureFlightId: String,
    @SerializedName("returnFlightId")
    val returnFlightId: String?,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("numAdults")
    val numAdults: Int,
    @SerializedName("numChildren")
    val numChildren: Int,
    @SerializedName("numBabies")
    val numBabies: Int,
    @SerializedName("flight")
    val flight: Flight,
    @SerializedName("details")
    val details: BookingDetails,
    @SerializedName("payment")
    val payment: Payment?,
)

data class BookingDetails(
    @SerializedName("departure")
    val departure: List<BookingDetail>,
    @SerializedName("return")
    val returnDetails: List<BookingDetail?>
)

data class Flight(
    @SerializedName("departure")
    val departure: FlightInfo,
    @SerializedName("return")
    val returnFlight: FlightInfo?
)

data class FlightInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("flightCode")
    val flightCode: String,
    @SerializedName("terminal")
    val terminal: String,
    @SerializedName("departureAirportId")
    val departureAirportId: Int,
    @SerializedName("arrivalAirportId")
    val arrivalAirportId: Int,
    @SerializedName("airlineId")
    val airlineId: Int?,
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("arrivalTime")
    val arrivalTime: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("flightClass")
    val flightClass: String,
    @SerializedName("information")
    val information: String,
    @SerializedName("departureAirport")
    val departureAirport: Airport,
    @SerializedName("arrivalAirport")
    val arrivalAirport: Airport,
    @SerializedName("airline")
    val airline: Airline?,
)

data class Airport(
    @SerializedName("id")
    val id: Int,
    @SerializedName("airportCode")
    val airportCode: String,
    @SerializedName("airportName")
    val airportName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
)

data class Airline(
    @SerializedName("id")
    val id: Int,
    @SerializedName("airlineCode")
    val airlineCode: String,
    @SerializedName("airlineName")
    val airlineName: String,
    @SerializedName("image")
    val image: String,
)

data class Payment(
    @SerializedName("bookingId")
    val bookingId: String,
    @SerializedName("paymentAmount")
    val paymentAmount: Int,
    @SerializedName("paymentStatus")
    val paymentStatus: String?,
    @SerializedName("snapToken")
    val snapToken: String,
    @SerializedName("snapRedirectUrl")
    val snapRedirectUrl: String,
)

data class BookingDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("bookingId")
    val bookingId: Int,
    @SerializedName("passengerId")
    val passengerId: Int,
    @SerializedName("seatId")
    val seatId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("passenger")
    val passenger: Passenger,
    @SerializedName("seat")
    val seat: Seat
)

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

data class Seat(
    @SerializedName("id")
    val id: Int,
    @SerializedName("seatCode")
    val seatCode: String,
    @SerializedName("seatAvailable")
    val seatAvailable: Boolean,
    @SerializedName("flightId")
    val flightId: Int,
)