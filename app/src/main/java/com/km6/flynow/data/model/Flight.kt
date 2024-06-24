package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flight(
    val id: Int?,
    val flightCode: String?,
    val terminal: String?,
    val departureAirportId: Int?,
    val arrivalAirportId: Int?,
    val airlineId: Int?,
    val departureTime: String?,
    val arrivalTime: String?,
    val price: Double?,
    val flightClass: String?,
    val information: String?,
    val deletedAt: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val depatureid: Int?,
    val depatureairportCode: String?,
    val depatureairportName: String?,
    val depaturecity: String?,
    val depaturecountry: String?,
    val depaturedeletedAt: String?,
    val arrivalid: Int?,
    val arrivalairportCode: String?,
    val arrivalairportName: String?,
    val arrivalcity: String?,
    val arrivalcountry: String?,
    val arrivaldeletedAt: String?,
    val airlineid: Int?,
    val airlineCode: String?,
    val airlineName: String?,
    val image: String?,
): Parcelable


