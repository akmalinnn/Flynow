// PaymentRepository.kt
package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.payment.PaymentDataSource
import com.km6.flynow.data.model.Payment
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse
import com.km6.flynow.data.source.network.service.FlynowApiService
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun createPayment(bookingId: Int, paymentAmount : Int ): Flow<ResultWrapper<Boolean>>
}


class PaymentRepositoryImpl(
    private val dataSource: PaymentDataSource
) : PaymentRepository {

    override suspend fun createPayment(bookingId: Int,paymentAmount : Int ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val response = dataSource.createPayment(bookingId, paymentAmount)
            if (response.message == "success create payment") {
                true
            } else {
                throw Exception(response.message)
            }
        }
    }
}