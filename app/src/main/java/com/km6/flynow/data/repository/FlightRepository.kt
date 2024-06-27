
package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.FlightDataSource
import com.km6.flynow.data.mapper.toDepartureFlights
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.mapper.toReturnFlights
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun searchFlight(da : String?, aa : String?, dd : String?, rd : String?, adult : String?, child : String?, baby : String?, clas : String?, sort : String?): Flow<ResultWrapper<Pair<List<Flight>,List<Flight>>>>
}

class FlightRepositoryImpl(
    private val dataSource: FlightDataSource,
) : FlightRepository{

    override fun searchFlight(
        da: String?,
        aa: String?,
        dd: String?,
        rd: String?,
        adult: String?,
        child: String?,
        baby: String?,
        clas: String?,
        sort: String?
    ): Flow<ResultWrapper<Pair<List<Flight>,List<Flight>>>>  {
        return proceedFlow {
            val departureFlight = dataSource.searchFlight(da, aa, dd, rd, adult, child, baby, clas, sort).data?.departureFlights.toDepartureFlights()
            val returnFlight = dataSource.searchFlight(da, aa, dd, rd, adult, child, baby, clas, sort).data?.returnFlights.toReturnFlights()
            Pair(departureFlight, returnFlight)
        }
    }
}

