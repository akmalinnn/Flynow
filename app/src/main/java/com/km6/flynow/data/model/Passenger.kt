package com.km6.flynow.data.model

data class Passenger(
    val name : String,
    val dateOfBirth : String,
    val nationality : String,
    val docType : String,
    val docNumber : String,
    val issuingCountry : String,
    val expiryDate : String,
    val passengerType : String
)
