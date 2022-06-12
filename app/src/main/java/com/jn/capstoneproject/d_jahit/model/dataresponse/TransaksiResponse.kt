package com.jn.capstoneproject.d_jahit.model.dataresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TransaksiResponse(

	@field:SerializedName("transactions")
	val transactions: List<TransactionsItem>,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class TransactionsItem(

	@field:SerializedName("productAmount")
	val productAmount: Int,

	@field:SerializedName("totalPrice")
	val totalPrice: Int,

	@field:SerializedName("insertedAt")
	val insertedAt: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("idPurpose")
	val idPurpose: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("idTransaction")
	val idTransaction: String,

	@field:SerializedName("username")
	val username: String
) : Parcelable
