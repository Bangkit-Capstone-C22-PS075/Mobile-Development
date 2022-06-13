package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class StatusLoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: String? =null,
	@field:SerializedName("status")
	val status: String? = null
)
