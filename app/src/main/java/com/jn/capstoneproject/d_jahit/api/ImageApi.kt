package com.jn.capstoneproject.d_jahit.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ImageApi {
    @Multipart
    @POST("image/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Call<ImageResponse>

    @GET("image/{id}")
    fun getImageById(
        @Path("id")id:String
    ):Call<ImagesItem>
}