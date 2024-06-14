package com.km6.flynow.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.Notification // Replace with your actual Notification model
import com.km6.flynow.data.repository.UserRepository

class NotificationViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>>
        get() = _notifications

    fun getUser() = userRepository.getUser()

    fun getToken() = userRepository.getToken()

    fun logOut() {
        userRepository.clearUser()
        userRepository.clearToken()
    }

    fun loadNotifications() {
        // Example: Fetch notifications from repository or create dummy data
        val dummyNotifications = listOf(
            Notification("Promotion", "20 Maret, 14:04", "Dapatkan Potongan 50% Tiket!", "Syarat dan Ketentuan berlaku!"),
            Notification("New Message", "21 Maret, 09:30", "You have received a new message.", ""),
            Notification("Reminder", "22 Maret, 18:00", "Don't forget to attend the meeting tomorrow.", "Meeting Room B, 10 AM")
        )
        _notifications.value = dummyNotifications // Update LiveData with fetched notifications
    }
}
