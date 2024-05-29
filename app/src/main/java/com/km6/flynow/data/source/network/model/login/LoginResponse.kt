package com.km6.flynow.data.source.network.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: LoginData
)