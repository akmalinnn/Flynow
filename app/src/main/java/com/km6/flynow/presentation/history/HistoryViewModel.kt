package com.km6.flynow.presentation.history

import androidx.lifecycle.ViewModel
import com.km6.flynow.data.repository.UserRepository

class HistoryViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUser() = userRepository.getUser()
    fun getToken() = userRepository.getToken()
    fun logOut() {
        userRepository.clearUser()
        userRepository.clearToken()
    }
}