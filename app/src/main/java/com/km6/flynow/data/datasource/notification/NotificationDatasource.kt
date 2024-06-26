package com.km6.flynow.data.datasource.notification

import com.km6.flynow.data.source.network.model.notification.NotificationResponse


interface NotificationDatasource {
    suspend fun getNotification(): NotificationResponse

}
