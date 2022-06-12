package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {


    val isLoading: LiveData<Boolean> = repository.isLoading

     fun registerUser(name: String,username: String, email: String, password: String, callback: ApiCallbackString) {
        repository.createUser(name,username, email, password, callback)
    }
}