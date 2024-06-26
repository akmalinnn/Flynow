package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.data.model.notification.NotificationDetail
import com.km6.flynow.data.source.network.model.notification.NotificationDetailItem
import com.km6.flynow.data.source.network.model.notification.NotificationItem


fun NotificationDetailItem.toNotificationDetail() =
    NotificationDetail(
        id = this.id,
        userId = this.userId,
        message = this.message,
        type = this.type,
        isRead = this.isRead,
        createdAt = this.createdAt,

        )

//fun Collection<NotificationDetailItem>.toNotificationDetail() =
//    this.map {
//        it.toNotificationDetail()
//    } ?: listOf()