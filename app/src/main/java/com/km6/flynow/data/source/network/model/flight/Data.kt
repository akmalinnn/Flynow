package com.km6.flynow.data.source.network.model.flight


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("departureFlights")
    val departureFlights: List<DepartureFlight>?,
    @SerializedName("returnFlights")
    val returnFlights: List<ReturnFlight>?
)