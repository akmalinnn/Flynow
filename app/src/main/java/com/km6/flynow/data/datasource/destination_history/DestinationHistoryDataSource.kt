package com.km6.flynow.data.datasource.destination_history

import com.km6.flynow.data.source.local.database.dao.DestinationHistoryDao
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity
import kotlinx.coroutines.flow.Flow

interface DestinationHistoryDataSource {

    fun getAllDestinationHistory(): Flow<List<DestinationHistoryEntity>>

    suspend fun insertDestination(destinationHistory: DestinationHistoryEntity): Long

    suspend fun deleteDestination(destinationHistory: DestinationHistoryEntity): Int

    suspend fun deleteAllDestinationHistory()
}

class DestinationHistoryDatabaseDataSource(
    private val dao:DestinationHistoryDao,
): DestinationHistoryDataSource {
    override fun getAllDestinationHistory(): Flow<List<DestinationHistoryEntity>> = dao.getAllDestinationHistory()

    override suspend fun insertDestination(destinationHistory: DestinationHistoryEntity): Long = dao.insertDestination(destinationHistory)


    override suspend fun deleteDestination(destinationHistory: DestinationHistoryEntity): Int = dao.deleteDestination(destinationHistory)

    override suspend fun deleteAllDestinationHistory() = dao.deleteAllDestinationHistory()

}