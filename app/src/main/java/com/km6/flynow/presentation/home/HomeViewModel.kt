package com.km6.flynow.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.data.repository.FavoriteFlightRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val favoriteFlightRepository: FavoriteFlightRepository
) : ViewModel() {

    var airportFrom: Airport? = null
    var airportTo: Airport? = null
    var departureDate: Long = 0L
    var returnDate: Long = 0L

    var adultCount: Int = 0
    var childrenCount: Int = 0
    var babyCount: Int = 0
    var totalPassenger : Int = 0

    var seatClass: SeatClass? = null
    var seatClassPosition: Int = -1

    var isRoundTrip = false

    fun updatePassengerData(adultCount: Int, childrenCount: Int, babyCount: Int, totalPassenger: Int) {
        this.adultCount = adultCount
        this.childrenCount = childrenCount
        this.babyCount = babyCount
        this.totalPassenger = totalPassenger
    }

    fun getFavoriteFlight() =
        favoriteFlightRepository.getFavoriteFlight().asLiveData(Dispatchers.IO)


}