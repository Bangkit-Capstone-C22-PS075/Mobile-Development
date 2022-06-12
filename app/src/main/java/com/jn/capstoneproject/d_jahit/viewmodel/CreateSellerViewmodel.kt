package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateSellerViewmodel (private val repository: Repository) : ViewModel() {
    val getSeller:LiveData<SellersItem> = repository.seller

    val isLoading: LiveData<Boolean> = repository.isLoading
    fun createSeller(
        userId: RequestBody,
        shopName: RequestBody,
        sellerPhoto: MultipartBody.Part,
        province: RequestBody,
        city: RequestBody,
        streetName: RequestBody,
        detailStreet: RequestBody,
        sellerName: RequestBody,
        phoneNumber: RequestBody,
        latLng: LatLng? = null,
        callback: ApiCallbackString
    ) = repository.createSeller(
        userId,shopName,sellerPhoto, province, city, streetName, detailStreet, sellerName, phoneNumber,latLng,callback
    )
    fun getSellerById(id: String)=repository.getSellerById(id)
    fun updateSeller(
        id: String,
        userId: RequestBody,
        shopName: RequestBody,
        sellerPhoto: MultipartBody.Part,
        province: RequestBody,
        city: RequestBody,
        streetName: RequestBody,
        detailStreet: RequestBody,
        sellerName: RequestBody,
        phoneNumber: RequestBody,
        latLng: LatLng? = null,
        callback: ApiCallbackString
    ) = repository.updateSeller(
        id,userId,shopName,sellerPhoto, province, city, streetName, detailStreet, sellerName, phoneNumber,latLng,callback
    )
}