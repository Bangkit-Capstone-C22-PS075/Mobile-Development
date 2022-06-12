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
        @Part("sellerId") sellerId: RequestBody,
        @Part productPhoto: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("definition") description: RequestBody,
        @Part("price_1") price_1: Double,
        @Part("price_2") price_2: Double
    ): Call<AddProductResponse>

    @GET("product")
    fun getAllProduct(): Call<ProductResponse>

    @GET("product/seller/{sellerId}")
    fun getAllProductSeller(
        @Path("sellerId") sellerId:String
    ): Call<ProductSellerResponse>
    @GET("product/{id}")
    fun getProductById(
        @Path("id") id:String
    ): Call<ProductsItem>
}