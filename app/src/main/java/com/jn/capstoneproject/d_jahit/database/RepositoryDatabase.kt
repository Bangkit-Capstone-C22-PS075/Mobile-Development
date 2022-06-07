package com.jn.capstoneproject.d_jahit.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.jn.capstoneproject.d_jahit.database.history.HistoryDao
import com.jn.capstoneproject.d_jahit.database.history.HistoryDatabase
import com.jn.capstoneproject.d_jahit.database.history.ProductModel

class RepositoryDatabase(application: Application) {
    private val dao: HistoryDao

    init {
        val database: HistoryDatabase= HistoryDatabase.getInstance(application)
        dao= database.historyDao()

    }

    suspend fun  insertHistory(history: ProductModel) = dao.insertHistoryProduct(history)
    suspend fun deleteHistory(history: ProductModel) = dao.deleteHistoryProduct(history)
    fun getHistoryList(): LiveData<List<ProductModel>> = dao.getHistoryListProduct()
}