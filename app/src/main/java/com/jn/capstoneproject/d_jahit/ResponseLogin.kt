package com.jn.capstoneproject.d_jahit

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("status")
	val status: String
)

data class UserIdItem(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)

data class LoginResult(

	@field:SerializedName("userId")
	val userId: List<UserIdItem>
)
