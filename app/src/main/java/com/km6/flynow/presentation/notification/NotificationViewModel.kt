package com.km6.flynow.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.repository.NotificationRepository
import com.km6.flynow.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class NotificationViewModel(private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
) : ViewModel() {

        fun getNotification() =
            notificationRepository.getNotification().asLiveData(Dispatchers.IO)

        fun getToken(): String? {
            return userRepository.getToken()
        }


}

