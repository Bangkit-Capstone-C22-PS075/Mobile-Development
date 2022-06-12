package com.jn.capstoneproject.d_jahit.model.dataresponse

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("products")
	val products: List<ProductsItem>
)


@Parcelize
@Entity(tableName = "produk")
data class ProductsItem(
	@ColumnInfo(name = "idhistory")
	@PrimaryKey(autoGenerate = true)
	val idhistory: Int,

	@field:SerializedName("id")
	@ColumnInfo(name = "id")
	val id: String,

	@field:SerializedName("name")
	@ColumnInfo(name = "name")
	val name: String,

	@field:SerializedName("definition")
	@ColumnInfo(name = "definition")
	val definition: String,

	@field:SerializedName("productPhoto")
	@ColumnInfo(name = "productPhoto")
	val productPhoto: String,
	@field:SerializedName("price1")
	@ColumnInfo(name = "price1")
	val price1: Double,

	@field:SerializedName("price2")
	@ColumnInfo(name = "price2")
	val price2: Double,

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@ColumnInfo(name = "isFavorite")
	var isHistory: Boolean? = false
): Parcelable
data class ProductSellerResponse(

	@field:SerializedName("products")
	val products: List<ProductsItem>
)

