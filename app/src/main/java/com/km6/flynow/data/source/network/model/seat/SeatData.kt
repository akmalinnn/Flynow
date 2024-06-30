package com.km6.flynow.data.source.network.model.seat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeatData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("seatCode")
    val seatCode: String,
    @SerializedName("seatAvailable")
    val seatAvailable: Boolean,
    @SerializedName("flightId")
    val flightId: Int,
)
