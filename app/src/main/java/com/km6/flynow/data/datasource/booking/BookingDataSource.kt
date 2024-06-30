package com.km6.flynow.data.datasource.booking

import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.source.network.model.booking.BookingResponse

interface BookingDataSource {
    suspend fun createBooking(bookingRequest: Booking): BookingResponse
}
