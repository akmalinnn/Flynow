package com.km6.flynow.presentation.history.historydetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.repository.PaymentRepository
import com.km6.flynow.data.source.network.model.payment.PaymentRequest
import com.km6.flynow.data.source.network.model.payment.PaymentResponse
import com.km6.flynow.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryDetailViewModel(
    private val extras: Bundle?,
) : ViewModel() {

    private val _historyItem = MutableLiveData<History?>()
    val historyItem: LiveData<History?> get() = _historyItem

    private val _totalAdultPrice = MutableLiveData<Int>()
    val totalAdultPrice: LiveData<Int> get() = _totalAdultPrice

    private val _totalChildrenPrice = MutableLiveData<Int>()
    val totalChildrenPrice: LiveData<Int> get() = _totalChildrenPrice

    private val _totalBabyPrice = MutableLiveData<Int>()
    val totalBabyPrice: LiveData<Int> get() = _totalBabyPrice

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> get() = _totalPrice

    init {
        val history = extras?.getParcelable<History>(HistoryDetailActivity.EXTRA_HISTORY_ITEM)
        _historyItem.value = history
        calculateTotalPrice(history)
    }

    private fun calculateTotalPrice(history: History?) {
        history?.let {
            val price = it.price
            val priceReturn = it.priceReturn ?: 0

            val totalAdults = (it.numAdults * price) + (it.numAdults * priceReturn)
            val totalChildren = (it.numChildren * price) + (it.numChildren * priceReturn)
            val totalBaby = (it.numBabies * price * 0) + (it.numBabies * priceReturn * 0)

            val total = totalAdults + totalChildren + totalBaby

            _totalAdultPrice.value = totalAdults
            _totalChildrenPrice.value = totalChildren
            _totalBabyPrice.value = totalBaby
            _totalPrice.value = total
        } ?: run {
            _totalAdultPrice.value = 0
            _totalChildrenPrice.value = 0
            _totalBabyPrice.value = 0
            _totalPrice.value = 0

        }
    }

}
