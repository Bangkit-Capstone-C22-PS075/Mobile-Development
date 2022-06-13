package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SellerApi {
    @Multipart
    @POST("seller")
    fun createSeller(
        @Part("userId") userId:RequestBody,
        @Part("shopName") shopName: RequestBody,
        @Part sellerPhoto: MultipartBody.Part,
        @Part("province") province: RequestBody,
        @Part("city") city: RequestBody,
        @Part("streetName") streetName: RequestBody,
        @Part("detailStreet") detailStreet: RequestBody,
        @Part("sellerName") sellerName: RequestBody,
        @Part("phoneNumber}") phoneNumber: RequestBody,
        @Part("latitude") latitude: Double,
        @Part("longitude}") longitude: Double,
    ): Call<StatusSellerResponse>

    @GET("seller/")
    fun getAllSeller(
        @Path("id")
        id: String
    ): Call<SellerResponse>

    @GET("seller/user-id/{id}")
    fun getSellerById(
        @Path("id")
        id: String
    ): Call<SellersItem>



    @Multipart
    @PUT("seller/{id}")
    fun editSellerById(
        @Path("id")id: String,
        @Part("userId") userId:RequestBody,
        @Part("shopName") shopName: RequestBody,
        @Part sellerPhoto: MultipartBody.Part,
        @Part("province") province: RequestBody,
        @Part("city") city: RequestBody,
        @Part("streetName") streetName: RequestBody,
        @Part("detailStreet") detailStreet: RequestBody,
        @Part("sellerName") sellerName: RequestBody,
        @Part("phoneNumber}") phoneNumber: RequestBody,
        @Part("latitude") latitude: Double,
        @Part("longitude}") longitude: Double,
    ): Call<SellerResponse>

    @DELETE("seller{id}")
    fun deleteSellerById(
        @Path("id")
        id: String
    ): Call<SellerResponse>

}