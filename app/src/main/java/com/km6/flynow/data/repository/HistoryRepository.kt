package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.history.HistoryDataSource
import com.km6.flynow.data.mapper.toHistory
import com.km6.flynow.data.model.history.History
import com.km6.flynow.data.source.network.model.history.HistoryItemResponse
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface HistoryRepository {
    fun getHistory(): Flow<ResultWrapper<List<History>>>
}

class HistoryRepositoryImpl(private val historyDataSource: HistoryDataSource) : HistoryRepository {
    override fun getHistory(): Flow<ResultWrapper<List<History>>> {
        return proceedFlow {
            historyDataSource.getHistory().data.toHistory()
        }
    }
}
