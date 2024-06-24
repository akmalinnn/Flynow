package com.km6.flynow.data.datasource.history


import com.km6.flynow.data.source.network.model.history.HistoryItemResponse
import com.km6.flynow.data.source.network.service.FlynowApiService


class HistoryApiDataSource (private val service: FlynowApiService) : HistoryDataSource {
    override suspend fun getHistory(): HistoryItemResponse {
        return  service.getHistory()
    }
}