package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("products")
	val products: List<ProductsItem>
)

data class ProductsItem(

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("definition")
	val definition: String,

	@field:SerializedName("id")
	val id: String
)
