package com.km6.flynow.data.datasource.notification

import com.km6.flynow.data.source.network.model.notification.NotificationDetailResponse
import com.km6.flynow.data.source.network.model.notification.NotificationResponse


interface NotificationDetailDatasource {
    suspend fun readNotification(id: Int): NotificationDetailResponse
}
