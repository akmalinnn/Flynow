package com.km6.flynow.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _forgotPasswordResult = MutableLiveData<ResultWrapper<Boolean>>()
    val forgotPasswordResult: LiveData<ResultWrapper<Boolean>>
        get() = _forgotPasswordResult

    fun forgotPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.forgotPassword(email).collect {
                withContext(Dispatchers.Main) {
                    _forgotPasswordResult.value = it
                }
            }
        }
    }
}
