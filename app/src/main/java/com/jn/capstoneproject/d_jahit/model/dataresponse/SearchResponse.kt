package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("sellers")
	val sellers: List<SellerProductItem>,

	@field:SerializedName("status")
	val status: String
)

data class SellerProductItem(

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("productPhoto")
	val productPhoto: String,

	@field:SerializedName("price_1")
	val price1: Int,

	@field:SerializedName("price_2")
	val price2: Int,

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("definition")
	val definition: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("updatedAt")
	val updatedAt: Any
)
