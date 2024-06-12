package com.km6.flynow.data.source.network.model.airport
import com.google.gson.annotations.SerializedName


data class AirportItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("airportCode")
    val airportCode: String,
    @SerializedName("airportName")
    val airportName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("deletedAt")
    val deletedAt: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)