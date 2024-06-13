package com.km6.flynow.data.source.network.model.flight


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("airline")
    val airline: Airline?,
    @SerializedName("airlineId")
    val airlineId: Int?,
    @SerializedName("arrivalAirport")
    val arrivalAirport: ArrivalAirport?,
    @SerializedName("arrivalAirportId")
    val arrivalAirportId: Int?,
    @SerializedName("arrivalTime")
    val arrivalTime: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("departureAirport")
    val departureAirport: DepartureAirport?,
    @SerializedName("departureAirportId")
    val departureAirportId: Int?,
    @SerializedName("departureTime")
    val departureTime: String?,
    @SerializedName("flightClass")
    val flightClass: String?,
    @SerializedName("flightCode")
    val flightCode: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("information")
    val information: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("terminal")
    val terminal: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)