package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.source.local.pref.UserPreference

class BiodataPenumpangViewModel(
    extras: Bundle?,
    private val preference: UserPreference,
) : ViewModel() {
    val search = extras?.getParcelable<Search>(BiodataPenumpangActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(BiodataPenumpangActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(BiodataPenumpangActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(BiodataPenumpangActivity.EXTRA_TOTAL_PRICE)
    val userId = preference.getUserData()
}