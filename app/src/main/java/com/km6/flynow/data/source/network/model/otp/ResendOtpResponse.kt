package com.km6.flynow.data.source.network.model.otp

import com.google.gson.annotations.SerializedName

data class ResendOtpResponse(
    @SerializedName("message") val message: String
)
