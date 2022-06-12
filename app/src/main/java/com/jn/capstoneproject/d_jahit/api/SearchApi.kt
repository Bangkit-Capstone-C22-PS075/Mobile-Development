package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.model.dataresponse.SearchResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApi {
    @GET("search/{category}")
    fun searchCategory(
        @Path("category")
        category: String
    ): Call<SearchResponse>
}