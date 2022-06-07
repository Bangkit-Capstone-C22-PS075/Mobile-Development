package com.jn.capstoneproject.d_jahit.api

import com.jn.capstoneproject.d_jahit.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL: String = "http://192.168.86.245:3000/"

    private val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getSellerApi(): SellerApi {
        return retrofit.create(SellerApi::class.java)
    }
    fun getUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    fun getProductApi(): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }


}