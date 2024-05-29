package com.km6.flynow.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ResultWrapper<Boolean>>()
    val registerResult: LiveData<ResultWrapper<Boolean>>
        get() = _registerResult

    fun register(name: String, email: String, password: String, phoneNumber: String, imageFile: File) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.register(name, email, password, phoneNumber, imageFile).collect {
                _registerResult.postValue(it)
            }
        }
    }
}