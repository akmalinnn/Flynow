package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.notification.NotificationDatasource
import com.km6.flynow.data.datasource.notification.NotificationDetailDatasource
import com.km6.flynow.data.mapper.toNotification
import com.km6.flynow.data.mapper.toNotificationDetail
import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.data.model.notification.NotificationDetail
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface NotificationDetailRepository {
    fun readNotification(id: Int): Flow<ResultWrapper<NotificationDetail>>
}

class NotificationDetailRepositoryImpl(private val notificationDataSource: NotificationDetailDatasource) :
    NotificationDetailRepository {
    override fun readNotification(id: Int): Flow<ResultWrapper<NotificationDetail>> {
        return proceedFlow {
            notificationDataSource.readNotification(id).data.toNotificationDetail()
        }
    }
}