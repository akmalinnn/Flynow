package com.km6.flynow.data.source.network.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationDetailItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("createdAt")
    val createdAt: String,
)
