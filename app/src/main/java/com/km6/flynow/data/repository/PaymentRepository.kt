package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.payment.PaymentDataSource
import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse

class PaymentRepository(private val paymentDataSource: PaymentDataSource) {

    suspend fun createPayment(paymentRequest: PaymentRequest): PaymentResponse {
        return paymentDataSource.createPayment(paymentRequest)
    }
}
