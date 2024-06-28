package com.km6.flynow.presentation.checkout.chooseseat.seatbookview

import android.view.View

interface SeatLongClickListener {
    fun onAvailableSeatLongClick(view: View)

    fun onBookedSeatLongClick(view: View)

    fun onReservedSeatLongClick(view: View)
}