package com.km6.flynow.presentation.filter_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.km6.flynow.data.model.Filter
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.data.repository.FlightRepository
import kotlinx.coroutines.Dispatchers

class FilterResultViewModel(
    private val flightRepository: FlightRepository
) : ViewModel() {

    var selectedFilter: Filter? = null
    var filterPosition: Int = -1


    fun searchFlight(da: String? = null,
                     aa: String? = null,
                     dd: String? = null,
                     rd: String? = null,
                     adult: String? = null,
                     child: String? = null,
                     baby: String? = null,
                     clas: String? = null,
                     sort: String? = null) = flightRepository.searchFlight(da,aa,dd,rd,adult, child, baby, clas, sort).asLiveData(Dispatchers.IO)

}