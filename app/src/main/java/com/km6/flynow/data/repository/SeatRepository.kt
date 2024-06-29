package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.seat.SeatDataSource
import com.km6.flynow.data.mapper.toSeat
import com.km6.flynow.data.source.network.model.history.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface SeatRepository {
    fun getSeat(
        id: Int,
    ): Flow<ResultWrapper<List<SeatData>>>
}

class SeatRepositoryImpl(
    private val dataSource: SeatDataSource,
) : SeatRepository {
    override fun getSeat(
        id: Int,
    ): Flow<ResultWrapper<List<SeatData>>> {
        return proceedFlow {
            dataSource.getSeat(id).data.toSeat()
        }
    }
}
