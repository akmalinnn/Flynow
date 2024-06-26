package com.km6.flynow.data.model.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Notification(
    val id: Int,
    val userId: Int,
    val message: String,
    val type: String,
    val isRead: Boolean?,
    val createdAt: String
) : Parcelable
