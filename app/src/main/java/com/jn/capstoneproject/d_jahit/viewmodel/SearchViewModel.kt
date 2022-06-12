package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerProductItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem

class SearchViewModel (private val repository: Repository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    private val searchProductCatergory=MutableLiveData<List<SellerProductItem>>()

    fun seacrCategory(category: String):LiveData<List<SellerProductItem>> = repository.searchCategory(category)

    fun getProduct(): LiveData<List<SellerProductItem>> {
        return searchProductCatergory
    }
}