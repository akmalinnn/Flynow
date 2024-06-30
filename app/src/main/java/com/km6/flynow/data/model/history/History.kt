package com.km6.flynow.data.model.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class History(


    val id: Int,
    val bookingCode: String,
    val departureFlightId: String,
    val returnFlightId: String?,
    val userId: String,
    val numAdults: Int,
    val numChildren: Int,
    val numBabies: Int,
    val departureTime: String,
    val arrivalTime: String,
    val price: Int,
    val flightClass: String,
    val flightCode: String,
    val flightInformation: String,

    val airportDepartureCity: String,
    val airportArrivalCity: String,
    val airportDepartureName: String,
    val airportArrivalName: String,

    val airlineDepartureName: String?,
    val airlineLogo: String?,
    val passengerName: List<String>,
    val passengerSeat: List<String>,


    val departureTimeReturn: String?,
    val arrivalTimeReturn: String?,
    val priceReturn: Int?,
    val flightClassReturn: String?,
    val flightCodeReturn: String?,
    val flightInformationReturn: String,

    val airportDepartureCityReturn: String?,
    val airportArrivalCityReturn: String?,
    val airportDepartureNameReturn: String?,
    val airportArrivalNameReturn: String?,

    val airlineDepartureNameReturn: String?,
    val airlineLogoReturn: String?,
    val paymentStatus: String?,
    val snapUrl: String?,
    val passengerSeatReturn: List<String>?



) : Parcelable
