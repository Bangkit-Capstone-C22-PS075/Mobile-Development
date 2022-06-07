package com.jn.capstoneproject.d_jahit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.database.RepositoryDatabase
import com.jn.capstoneproject.d_jahit.database.history.HistoryDao
import com.jn.capstoneproject.d_jahit.database.history.HistoryDatabase
import com.jn.capstoneproject.d_jahit.database.history.ProductModel
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: Repository)  : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = repository.isLoading
//
//    private val repositoryDatabase =RepositoryDatabase(application)
//    private val dao:HistoryDao
//    private val product= MutableLiveData<ProductModel>()
//    init {
//        val database: HistoryDatabase = HistoryDatabase.getInstance(application)
//        dao= database.historyDao()
//
//    }

    //    val getProduct: LiveData<List<ProductResponse>> =repository.getProduct
//    val getallUser: LiveData<List<UserResponse>> =repository.getallUser
    private val _getProduct = MutableLiveData<List<ProductsItem>>()
    val getProduct: LiveData<List<ProductsItem>> = _getProduct

    //    fun getAllProduct(callback: ApiCallbackString){
//        repository.getAllProduct(callback)
//    }
//    fun getAllUser(callback: ApiCallbackString){
//        repository.getAlUser(callback)
//    }
    fun getAllProduct() {
        val service = ApiConfig.getProductApi().getAllProduct()
        service.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _getProduct.value = response.body()?.products

                    } else {
                        Log.e(TAG, "GAGAl")
                    }

                } else {
                    Log.e(Repository.TAG, "onFailure1: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")

                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

            }
        })
    }
//
//    fun insertHistoryProduct(history: ProductModel) = viewModelScope.launch {
//        repositoryDatabase.insertHistory(history)
//    }
//
//    fun deleteHistoryProduct(history: ProductModel) = viewModelScope.launch {
//        repositoryDatabase.deleteHistory(history)
//    }
//
//    fun getHistoryProduct(): LiveData<ProductModel>{
//        return product
//    }

    companion object {
        const val TAG = "API"
    }
}