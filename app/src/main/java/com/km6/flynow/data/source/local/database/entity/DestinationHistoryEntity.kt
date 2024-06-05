package com.km6.flynow.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinationHistory")
data class DestinationHistoryEntity (
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "destination_name")
    var destinationName: String,
)