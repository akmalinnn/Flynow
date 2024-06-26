package com.km6.flynow.data.source.network.model.notification


data class NotificationResponse(
    val message: String,
    val data: List<NotificationItem>
)