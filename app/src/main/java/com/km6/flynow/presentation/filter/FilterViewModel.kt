package com.km6.flynow.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.Filter
import com.km6.flynow.data.model.SeatClass

class FilterViewModel : ViewModel() {
    private val filter = MutableLiveData<List<Filter>>()
    val options: LiveData<List<Filter>> get() = filter

    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> get() = _selectedPosition

    init {
        // Initialize the options list
        filter.value = listOf(
            Filter("Harga", "Termurah"),
            Filter("Durasi", "Terpendek"),
            Filter("Keberangkatan", "Paling Awal"),
            Filter("Keberangkatan", "Paling Akhir"),
            Filter("Kedatangan", "Paling Awal"),
            Filter("Kedatangan", "Paling Akhir"),
            )
        _selectedPosition.value = -1
    }

    fun selectPosition(position: Int) {
        _selectedPosition.value = position
    }
}