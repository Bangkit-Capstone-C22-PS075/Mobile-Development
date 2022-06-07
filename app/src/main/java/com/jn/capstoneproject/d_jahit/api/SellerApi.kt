package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.StatusResponse
import retrofit2.Call
import retrofit2.http.*

interface SellerApi {
    @FormUrlEncoded
    @POST("seller")
    fun createSeller(
        @Field("shopName") shopName: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("streetName") streetName: String,
        @Field("detailStreet") detailStreet: String,
        @Field("sellerName") sellerName: String,
        @Field("phoneNumber") phoneNumber: String,
//        @Field("gender") password: String
    ): Call<StatusResponse>

    @GET("seller/")
    fun getAllSeller(
        @Path("id")
        id: String
    ): Call<SellerResponse>

    @GET("seller/{id}")
    fun getSellerById(
        @Path("id")
        id: String
    ): Call<SellerResponse>

    @FormUrlEncoded
    @PUT("seller{id}")
    fun editSellerById(
        @Path("id")
        id: String,
        @Field("shopName") shopName: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("streetName") streetName: String,
        @Field("detailStreet") detailStreet: String,
        @Field("sellerName") sellerName: String,
        @Field("phoneNumber") phoneNumber: String,
    ): Call<SellerResponse>

    @DELETE("seller{id}")
    fun deleteSellerById(
        @Path("id")
        id: String
    ): Call<SellerResponse>

}