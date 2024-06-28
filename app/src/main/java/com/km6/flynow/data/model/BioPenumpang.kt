package com.km6.flynow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BioPenumpangList(
    var list: List<BioPenumpang>,
) : Parcelable

@Parcelize
data class BioPenumpang(
    val type: String, // "Adult", "Child", "Baby"
    var firstName: String = "",
//    var lastName: String? = null,
    var dateOfBirth: String = "",
    var nationality: String = "",
    var docNumber: String = "",
    var docType : String = "",
    var expiryDate : String = "",
    var issuingCountry: String = ""
) : Parcelable