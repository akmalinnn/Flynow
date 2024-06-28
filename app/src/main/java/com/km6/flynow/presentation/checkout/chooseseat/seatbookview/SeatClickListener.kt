package com.km6.flynow.presentation.checkout.chooseseat.seatbookview

import android.view.View

interface SeatClickListener {
    fun onAvailableSeatClick(
        selectedIdList: List<Int>,
        view: View,
    )

    fun onBookedSeatClick(view: View)

    fun onReservedSeatClick(view: View)
}
