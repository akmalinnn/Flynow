package com.km6.flynow.data.source.network.model.otp


data class VerifyOtpRequest(
    val email: String,
    val otp: String
)