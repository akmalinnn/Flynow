package com.km6.flynow.presentation.choose_seat_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.model.SeatClass

class ChooseSeatClassViewModel : ViewModel() {
    private val seatClass = MutableLiveData<List<SeatClass>>()
    val options: LiveData<List<SeatClass>> get() = seatClass

    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> get() = _selectedPosition

    init {
        // Initialize the options list
        seatClass.value = listOf(
            SeatClass("Economy", " IDR 1.000.000"),
            SeatClass("Business", "IDR 10.000.000"),
            SeatClass("First Class", "IDR 20.620.000")
        )
        _selectedPosition.value = -1
    }

    fun selectPosition(position: Int) {
        _selectedPosition.value = position
    }
}