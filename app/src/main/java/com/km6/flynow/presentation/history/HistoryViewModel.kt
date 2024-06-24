package com.km6.flynow.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.repository.AirportRepository
import com.km6.flynow.data.repository.DestinationHistoryRepository
import com.km6.flynow.data.repository.HistoryRepository
import com.km6.flynow.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    private val userRepository: UserRepository,
    ) : ViewModel(
) {

    fun getHistory() =
        historyRepository.getHistory().asLiveData(Dispatchers.IO)

    fun getToken(): String? {
        return userRepository.getToken()
    }


}
