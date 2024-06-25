package com.km6.flynow.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.km6.flynow.data.repository.PaymentRepository
import com.km6.flynow.data.source.network.model.payment.PaymentRequest

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {

    fun createPayment(bookingId: Int, paymentAmount: Int) = liveData {
        try {
            val paymentRequest = PaymentRequest(bookingId, paymentAmount)
            val response = paymentRepository.createPayment(paymentRequest)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}