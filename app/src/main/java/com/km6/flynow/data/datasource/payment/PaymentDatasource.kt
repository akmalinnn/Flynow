package com.km6.flynow.data.datasource.payment

import com.km6.flynow.data.datasource.AuthDataSource
import com.km6.flynow.data.model.Payment
import com.km6.flynow.data.source.network.model.otp.VerifyOtpRequest
import com.km6.flynow.data.source.network.model.otp.VerifyOtpResponse
import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse
import com.km6.flynow.data.source.network.service.FlynowApiService

interface PaymentDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun createPayment(bookingId: Int,paymentAmount : Int ): PaymentResponse

}
class PaymentDataSourceImpl(private val service: FlynowApiService) : PaymentDataSource {
    override suspend fun createPayment(bookingId: Int, paymentAmount: Int): PaymentResponse {
        val requestBody = PaymentRequest(bookingId, paymentAmount)
        return service.createPayment(requestBody)
    }
}

