package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface SellerApi {
    @FormUrlEncoded
    @POST("addSeller")
    suspend fun login(
//        @Field("fullName") id: String,
//        @Field("username") password: String,
//        @Field("password") email: String,
//        @Field("gender") password: String
    ):Response<UserResponse>

    @GET("getAllSeller/")
    suspend fun getAllSeller():Response<SellerResponse>

    @GET("getSellerById")
    suspend fun getSellerById():Response<SellerResponse>

    @PUT("editSellerById")
    suspend fun editSellerById():Response<SellerResponse>

    @DELETE("deleteSellerByIDd")
    suspend fun deleteSellerByIDd():Response<SellerResponse>

}