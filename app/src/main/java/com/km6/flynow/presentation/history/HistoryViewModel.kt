package com.km6.flynow.presentation.history


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import com.km6.flynow.data.repository.HistoryRepository
import com.km6.flynow.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers


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
