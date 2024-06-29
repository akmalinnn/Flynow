package com.km6.flynow.data.datasource.seat

import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.data.source.network.model.seat.SeatResponse

interface SeatDataSource {
    suspend fun getSeats(
        flightId: Int
    ): SeatResponse
}