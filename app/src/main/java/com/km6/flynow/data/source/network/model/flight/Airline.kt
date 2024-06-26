package com.km6.flynow.data.source.network.model.flight


import com.google.gson.annotations.SerializedName

data class Airline(
    @SerializedName("airlineCode")
    val airlineCode: String?,
    @SerializedName("airlineName")
    val airlineName: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("deletedAt")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)