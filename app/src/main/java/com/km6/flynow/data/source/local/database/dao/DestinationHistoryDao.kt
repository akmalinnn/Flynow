package com.km6.flynow.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationHistoryDao {
    @Query("SELECT * FROM DESTINATIONHISTORY")
    fun getAllDestinationHistory(): Flow<List<DestinationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: DestinationHistoryEntity): Long

    @Delete
    suspend fun deleteDestination(destination: DestinationHistoryEntity): Int

    @Query("DELETE FROM DESTINATIONHISTORY")
    suspend fun deleteAllDestinationHistory()
}
