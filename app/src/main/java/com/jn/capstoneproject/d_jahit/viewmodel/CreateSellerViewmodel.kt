package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository

class CreateSellerViewmodel (private val repository: Repository) : ViewModel() {


    val isLoading: LiveData<Boolean> = repository.isLoading
    fun createSeller(
        shopName: String,
        province: String,
        city: String,
        streetName: String,
        detailStreet: String,
        sellerName: String,
        phoneNumber: String,
        callback: ApiCallbackString
    ) = repository.createSeller(
        shopName, province, city, streetName, detailStreet, sellerName, phoneNumber, callback
    )
}