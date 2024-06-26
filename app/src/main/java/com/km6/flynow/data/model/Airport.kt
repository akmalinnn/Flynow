package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Airport(
    val id: Int,
    val airportCode: String,
    val airportName: String,
    val city: String,
    val country: String,
    val deletedAt: String?,
    val createdAt: String,
    val updatedAt: String
): Parcelable