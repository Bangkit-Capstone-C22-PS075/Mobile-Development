package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()

    private val _getProduct = MutableLiveData<List<ProductsItem>>()
    val getProduct: LiveData<List<ProductsItem>> = repository.getProduct

    fun getAllProduct(callback: ApiCallbackString) {
        repository.getAllProduct(callback)
    }


    companion object {
        const val TAG = "API"
    }
}