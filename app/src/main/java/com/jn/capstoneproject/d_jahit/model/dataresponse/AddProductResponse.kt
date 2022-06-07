package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class AddProductResponse(

	@field:SerializedName("data")
	val data: DataProduct? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataProduct(

	@field:SerializedName("productId")
	val productId: String? = null
)
