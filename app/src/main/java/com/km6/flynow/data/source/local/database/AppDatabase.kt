package com.km6.flynow.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.km6.flynow.data.source.local.database.dao.DestinationHistoryDao
import com.km6.flynow.data.source.local.database.entity.DestinationHistoryEntity


@Database(
    entities = [DestinationHistoryEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun destinationHistoryDao(): DestinationHistoryDao

    companion object {
        private const val DB_NAME = "Flynow.db"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME,
            ).fallbackToDestructiveMigration().build()
        }
    }
}
