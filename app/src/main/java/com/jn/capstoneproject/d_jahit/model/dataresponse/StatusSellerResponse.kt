package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class StatusSellerResponse(

	@field:SerializedName("data")
	val data: DataSeller,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataSeller(

	@field:SerializedName("sellerId")
	val sellerId: String
)
