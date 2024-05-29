package com.km6.flynow.data.source.network.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.km6.flynow.data.model.User

@Keep
data class LoginData(
    @SerializedName("user")
    val user: User,
    @SerializedName("token")
    val token: String
)