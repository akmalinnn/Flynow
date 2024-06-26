package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.FavoriteFlight
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.source.network.model.favorite_flight.FavoriteFlightItem
import com.km6.flynow.data.source.network.model.history.HistoryItem

fun FavoriteFlightItem.toFavoriteFlight() =
    FavoriteFlight(
        id = this.id,
        flightId = this.flightId,
        airlineId = this.airlineId,
        airline = this.airline,
        departureAirportId = this.departureAirportId,
        departureAirport = this.departureAirport,
        departureCity = this.departureCity,
        arrivalAirportId = this.arrivalAirportId,
        arrivalAirport = this.arrivalAirport,
        arrivalCity = this.arrivalCity,
        departureTime = this.departureTime,
        price = this.price,
        discount = this.discount,
        description = this.description,
        isFavorite = this.isFavorite,
        image = this.image,
    )

fun Collection<FavoriteFlightItem>.toFavoriteFlightList()=
    this.map {
        it.toFavoriteFlight()
    }
