package com.km6.flynow.data.source.network.model.history

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HistoryResponse(
    val bookings: List<Booking>
)
