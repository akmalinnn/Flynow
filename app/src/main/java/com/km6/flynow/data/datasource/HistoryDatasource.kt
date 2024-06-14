package com.km6.flynow.data.datasource

import com.km6.flynow.data.model.HistoryItem

class HistoryDatasource {

    interface HistoryDataSource {
        suspend fun getHistory(): List<HistoryItem>
    }

    class HistoryDataSourceImpl : HistoryDataSource {
        override suspend fun getHistory(): List<HistoryItem> {

            TODO("Not implemented")
        }
    }
}