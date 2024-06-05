package com.km6.flynow.presentation.choose_destination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.model.Destination
import com.km6.flynow.data.repository.AirportRepository
import com.km6.flynow.data.repository.DestinationHistoryRepository
import com.km6.flynow.data.repository.UserRepository
import com.km6.flynow.data.source.network.model.airport.AirportItemResponse
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChooseDestinationViewModel (
    private val airportRepository:AirportRepository,
    private val userRepository: UserRepository,
    private val destinationHistoryRepository: DestinationHistoryRepository
): ViewModel() {

    fun addToDestinationHistory(idDestination: String, destination: String) : LiveData<ResultWrapper<Boolean>> {
        val des = Destination(id = idDestination, destinationName = destination)

        return des.let {
            destinationHistoryRepository.createDestination(it).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Destination not found")))}
    }

    fun getAllDestinationHistory() = destinationHistoryRepository.getUserDestinationHistoryData().asLiveData(Dispatchers.IO)

    fun removeDestination(item: Destination) {
        viewModelScope.launch(Dispatchers.IO) {
            destinationHistoryRepository.deleteDestination(item).collect()
        }
    }

    fun deleteAllDestinationHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            destinationHistoryRepository.deleteAllDestinationHistory().collect()
        }
    }

    fun searchAirport(token: String, keyword: String? = null) =
        airportRepository.searchAirport(token,keyword).asLiveData(Dispatchers.IO)

    fun getToken(): String? {
        return userRepository.getToken()
    }
}