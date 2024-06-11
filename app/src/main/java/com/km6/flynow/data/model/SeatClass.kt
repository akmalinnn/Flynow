package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeatClass (
    val name: String,
    val price: String
) : Parcelable