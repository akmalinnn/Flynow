package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.DetailFlightDataSource
import com.km6.flynow.data.mapper.toDepartureFlights
import com.km6.flynow.data.mapper.toReturnFlights
import com.km6.flynow.data.model.Flight
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface DetailFlightRepository {
    fun getFlight(id: Int): Flow<ResultWrapper<List<Flight>>>
}
class DetailFlightRepositoryImpl( private val dataSource: DetailFlightDataSource) : DetailFlightRepository {
    override fun getFlight(id: Int): Flow<ResultWrapper<List<Flight>>> {
        return proceedFlow {
            dataSource.detailFlight(id).data?.departureFlights.toDepartureFlights() ; dataSource.detailFlight(id).data?.returnFlights.toReturnFlights()
        }
    }
}