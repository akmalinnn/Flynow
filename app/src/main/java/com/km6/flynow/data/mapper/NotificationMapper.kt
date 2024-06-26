package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.data.source.network.model.notification.NotificationItem


fun NotificationItem.toNotification() =
    Notification(
        id = this.id,
        userId = this.userId,
        message = this.message,
        type = this.type,
        isRead = this.isRead,
        createdAt = this.createdAt,

        )

fun Collection<NotificationItem>.toNotification() =
    this.map {
        it.toNotification()
    } ?: listOf()