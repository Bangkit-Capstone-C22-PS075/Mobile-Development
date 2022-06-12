package com.jn.capstoneproject.d_jahit.api

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@field:SerializedName("images")
	val images: ImagesItem,

	@field:SerializedName("status")
	val status: String
)

data class ImagesItem(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String
)
