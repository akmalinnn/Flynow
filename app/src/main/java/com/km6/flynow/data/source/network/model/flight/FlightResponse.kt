package com.km6.flynow.data.source.network.model.flight


import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("message")
    val message: String?
)