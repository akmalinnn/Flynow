package com.km6.flynow.presentation.checkout.chooseseat

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Passenger
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.repository.BookingRepository
import com.km6.flynow.data.repository.SeatRepository
import com.km6.flynow.data.source.network.model.history.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData
import kotlinx.coroutines.Dispatchers

class SelectPassengerSeatViewModel(
    extras: Bundle?,
    private val repository: SeatRepository,
    private val repositoryBooking: BookingRepository,
) : ViewModel() {

    suspend fun createBooking(bookingRequest: Booking) = repositoryBooking.createBooking(bookingRequest)

    var capacity = 72


    val seat = MutableLiveData<List<SeatData>>()
    val seats: LiveData<List<SeatData>> = seat

    fun getSeat(id: Int) = repository.getSeat(id).asLiveData(Dispatchers.IO)
}
