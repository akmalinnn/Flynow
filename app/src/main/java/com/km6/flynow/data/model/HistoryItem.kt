package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryItem(
    val status: String,
    val departureCity: String,
    val departureDate: String,
    val departureTime: String,
    val arrivalCity: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val durationHours: String,
    val durationMinutes: String,
    val bookingCode: String,
    val flightClass: String,
    val price: String
): Parcelable
