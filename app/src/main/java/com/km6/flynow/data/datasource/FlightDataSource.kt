package com.km6.flynow.data.datasource

import com.km6.flynow.data.source.network.model.flight.FlightResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


interface FlightDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun searchFlight(da : String?, aa : String?, dd : String?, rd : String?, adult : String?, child : String?, baby : String?, clas : String?, sort : String?,) : FlightResponse
    suspend fun getFlight() : FlightResponse
}

class FlightApiDataSource(
    private val service: FlynowApiService,
) : FlightDataSource{
    override suspend fun getFlight(): FlightResponse {
        return service.getFlight()
    }

    override suspend fun searchFlight(
        da: String?,
        aa: String?,
        dd: String?,
        rd: String?,
        adult: String?,
        child: String?,
        baby: String?,
        clas: String?,
        sort: String?
    ): FlightResponse {
        return service.searchFlight(da, aa, dd, rd, adult, child, baby, clas, sort)
    }
}