package com.km6.flynow.data.datasource.seat

import com.km6.flynow.data.model.Response
import com.km6.flynow.data.source.network.model.seat.SeatData


interface SeatDataSource {
    suspend fun getSeat(id: Int): Response<List<SeatData>?>
}
