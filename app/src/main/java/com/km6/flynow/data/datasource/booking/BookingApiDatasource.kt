package com.km6.flynow.data.datasource.booking

import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.source.network.model.booking.BookingResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

class BookingApiDataSource(private val apiService: FlynowApiService) : BookingDataSource {

    override suspend fun createBooking(bookingRequest: Booking): BookingResponse {
        return apiService.createBooking(bookingRequest)
    }
}
