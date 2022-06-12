package com.jn.capstoneproject.d_jahit.model.dataresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SellerResponse(

	@field:SerializedName("sellers")
	val sellers: List<SellersItem>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class SellersItem(

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("sellerName")
	val sellerName: String,

	@field:SerializedName("longtitude")
	val longtitude: Double,

	@field:SerializedName("shopName")
	val shopName: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("streetName")
	val streetName: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: Int,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@field:SerializedName("skill")
	val skill: String,

	@field:SerializedName("sellerPhoto")
	val sellerPhoto: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("detailStreet")
	val detailStreet: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
): Parcelable
