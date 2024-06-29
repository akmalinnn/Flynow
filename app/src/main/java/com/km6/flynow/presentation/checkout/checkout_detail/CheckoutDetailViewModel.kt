package com.km6.flynow.presentation.checkout.checkout_detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.Seat
import com.km6.flynow.data.repository.PaymentRepository
import com.km6.flynow.data.source.local.pref.UserPreference

class CheckoutDetailViewModel(
    extras: Bundle?,
    preference: UserPreference,
    private val repository: PaymentRepository,
) : ViewModel() {
    private val price = extras?.getDouble(CheckoutDetailActivity.EXTRA_TOTAL_PRICE)
    val flight = extras?.getParcelable<Flight>(CheckoutDetailActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(CheckoutDetailActivity.EXTRA_FLIGHT_RETURN)
    val params = extras?.getParcelable<Search>(CheckoutDetailActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val passenger = extras?.getParcelableArrayList<BioPenumpang>(CheckoutDetailActivity.EXTRA_PASSENGER_DATA)
    val seat = extras?.getParcelableArrayList<Seat>(CheckoutDetailActivity.EXTRA_SEAT)
    val seatReturn = extras?.getParcelableArrayList<Seat>(CheckoutDetailActivity.EXTRA_SEAT_RETURN)
//    val userId = preference.getUserID()
//    var totalPrice = 0.0
//    var adultPrice: Double? = null
//    var childrenPrice: Double? = null
//    val babyPrice = 0.0
//    var taxPrice = 0.0
//
//    fun calculatePrice() {
//        val totalPriceEx = (price!! * (params?.totalQty!! - params?.baby))
//        adultPrice = (price * params.adult)
//        childrenPrice = (price * params.child)
//        taxPrice = totalPriceEx * 0.01
//        totalPrice = totalPriceEx + taxPrice
//    }
//
//    fun createPayment(
//        totalPrice: Int,
//        status: String = params?.status!!,
//        passengerId: List<String> = passenger?.list?.map { it.id!! }!!,
//        seatId: List<String> = seat?.list?.map { it.id }!!,
//        seatReturnId: List<String> = seatReturn?.list?.map { it.id } ?: listOf(""),
//    ) = repository.createPayment(totalPrice, status, passengerId, seatId, seatReturnId).asLiveData(Dispatchers.IO)
}
