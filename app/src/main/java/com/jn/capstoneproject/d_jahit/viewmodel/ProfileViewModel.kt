package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import okhttp3.RequestBody

class ProfileViewModel (private val repository: Repository) : ViewModel() {

    val getUser : LiveData<UserResponse> = repository.getUser
    val getSeller:LiveData<SellersItem> = repository.seller

    fun getUserById(id:String, callback: ApiCallbackString){
        repository.getUserById(id, callback)
    }
    fun updateUser(id: String,
                   name: String,
                   username: String,
                   password: String,
                   gender: String,
                   dateOfBirth: String,
                   phoneNumber: String,
                   email: String,
                   callback: ApiCallbackString
    )=repository.updateUser(id, name, username,password, gender, dateOfBirth, phoneNumber, email,callback)

    fun getSellerById(id: String)=repository.getSellerById(id)
}