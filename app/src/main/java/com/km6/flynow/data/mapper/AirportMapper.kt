package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.source.network.model.airport.AirportItemResponse

fun AirportItemResponse?.toAirport() =
    Airport(
        id = this?.id ?: 0 ,
        airportCode = this?.airportCode.orEmpty(),
        airportName = this?.airportName.orEmpty(),
        city = this?.city.orEmpty(),
        country = this?.country.orEmpty(),
        deletedAt = this?.deletedAt.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty()
    )

fun Collection<AirportItemResponse>?.toAirport() =
    this?.map {
        it.toAirport()
    } ?: listOf()

