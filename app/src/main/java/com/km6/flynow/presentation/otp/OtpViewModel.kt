package com.km6.flynow.presentation.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OtpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _verifyOtpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val verifyOtpResult: LiveData<ResultWrapper<Boolean>>
        get() = _verifyOtpResult

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOtp(email, otp).collect {
                _verifyOtpResult.postValue(it)
            }
        }
    }

}
