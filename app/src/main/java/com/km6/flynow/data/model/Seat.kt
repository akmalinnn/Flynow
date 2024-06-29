package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seat(
    val id: Int,
    val seatCode: String,
    val seatAvailable: Boolean,
    val flightId: Int,
    val deletedAt: String?,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable

@Parcelize
data class ListSeat(
    val seats : List<Seat>
) : Parcelable
