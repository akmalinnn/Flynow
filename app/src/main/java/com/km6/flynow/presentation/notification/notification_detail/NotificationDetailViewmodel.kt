package com.km6.flynow.presentation.notification.notification_detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.notification.Notification
import com.km6.flynow.data.repository.NotificationDetailRepository
import com.km6.flynow.data.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers


class NotificationDetailViewmodel(
    private val extras: Bundle?, private val notificationDetailRepository: NotificationDetailRepository,
) : ViewModel() {

    val notificationDetail = extras?.getInt(NotificationDetailActivity.EXTRA_NOTIFICATION_ITEM)

    fun readNotification(id: Int) = notificationDetailRepository.readNotification(id).asLiveData(Dispatchers.IO)


}


