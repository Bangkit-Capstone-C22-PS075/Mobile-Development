package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class CommentResponse(

	@field:SerializedName("comments")
	val comments: List<CommentsItem>,

	@field:SerializedName("status")
	val status: String
)

data class CommentsItem(

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("username")
	val username: String
)
