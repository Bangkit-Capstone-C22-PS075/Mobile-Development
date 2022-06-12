package com.jn.capstoneproject.d_jahit.database.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import java.util.concurrent.locks.Lock


@Database(
    entities = [ProductsItem::class],
    exportSchema = true, version = 2
)
abstract class HistoryDatabase  : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        private var instance: HistoryDatabase? =null
        private val Lock = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(Lock){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            HistoryDatabase::class.java,
        "histroy.db"
        ).fallbackToDestructiveMigration()
            .build()
//        @JvmStatic
//        fun getInstance(context: Context): HistoryDatabase{
//            if (INSTANCE == null){
//                synchronized(HistoryDatabase::class.java){
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        HistoryDatabase::class.java,
//                        "history.db"
//                    ).build()
//                }
//            }
//            return INSTANCE as HistoryDatabase
//        }
    }
}