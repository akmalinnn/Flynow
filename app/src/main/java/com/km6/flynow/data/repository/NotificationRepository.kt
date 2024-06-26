package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.notification.NotificationDatasource
import com.km6.flynow.data.mapper.toNotification
import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
    fun getNotification(): Flow<ResultWrapper<List<Notification>>>
}

class NotificationRepositoryImpl(private val notificationDataSource: NotificationDatasource) :
    NotificationRepository {
    override fun getNotification(): Flow<ResultWrapper<List<Notification>>> {
        return proceedFlow {
            val response = notificationDataSource.getNotification()
            if (response.message == "Success") {
                notificationDataSource.getNotification().data.toNotification()
            } else {
                throw Exception(response.message)
            }
        }
    }
}