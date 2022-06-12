package com.jn.capstoneproject.d_jahit.model.dataresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("photoProfile")
	val photoProfile: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("insertedAt")
	val insertedAt: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("longtitude")
	val longtitude: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
):Parcelable

data class PutUser(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
