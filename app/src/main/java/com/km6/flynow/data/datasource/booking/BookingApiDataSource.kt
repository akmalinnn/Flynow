package com.km6.flynow.data.datasource.booking

import com.km6.flynow.data.source.network.model.booking.BookingRequest
import com.km6.flynow.data.source.network.model.booking.BookingResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

class BookingApiDataSource(private val service : FlynowApiService) : BookingDataSource {
    override suspend fun createBooking(bookingRequest: BookingRequest) : BookingResponse {
        return service.createBooking(bookingRequest)
    }
}