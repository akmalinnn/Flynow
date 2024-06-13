package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.source.network.model.flight.Data

fun Data?.toFlight() =
    Flight(
        id = this?.id?: 0,
        flightCode = this?.flightCode.orEmpty(),
        terminal = this?.terminal.orEmpty(),
        departureAirportId = this?.departureAirportId?:0,
        arrivalAirportId = this?.arrivalAirportId?:0,
        airlineId = this?.airlineId?:0,
        departureTime = this?.departureTime.orEmpty(),
        arrivalTime = this?.arrivalTime.orEmpty(),
        price = this?.price ?: 0.0,
        flightClass = this?.flightClass.orEmpty(),
        information = this?.information.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        depatureid = this?.departureAirport?.id?:0,
        depatureairportCode = this?.departureAirport?.airportCode.orEmpty(),
        depatureairportName = this?.departureAirport?.airportName.orEmpty(),
        depaturecity = this?.departureAirport?.city.orEmpty(),
        depaturecountry = this?.departureAirport?.country.orEmpty(),
        depaturedeletedAt = this?.departureAirport?.deletedAt.orEmpty(),
        arrivalid = this?.arrivalAirport?.id?:0,
        arrivalairportName = this?.arrivalAirport?.airportName.orEmpty(),
        arrivalairportCode = this?.arrivalAirport?.airportCode.orEmpty(),
        arrivaldeletedAt = this?.arrivalAirport?.deletedAt.orEmpty(),
        arrivalcountry = this?.arrivalAirport?.country.orEmpty(),
        arrivalcity = this?.arrivalAirport?.city.orEmpty(),
        airlineid = this?.airline?.id?:0,
        airlineCode = this?.airline?.airlineCode.orEmpty(),
        airlineName = this?.airline?.airlineName.orEmpty(),
        deletedAt = this?.deletedAt.orEmpty(),
        image = this?.airline?.image.orEmpty()
    )

fun Collection<Data>?.toFlights() = this?.map { it.toFlight() } ?: listOf()
