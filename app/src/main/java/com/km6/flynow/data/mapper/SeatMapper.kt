package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.ListSeat
import com.km6.flynow.data.model.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.data.source.network.model.seat.SeatResponse

fun SeatResponse.toSeat() =
    this.data?.map {
        Seat(
            id = it.id,
            seatCode = it.seatCode.toString(),
            seatAvailable = it.seatAvailable,
            flightId = it.flightId,
            deletedAt = it.deletedAt,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    } ?: listOf()

fun List<SeatData>?.toSeatsString(): String {
    var seatMap = ""
    var line = ""
    for (i in 0 until this?.size!!) {
        val seat = this[i]
        val seatAvailable = seat.seatAvailable
        val seatChar = getSeatChar(seatAvailable)
        line += seatChar
        if (line.length == 3) {
            line += '_'
        }
        if (line.length == 7 || i == this.size - 1) {
            seatMap += "/$line"
            line = ""
        }
    }
    return seatMap
}


fun List<Seat>?.toTitleString(): List<String> {
    val seatsList = this
    val title = mutableListOf<String>()
    for (i in 0 until seatsList?.size!! step 6) {
        title.add("/")
        title.add(seatsList[i].seatCode)
        title.add(seatsList[i + 1].seatCode)
        title.add(seatsList[i + 2].seatCode)
        title.add("_")
        title.add(seatsList[i + 3].seatCode)
        title.add(seatsList[i + 4].seatCode)
        title.add(seatsList[i + 5].seatCode)
    }
    return title
}

fun getSeatChar(seatAvailable: Boolean): Char {
    return if (seatAvailable) 'U' else 'A'
}

fun List<Seat>?.toListInt() =
    this?.map {
        it.id
    } ?: listOf()

fun List<Seat>.toListSeat() =
    ListSeat(
        seats = this
    )