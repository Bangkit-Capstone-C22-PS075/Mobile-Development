package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.PutUser
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusLoginResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface UserApi {

    @FormUrlEncoded
    @POST("user")
    fun addUser(
        @Field("fullName") Name: String,
        @Field("username") username: String,
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
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("email") email: String,
    ): Call<PutUser>

    @DELETE("user/{id}")
    fun deleteUserById(
        @Path("id")
        id: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/auth")
    fun loginUser(
        @Field("email") username: String,
        @Field("password") password: String
    ): Call<StatusLoginResponse>

}