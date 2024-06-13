package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.FlightDataSource
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.mapper.toFlights
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getFlight(): Flow<ResultWrapper<List<Flight>>>
}

class FlightRepositoryImpl(
    private val dataSource: FlightDataSource,
) : FlightRepository{
    override fun getFlight(): Flow<ResultWrapper<List<Flight>>> {
        return proceedFlow { dataSource.getFlight().data.toFlights() }
    }
}
