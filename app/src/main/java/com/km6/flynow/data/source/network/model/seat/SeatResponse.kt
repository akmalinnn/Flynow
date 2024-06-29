package com.km6.flynow.data.source.network.model.seat

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeatResponse(
    @SerializedName("data")
    val data: List<SeatData>?,
    @SerializedName("message")
    val message: String?,
)
