package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse

class ProfileViewModel (private val repository: Repository) : ViewModel() {

    val getUser : LiveData<UserResponse> = repository.getUser

    fun getUserById(id:String, callback: ApiCallbackString){
        repository.getUserById(id, callback)
    }
    fun updateUser(id: String,
                   name: String,
                   username: String,
                   gender: String,
                   dateOfBirth: String,
                   phoneNumber: String,
                   email: String){
        repository.updateUser(id, name, username, gender, dateOfBirth, phoneNumber, email)
    }
}