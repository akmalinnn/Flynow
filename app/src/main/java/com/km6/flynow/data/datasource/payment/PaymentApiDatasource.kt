package com.km6.flynow.data.datasource.payment

import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

class PaymentApiDataSource(private val apiService: FlynowApiService) : PaymentDataSource {

    override suspend fun createPayment(paymentRequest: PaymentRequest): PaymentResponse {
        return apiService.createPayment(paymentRequest)
    }
}
