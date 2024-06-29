package com.km6.flynow.data.datasource.booking

import com.km6.flynow.data.source.network.model.booking.BookingRequest
import com.km6.flynow.data.source.network.model.booking.BookingResponse

interface BookingDataSource {
    suspend fun createBooking(
        bookingRequest: BookingRequest
    ): BookingResponse
}