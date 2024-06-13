package com.km6.flynow.presentation.filter_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.repository.FlightRepository
import kotlinx.coroutines.Dispatchers

class FilterResultViewModel(
    private val flightRepository: FlightRepository
) : ViewModel() {
    fun getFlights() = flightRepository.getFlight().asLiveData(Dispatchers.IO)
}