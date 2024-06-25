package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter (
    val category: String,
    val desc: String
) : Parcelable