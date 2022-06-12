package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.CommentResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CommentApi {

    @FormUrlEncoded
    @POST("comment")
    fun postComment(
        @Field("fullName") Name: String,
        @Field("id") id: String,
        @Field("username") username: String,
        @Field("rating") rating: Int,
    ): Call<CommentResponse>

    @GET("Comment")
    fun getComment():Call<CommentResponse>
}