package com.km6.flynow.data.repository

import com.km6.flynow.data.datasource.destination_history.DestinationHistoryDataSource
import com.km6.flynow.data.mapper.toDestinationHistoryEntity
import com.km6.flynow.data.mapper.toDestinationHistoryList
import com.km6.flynow.data.model.DestinationHistory
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceed
import com.km6.flynow.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


interface DestinationHistoryRepository {
    fun getUserDestinationHistoryData() : Flow<ResultWrapper<List<DestinationHistory>>>

    fun createDestination(
        destinationHistory: DestinationHistory
    ) : Flow<ResultWrapper<Boolean>>

    fun deleteDestination(item: DestinationHistory): Flow<ResultWrapper<Boolean>>

    fun deleteAllDestinationHistory() : Flow<ResultWrapper<Boolean>>
}

class DestinationHistoryRepositoryImpl(private val destinationHistoryDataSource: DestinationHistoryDataSource) : DestinationHistoryRepository {
    override fun getUserDestinationHistoryData(): Flow<ResultWrapper<List<DestinationHistory>>> {
        return destinationHistoryDataSource.getAllDestinationHistory()
            .map {
                proceed {
                    val result = it.toDestinationHistoryList()
                    result
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }

    override fun createDestination(destinationHistory: DestinationHistory): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
                val affectedRow =
                    destinationHistoryDataSource.insertDestination(
                        DestinationHistoryEntity(
                            destinationName = destinationHistory.destinationName
                        )
                    )
                affectedRow > 0
            }
        }

    override fun deleteDestination(item: DestinationHistory): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { destinationHistoryDataSource.deleteDestination(item.toDestinationHistoryEntity()) > 0 }
    }

    override fun deleteAllDestinationHistory(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            destinationHistoryDataSource.deleteAllDestinationHistory()
            true
        }
    }
}