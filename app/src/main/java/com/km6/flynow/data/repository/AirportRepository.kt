package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.airport.AirportDataSource
import com.km6.flynow.data.mapper.toAirport
import com.km6.flynow.data.model.Airport
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun searchAirport(keyword: String? = null) : Flow<ResultWrapper<List<Airport>>>

}

class AirportRepositoryImpl (private val dataSource: AirportDataSource) : AirportRepository {
    override fun searchAirport(keyword: String?): Flow<ResultWrapper<List<Airport>>> {
        return proceedFlow {
            dataSource.searchAirport(keyword).data.toAirport()
        }
    }
}