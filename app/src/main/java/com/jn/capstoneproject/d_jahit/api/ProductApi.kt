package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductApi {
    @Multipart
    @POST("product")
    fun addProduct(
        @Part("Name") Name: String,
        @Part productPhoto: MultipartBody.Part,
        @Part("definition") description: RequestBody,
        @Part("price_1") price_1: Double? = null,
        @Part("price_2") price_2: Double? = null
    ): Call<AddProductResponse>

    @GET("product")
    fun getAllProduct(): Call<ProductResponse>
}