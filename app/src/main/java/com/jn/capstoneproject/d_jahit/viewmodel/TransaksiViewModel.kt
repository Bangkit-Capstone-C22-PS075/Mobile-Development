package com.jn.capstoneproject.d_jahit.viewmodel

import androidx.lifecycle.ViewModel
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.model.Repository

class TransaksiViewModel(private val repository: Repository) : ViewModel() {
    fun createTransaksi(
        userId: String,
        username: String,
        idPurpose: String,
        password: String,
        productAmount: String,
        price: Int,
        totalPrice: Int,
        callback: ApiCallbackString
    )=repository.createTransaksi(userId, username, idPurpose, password, productAmount, price, totalPrice, callback)
}