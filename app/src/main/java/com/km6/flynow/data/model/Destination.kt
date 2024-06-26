package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Destination (
    var id: String? = UUID.randomUUID().toString(),
    var imgUrl: String,
    var cityDepature: String,
    var cityDestination: String,
    var maskapai: String,
    var date: String,
    var price: Double,
) : Parcelable