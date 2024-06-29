package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search(
    val da: Airport? = null,
    val aa: Airport? = null,
    val dd: String? = null,
    val rd: String? = null,
    val adult: Int = 0,
    val child: Int = 0,
    val baby: Int = 0,
    val clas: SeatClass? = null,
    var totalPassenger : Int = 0,
    val roundTrip: Boolean = false
): Parcelable
