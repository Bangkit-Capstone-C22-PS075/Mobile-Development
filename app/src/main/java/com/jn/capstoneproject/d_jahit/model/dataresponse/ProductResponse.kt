package com.jn.capstoneproject.d_jahit.model.dataresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("products")
	val products: List<ProductsItem>
)


@Parcelize
data class ProductsItem(

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("definition")
	val definition: String,

	@field:SerializedName("price1")
	val price1: Double,

	@field:SerializedName("price2")
	val price2: Double,

	@field:SerializedName("id")
	val id: String
): Parcelable
