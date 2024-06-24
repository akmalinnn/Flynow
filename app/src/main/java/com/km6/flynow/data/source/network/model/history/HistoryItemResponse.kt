package com.km6.flynow.data.source.network.model.history

data class HistoryItemResponse(
    val message: String,
    val data: List<HistoryItem>
)