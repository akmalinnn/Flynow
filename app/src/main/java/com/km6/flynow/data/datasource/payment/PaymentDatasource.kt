package com.km6.flynow.data.datasource.payment

import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse

interface PaymentDataSource {
    suspend fun createPayment(paymentRequest: PaymentRequest): PaymentResponse
}
