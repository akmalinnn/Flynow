package com.km6.flynow.data.datasource.history

import com.km6.flynow.data.source.network.model.history.HistoryItemResponse


interface HistoryDataSource {
    suspend fun getHistory(): HistoryItemResponse
}
