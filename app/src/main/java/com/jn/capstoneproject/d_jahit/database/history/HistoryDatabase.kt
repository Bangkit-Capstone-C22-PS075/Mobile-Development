package com.jn.capstoneproject.d_jahit.database.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ProductModel::class],
    exportSchema = false, version = 1
)
abstract class HistoryDatabase  : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryDatabase? =null

        @JvmStatic
        fun getInstance(context: Context): HistoryDatabase{
            if (INSTANCE == null){
                synchronized(HistoryDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history.db"
                    ).build()
                }
            }
            return INSTANCE as HistoryDatabase
        }
    }
}