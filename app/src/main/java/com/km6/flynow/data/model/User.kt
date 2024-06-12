package com.km6.flynow.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val image: String,
    val phoneNumber: String,
    val isVerified: Boolean,
    val deletedAt: String?,
    val createdAt: String,
    val otp: String,
    val updatedAt: String,
)