package com.km6.flynow.data.datasource.notification


import com.km6.flynow.data.source.network.model.notification.NotificationResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


class NotificationApiDataSource (private val service: FlynowApiService) : NotificationDatasource {
    override suspend fun getNotification(): NotificationResponse {
        return  service.getNotification()
    }


}