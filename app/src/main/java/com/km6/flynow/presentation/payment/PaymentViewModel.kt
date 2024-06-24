package com.km6.flynow.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.model.Payment
import com.km6.flynow.data.repository.PaymentRepository
import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {


    private val _paymentState = MutableLiveData<ResultWrapper<Boolean>>()
    val paymentState: LiveData<ResultWrapper<Boolean>>
        get() = _paymentState


    fun createPayment(bookingId: Int, paymentAmount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.createPayment(bookingId, paymentAmount).collect {
                _paymentState.postValue(it)

            }
        }
    }
}
