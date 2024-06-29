package com.km6.flynow.data.mapper

import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Passenger

fun BioPenumpang?.toPassenger() =
    this?.let {
        Passenger(
            name = it.firstName + it.lastName,
            dateOfBirth = it.dateOfBirth,
            nationality = it.nationality,
            docType = it.docType,
            docNumber = it.docNumber,
            issuingCountry = it.issuingCountry,
            expiryDate = it.expiryDate,
            passengerType = it.type
        )
    }
