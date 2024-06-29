package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search(
    val da: Airport? = null,
    val aa: Airport? = null,
    val dd: String? = null,
    val rd: String? = null,
    val adult: Int,
    val child: Int,
    val baby: Int,
    val clas: SeatClass? = null,
    var totalPassenger : Int,
    val roundTrip: Boolean
): Parcelable
