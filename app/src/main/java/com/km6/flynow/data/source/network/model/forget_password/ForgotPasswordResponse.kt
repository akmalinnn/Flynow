package com.km6.flynow.data.source.network.model.forget_password

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("message") val message: String
)
