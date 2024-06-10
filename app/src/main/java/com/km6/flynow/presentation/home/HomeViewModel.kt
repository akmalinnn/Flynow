package com.km6.flynow.presentation.home

import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.Airport

class HomeViewModel : ViewModel() {
    var airportFrom: Airport? = null
    var airportTo: Airport? = null
    var adultCount: Int = 0
    var childrenCount: Int = 0
    var babyCount: Int = 0
    var totalPassenger : Int = 0

    fun updatePassengerData(adultCount: Int, childrenCount: Int, babyCount: Int, totalPassenger: Int) {
        this.adultCount = adultCount
        this.childrenCount = childrenCount
        this.babyCount = babyCount
        this.totalPassenger = totalPassenger

    }
}