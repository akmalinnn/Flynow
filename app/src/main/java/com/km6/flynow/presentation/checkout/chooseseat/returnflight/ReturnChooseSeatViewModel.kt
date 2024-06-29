package com.km6.flynow.presentation.checkout.chooseseat.returnflight

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.ListSeat
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.Seat
import com.km6.flynow.data.repository.SeatRepository
import com.km6.flynow.presentation.checkout.chooseseat.ChooseSeatActivity
import kotlinx.coroutines.Dispatchers

class ReturnChooseSeatViewModel(
    extras: Bundle?,
    private val repository: SeatRepository,
) : ViewModel() {
    val search = extras?.getParcelable<Search>(ReturnChooseSeatActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(ReturnChooseSeatActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(ReturnChooseSeatActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(ChooseSeatActivity.EXTRA_TOTAL_PRICE)
    val passengerDataList = extras?.getParcelableArrayList<BioPenumpang>(
        ReturnChooseSeatActivity.EXTRA_PASSENGER_DATA)
    val seatDeparture = extras?.getParcelableArrayList<Seat>(ReturnChooseSeatActivity.EXTRA_SEAT)
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
//        when (params?.ticketClass?.name) {
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
//        selectableSeat = params?.totalQty!! - params.babyQty
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
