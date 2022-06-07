package com.jn.capstoneproject.d_jahit.database.history

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HistoryDao {

    @Query("SELECT * FROM product ")
    fun getHistoryListProduct(): LiveData<List<ProductModel>>

    @Query("SELECT * FROM product WHERE id = :id ")
    suspend fun getDetailHistory(id:String): ProductModel


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryProduct(product: ProductModel)

    @Delete
    suspend fun deleteHistoryProduct(product: ProductModel)
}