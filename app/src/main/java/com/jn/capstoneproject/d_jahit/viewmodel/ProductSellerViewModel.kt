package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class ProductSellerViewModel (private val repository: Repository) : ViewModel()  {
    val getProduct: LiveData<List<ProductsItem>> = repository.getProduct

    fun getAllProduct(id:String,callback: ApiCallbackString) {
        repository.getProductSeller(id,callback)
    }
}