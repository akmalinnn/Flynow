package com.km6.flynow.data.datasource.seat

import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.data.source.network.model.seat.SeatResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

class SeatDataSourceImpl(private val service: FlynowApiService) : SeatDataSource {
    override suspend fun getSeats(
        flightId: Int,
    ): SeatResponse {
        return service.getSeat(flightId)
    }
}