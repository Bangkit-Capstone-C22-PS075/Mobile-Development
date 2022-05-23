package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST("addUser")
    suspend fun login(
//        @Field("fullName") id: String,
//        @Field("username") password: String,
//        @Field("password") email: String,
//        @Field("gender") password: String
    ): Response<UserResponse>

    @GET("getAllUser/")
    suspend fun getAllSeller(): Response<UserResponse>

    @GET("getUserById")
    suspend fun getSellerById(): Response<UserResponse>

    @PUT("editUserById")
    suspend fun editSellerById(): Response<UserResponse>

    @DELETE("deleteUserIDd")
    suspend fun deleteSellerByIDd(): Response<UserResponse>

}