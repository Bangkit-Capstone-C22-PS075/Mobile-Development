package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
class LoginViewModel (private val repository: Repository) : ViewModel() {

    fun loginUser(email:String,password:String, callback: ApiCallbackString){
        repository.loginUser(email,password, callback)
    }
}