package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.source.network.model.history.HistoryItem


fun HistoryItem.toHistory() =
    History(
        id = this.id,
        bookingCode = this.bookingCode,
        departureFlightId = this.departureFlightId,
        returnFlightId = this.returnFlightId.orEmpty(),
        userId = this.userId,
        numAdults = this.numAdults,
        numChildren = this.numChildren,
        numBabies = this.numBabies,
        departureTime = this.flight.departure.departureTime,
        arrivalTime = this.flight.departure.arrivalTime,
        price = this.flight.departure.price,
        flightClass = this.flight.departure.flightClass,
        flightCode = this.flight.departure.flightCode,

        airportDepartureCity = this.flight.departure.departureAirport.city,
        airportArrivalCity = this.flight.departure.arrivalAirport.city,
        airportDepartureName = this.flight.departure.departureAirport.airportName,
        airportArrivalName = this.flight.departure.arrivalAirport.airportName,
        airlineDepartureName = this.flight.departure.airline?.airlineName.orEmpty(),
        airlineLogo = this.flight.departure.airline?.image.orEmpty(),
        passengerName = this.details.departure.map { it.passenger.name },
        passengerSeat = this.details.departure.map { it.seat.seatCode.orEmpty() },

        passengerSeatReturn = this.details.returnDetails?.map { it?.seat?.seatCode.orEmpty() },


        departureTimeReturn = this.flight.returnFlight?.departureTime.orEmpty(),
        arrivalTimeReturn = this.flight.returnFlight?.arrivalTime.orEmpty(),
        priceReturn = this.flight.returnFlight?.price ?: 0,
        flightCodeReturn = this.flight.returnFlight?.flightCode.orEmpty(),
        flightClassReturn = this.flight.returnFlight?.flightClass.orEmpty(),
        airportDepartureCityReturn = this.flight.returnFlight?.departureAirport?.city.orEmpty(),
        airportArrivalCityReturn = this.flight.returnFlight?.arrivalAirport?.city.orEmpty(),
        airportDepartureNameReturn = this.flight.returnFlight?.departureAirport?.airportName.orEmpty(),
        airportArrivalNameReturn = this.flight.returnFlight?.arrivalAirport?.airportName.orEmpty(),
        airlineDepartureNameReturn = this.flight.returnFlight?.airline?.airlineName.orEmpty(),
        airlineLogoReturn = this.flight.returnFlight?.airline?.image.orEmpty(),
        paymentStatus = this.payment?.paymentStatus.orEmpty(),
        snapUrl = this.payment?.snapRedirectUrl.orEmpty(),
        flightInformationReturn = this.flight.returnFlight?.information.orEmpty(),
        flightInformation = this.flight.departure.information.orEmpty(),

    )

fun Collection<HistoryItem>.toHistory() =
    this.map {
        it.toHistory()
    } ?: listOf()