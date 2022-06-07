package com.jn.capstoneproject.d_jahit.database.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class ProductModel(


    @field:SerializedName("insertedAt")
    val insertedAt: String,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @field:SerializedName("definition")
    @ColumnInfo(name = "definition")
    val definition: String,

    @field:SerializedName("price1")
    @ColumnInfo(name = "price1")
    val price1: Double,

    @field:SerializedName("price2")
    @ColumnInfo(name = "price2")
    val price2: Double,

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "isFavorite")
    var isHistory: Boolean? = false
)
