package com.jn.capstoneproject.d_jahit.database.history

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem


@Dao
interface HistoryDao {

    @Query("SELECT * FROM produk ")
    fun getHistoryListProduct(): List<ProductsItem>

    @Query("SELECT * FROM produk WHERE id = :id ")
    suspend fun getDetailHistory(id:String): ProductsItem


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryProduct(product: ProductsItem)

    @Delete
    suspend fun deleteHistoryProduct(product: ProductsItem)
}