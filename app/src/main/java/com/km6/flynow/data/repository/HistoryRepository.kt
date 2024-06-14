package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.HistoryDatasource
import com.km6.flynow.data.model.HistoryItem

class HistoryRepository {

    interface HistoryRepository {
        suspend fun getHistory(): List<HistoryItem>
    }

    class HistoryRepositoryImpl(private val historyDataSource: HistoryDatasource.HistoryDataSource) : HistoryRepository {
        override suspend fun getHistory(): List<HistoryItem> {
            return historyDataSource.getHistory()
        }
    }
}