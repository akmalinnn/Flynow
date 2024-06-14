package com.km6.flynow.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.HistoryItem
import com.km6.flynow.data.repository.UserRepository

class HistoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    val historyItems: LiveData<List<HistoryItem>> get() = _historyItems

    init {
        loadHistoryItems()
    }

    fun getUser() = userRepository.getUser()
    fun getToken() = userRepository.getToken()
    fun logOut() {
        userRepository.clearUser()
        userRepository.clearToken()
    }

    private fun loadHistoryItems() {
        // Mock data
        val items = listOf(
            HistoryItem(
                status = "Paid!",
                departureCity = "Jakarta",
                departureDate = "5 Maret 2023",
                departureTime = "19:00",
                arrivalCity = "Melbourne",
                arrivalDate = "5 Maret 2023",
                arrivalTime = "21:00",
                durationHours = "4h",
                durationMinutes = "0m",
                bookingCode = "453653657",
                flightClass = "Economy",
                price = "IDR 5.950.000"
            ),
            HistoryItem(
                status = "Issue!",
                departureCity = "Singapore",
                departureDate = "10 April 2023",
                departureTime = "15:30",
                arrivalCity = "Tokyo",
                arrivalDate = "10 April 2023",
                arrivalTime = "21:00",
                durationHours = "6h",
                durationMinutes = "30m",
                bookingCode = "789456123",
                flightClass = "Business",
                price = "IDR 4.950.000"
            ),
            HistoryItem(
                status = "Paid!",
                departureCity = "New York",
                departureDate = "15 May 2023",
                departureTime = "08:00",
                arrivalCity = "London",
                arrivalDate = "15 May 2023",
                arrivalTime = "13:30",
                durationHours = "7h",
                durationMinutes = "30m",
                bookingCode = "951357852",
                flightClass = "First Class",
                price = "IDR 2.950.000"
            ),
            HistoryItem(
                status = "Paid!",
                departureCity = "Los Angeles",
                departureDate = "15 May 2023",
                departureTime = "08:00",
                arrivalCity = "London",
                arrivalDate = "15 May 2023",
                arrivalTime = "13:30",
                durationHours = "7h",
                durationMinutes = "30m",
                bookingCode = "951357852",
                flightClass = "First Class",
                price = "IDR 5.950.000"
            ),
            // Add more items as needed
        )
        _historyItems.value = items
    }
}
