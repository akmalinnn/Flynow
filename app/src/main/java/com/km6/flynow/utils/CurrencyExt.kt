package com.km6.flynow.utils

import java.text.NumberFormat
import java.util.Locale

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

fun Double?.doubleToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Double?.toDollarFormat() = this.doubleToCurrency("en", "US")

fun String?.stringToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun String?.toDollarFormat() = this.stringToCurrency("en", "US")

// New function for Int type
fun Int?.intToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

// Function to convert Int to IDR format and change symbol to IDR
fun Int?.toIDRFormat(): String? {
    return try {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        val formattedValue = numberFormat.format(this)
        formattedValue.replace("Rp", "IDR ")
        formattedValue.replace(",00", "")
    } catch (e: Exception) {
        null
    }
}


