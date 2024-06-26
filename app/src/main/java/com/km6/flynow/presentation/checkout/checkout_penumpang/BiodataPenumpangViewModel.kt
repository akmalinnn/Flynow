package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.km6.flynow.data.source.local.pref.UserPreference

class BiodataPenumpangViewModel(
    extras: Bundle?,
    private val preference: UserPreference,
) : ViewModel() {
    val userId = preference.getUserID()
}