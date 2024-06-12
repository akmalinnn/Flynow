package com.km6.flynow.presentation.home

import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.SeatClass

class HomeViewModel : ViewModel() {
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

    fun updatePassengerData(adultCount: Int, childrenCount: Int, babyCount: Int, totalPassenger: Int) {
        this.adultCount = adultCount
        this.childrenCount = childrenCount
        this.babyCount = babyCount
        this.totalPassenger = totalPassenger
    }
}