package com.km6.flynow.data.source.network.model.seat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeatData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("seatCode")
    val seatCode: Int,
    @SerializedName("seatAvailable")
    val seatAvailable: Boolean,
    @SerializedName("flightId")
    val flightId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
