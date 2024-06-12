package com.km6.flynow.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
    return "$dayOfMonth ${getMonthName(month)} $year"
}

fun getMonthName(month: Int): String {
    val monthNames = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
    return monthNames[month]
}

fun Long.getFormattedDate() : String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return getFormattedDate(year, month, dayOfMonth)
}

fun getIsoFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(calendar.time)
}

fun Long.getIsoFormattedDate() : String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return getIsoFormattedDate(year, month, dayOfMonth)
}

fun getTimestamp(year: Int, month: Int, dayOfMonth: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}