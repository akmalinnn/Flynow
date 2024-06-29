package com.km6.flynow.data.model

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?,
)
