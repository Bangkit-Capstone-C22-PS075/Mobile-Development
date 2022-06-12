package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddProductViewModel constructor( private val repository: Repository): ViewModel() {
    val getProdukByid:LiveData<ProductsItem> = repository.getProductId

    fun addProduct(
        sellerId: RequestBody,
        photoProduct: MultipartBody.Part,
        nameProduct:RequestBody,
        category: RequestBody,
        defination:RequestBody,
        price1:Double,
        price2: Double,
        callback: ApiCallbackString
    ) = repository.addProduct(sellerId,photoProduct,nameProduct,category, defination, price1, price2, callback)
    fun getProductById(id:String)= repository.getProduckById(id)
}