package com.km6.flynow.data.source.network.model.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.km6.flynow.data.source.network.model.login.LoginData

@Keep
data class PaymentResponse(
    @SerializedName("message")
    val message: String,
//    @SerializedName("data")
//    val data: PaymentData
)

//data class PaymentData(
//    @SerializedName("message")
//    val token: String,
//    @SerializedName("redirect_url")
//    val snapUrl: String
//)
