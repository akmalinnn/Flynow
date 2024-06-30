package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.booking.BookingDataSource
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.source.network.model.booking.BookingResponse

class BookingRepository(private val bookingDataSource: BookingDataSource) {

     suspend fun createBooking(bookingRequest: Booking): BookingResponse {
        return bookingDataSource.createBooking(bookingRequest)
    }
}
