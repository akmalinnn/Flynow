package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.seat.SeatDataSource
import com.km6.flynow.data.mapper.toSeat
import com.km6.flynow.data.model.Seat
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface SeatRepository {
    fun getSeats(
        flightId: Int
    ): Flow<ResultWrapper<List<Seat>>>
}

class SeatRepositoryImpl(private val dataSource: SeatDataSource) : SeatRepository {
    override fun getSeats(
        flightId: Int
    ): Flow<ResultWrapper<List<Seat>>> {
        return proceedFlow {
            dataSource.getSeats(flightId).toSeat()
        }.map {
            if (it.payload?.isNotEmpty() == true) return@map it
            ResultWrapper.Empty(it.payload)
        }.catch{
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(1000)
        }
    }
}