package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("photoProfile")
    val photoProfile: String,

    @field:SerializedName("insertedAt")
    val insertedAt: String,

    )
