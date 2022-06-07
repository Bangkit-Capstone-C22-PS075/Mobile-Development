package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.PutUser
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusLoginResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST("user")
    fun addUser(
        @Field("fullName") Name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<StatusResponse>

    @GET("user")
    fun getAllUser(): Call<PutUser>

    @GET("user/{id}")
    fun getUserById(
        @Path("id")
        id: String
    ): Call<UserResponse>


    @FormUrlEncoded
    @PUT("user/{id}")
    fun editUserById(
        @Path("id") id: String,
        @Field("fullName") Name: String,
        @Field("username") username: String,
        @Field("gender") gender: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("email") email: String,
    ): Call<UserResponse>

    @DELETE("user/{id}")
    fun deleteUserById(
        @Path("id")
        id: String
    ): Call<UserResponse>

    @GET("user/auth/{username}/{password}")
    fun loginUser(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<StatusLoginResponse>

}