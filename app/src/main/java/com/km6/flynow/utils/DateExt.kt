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

fun String.getFormattedDate(format: String = "yyyy-MM-dd"): String {
    val timeInMillis = this.toLongOrNull() ?: return "Invalid date"
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun getIsoFormattedDate(year: Int, month: Int, dayOfMonth: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(calendar.time)
}

fun Long.getIsoFormattedDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}


fun getTimestamp(year: Int, month: Int, dayOfMonth: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun String.toCustomDateFormat(): String? {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        val date: Date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        null
    }
}

// Function to convert ISO 8601 string to custom time format
fun String.toCustomTimeFormat(): String? {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("HH:mm", Locale("in", "ID"))
        val date: Date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        null
    }

}

fun calculateEstimatedTime(departureTime: String, arrivalTime: String): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC") // Adjust timezone if necessary

    try {
        val departureDate = format.parse(departureTime)
        val arrivalDate = format.parse(arrivalTime)

        val diff = arrivalDate.time - departureDate.time

        val hours = diff / (60 * 60 * 1000)
        val minutes = (diff % (60 * 60 * 1000)) / (60 * 1000)

        val hoursString = if (hours > 0) "$hours h" else ""
        val minutesString = if (minutes > 0) " $minutes m" else ""

        return "$hoursString$minutesString".trim()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}