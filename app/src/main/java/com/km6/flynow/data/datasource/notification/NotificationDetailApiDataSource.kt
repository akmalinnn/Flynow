package com.km6.flynow.data.datasource.notification


import com.km6.flynow.data.source.network.model.notification.NotificationDetailResponse
import com.km6.flynow.data.source.network.model.notification.NotificationResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


class NotificationDetailApiDataSource (private val service: FlynowApiService) : NotificationDetailDatasource {
    override suspend fun readNotification(id: Int): NotificationDetailResponse {
        return  service.readNotification(id = id)
    }

}