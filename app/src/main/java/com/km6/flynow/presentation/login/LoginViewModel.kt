package com.km6.flynow.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.repository.AuthRepository
import com.km6.flynow.data.repository.UserRepository
import com.km6.flynow.data.source.network.model.login.LoginResponse
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel (private val authRepository: AuthRepository, private val userRepository: UserRepository): ViewModel() {
    private val _loginResult = MutableLiveData<ResultWrapper<Pair<Boolean, LoginResponse>>>()
    val loginResult: LiveData<ResultWrapper<Pair<Boolean, LoginResponse>>>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password).collect {
                if (it is ResultWrapper.Success) {
                    val response = it.payload?.second
                    val user = response?.data?.user
                    val token = response?.data?.token

                    if (user != null) {
                        userRepository.saveUser(user)
                    }
                    if (token != null) {
                        userRepository.saveToken(token)
                    }

                    withContext(Dispatchers.Main) {
                        _loginResult.postValue(it)
                    }
                } else {
                    _loginResult.postValue(it)
                }
            }
        }
    }
}