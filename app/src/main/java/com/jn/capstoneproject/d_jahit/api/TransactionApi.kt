package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.TransactionsItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.TransaksiResponse
import retrofit2.Call
import retrofit2.http.*

interface TransactionApi {

    @FormUrlEncoded
    @POST("Transaction")
    fun addTransaksi(
        @Field("userId") userId: String,
        @Field("username") username: String,
        @Field("idPurpose") idPurpose: String,
        @Field("productName") password: String,
        @Field("productAmount") productAmount: String,
        @Field("price") price: Int,
        @Field("totalPrice") totalPrice: Int,
    ): Call<TransaksiResponse>
    @GET("Transaction/{id}")
    fun gettransaksi(
        @Path("id")id:String
    ):Call<TransactionsItem>

    @GET("Transaction")
    fun getAlltransaksi(
    ):Call<TransactionsItem>

    @FormUrlEncoded
    @PUT("Transaction")
    fun updateTransaksi(
        @Field("userId") userId: String,
        @Field("username") username: String,
        @Field("idPurpose") idPurpose: String,
        @Field("productName") password: String,
        @Field("productAmount") productAmount: String,
        @Field("price") price: Int,
        @Field("totalPrice") totalPrice: Int,
    ): Call<TransaksiResponse>
}