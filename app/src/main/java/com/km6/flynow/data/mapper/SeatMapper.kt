package com.km6.flynow.data.mapper


import com.km6.flynow.data.source.network.model.history.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData

fun SeatData?.toSeat() =
    SeatData(
        id = this?.id ?: 0,
        seatCode = this?.seatCode.orEmpty(),
        flightId = this?.id ?: 0,
        seatAvailable = this?.seatAvailable ?: false,
    )

fun Collection<SeatData>?.toSeat() =
    this?.map {
        it.toSeat()
    } ?: listOf()
