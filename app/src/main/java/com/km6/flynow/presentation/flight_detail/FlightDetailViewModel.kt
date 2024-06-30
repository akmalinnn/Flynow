package com.km6.flynow.presentation.flight_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.repository.DetailFlightRepository
import com.km6.flynow.data.repository.FlightRepository
import com.km6.flynow.data.repository.UserRepository
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class FlightDetailViewModel(
    private val flightDetailRepository: DetailFlightRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun getFlight(id: Int)= flightDetailRepository.getFlight(id).asLiveData(Dispatchers.IO)

    fun getToken(): String? {
        return userRepository.getToken()
    }
}