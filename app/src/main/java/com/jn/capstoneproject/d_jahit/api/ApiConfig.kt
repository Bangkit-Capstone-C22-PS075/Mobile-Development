package com.jn.capstoneproject.d_jahit.api

import com.google.gson.GsonBuilder
import com.jn.capstoneproject.d_jahit.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {

    private const val BASE_URL: String = "BASE_URL"
   
    private val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun getSearch():SearchApi{
        return retrofit.create(SearchApi::class.java)
    }
    fun getTransaksi():TransactionApi{
        return retrofit.create(TransactionApi::class.java)
    }


    fun getImage():ImageApi{
        return retrofit.create(ImageApi::class.java)
    }


}
