package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class SellerResponse(

	@field:SerializedName("streetName")
	val streetName: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: Int,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("skill")
	val skill: String,

	@field:SerializedName("sellerName")
	val sellerName: String,

	@field:SerializedName("shopName")
	val shopName: String,

	@field:SerializedName("sellerPhoto")
	val sellerPhoto: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("detailStreet")
	val detailStreet: String
)
