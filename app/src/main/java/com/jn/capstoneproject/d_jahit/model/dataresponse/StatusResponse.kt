package com.jn.capstoneproject.d_jahit.model.dataresponse

import com.google.gson.annotations.SerializedName

data class StatusResponse(


    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class Data(

    @field:SerializedName("userId")
    val userId: String
)

