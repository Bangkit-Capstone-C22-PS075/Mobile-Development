package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class StatusLoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class LoginResult(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("insertedAt")
	val insertedAt: String? = null,

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
)
