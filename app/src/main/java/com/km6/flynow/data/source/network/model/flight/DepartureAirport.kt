package com.km6.flynow.data.source.network.model.flight


import com.google.gson.annotations.SerializedName

data class DepartureAirport(
    @SerializedName("airportCode")
    val airportCode: String?,
    @SerializedName("airportName")
    val airportName: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)