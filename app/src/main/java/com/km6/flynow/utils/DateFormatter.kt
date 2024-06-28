package com.km6.flynow.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String?.formatDate(): String? {
    val originalFormatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val changeFormatDate = SimpleDateFormat("dd-MMMM-yyyy")

    return this?.let { originalFormatDate.parse(it)?.let { date -> changeFormatDate.format(date) } }
}

fun String?.formatTime(): String? {
    val originalFormatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val changeFormatDate = SimpleDateFormat("HH:mm")


    return this?.let { originalFormatDate.parse(it)?.let { date -> changeFormatDate.format(date) } }
}
// Fungsi ekstensi untuk mempermudah pemanggilan
fun String?.toTimeFormat(): String? = this.formatTime()
fun String?.toDateFormat(): String? = this.formatDate()

