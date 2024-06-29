package com.km6.flynow.presentation.checkout.chooseseat

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.Seat
import com.km6.flynow.data.repository.SeatRepository
import kotlinx.coroutines.Dispatchers

class ChooseSeatViewModel(
    extras: Bundle?,
    private val repository: SeatRepository,
) : ViewModel() {
    val search = extras?.getParcelable<Search>(ChooseSeatActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(ChooseSeatActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(ChooseSeatActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(ChooseSeatActivity.EXTRA_TOTAL_PRICE)
    val passengerDataList =
        extras?.getParcelableArrayList<BioPenumpang>(ChooseSeatActivity.EXTRA_PASSENGER_DATA)
    var selectableSeat = 0
    var capacity = 0
    private var seatClass: String = ""

    val seat = MutableLiveData<List<Seat>>()
    val seats: LiveData<List<Seat>> = seat

    init {
        //capacity()
        seatClass()
    }

//    private fun capacity() {
//        when (search?.clas?.name) {
//            "Economy" -> {
//                capacity = flight?.capacityEconomy!!
//            }
//            "Business" -> {
//                capacity = flight?.capacityBussines!!
//            }
//            "First Class" -> {
//                capacity = flight?.capacityFirstClass!!
//            }
//        }
//        selectableSeat = search?.totalPassenger!! - search.baby
//    }

    private fun seatClass() {
        when (search?.clas?.name) {
            "Economy" -> {
                seatClass = "ECONOMY"
            }

            "Business" -> {
                seatClass = "BUSINESS"
            }

            "First Class" -> {
                seatClass = "FIRST_CLASS"
            }
        }
    }

    fun getSeat(
        id: Int
    ) = repository.getSeats(id).asLiveData(Dispatchers.IO)
}
