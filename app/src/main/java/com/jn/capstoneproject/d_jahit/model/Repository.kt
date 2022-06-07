package com.jn.capstoneproject.d_jahit.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.Constanta
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.model.dataresponse.*
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository(
    private val session: SessionManager,

    ) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _getUser = MutableLiveData<UserResponse>()
    val getUser: LiveData<UserResponse> = _getUser
    private val _getallUser = MutableLiveData<List<UserResponse>>()
    val getallUser: LiveData<List<UserResponse>> = _getallUser
    private val _getProduct = MutableLiveData<List<ProductResponse>>()
    val getProduct: LiveData<List<ProductResponse>> =_getProduct


    fun createUser(Name: String, Email: String, Password: String, callback: ApiCallbackString) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi().addUser(Name, Email, Password)
        service.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    responseBody?.data?.let {
                        session.saveAccessId(it.userId)
                    }
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }

            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
                callback.onResponse(false, t.message.toString())
            }
        })

    }

    fun updateUser(
        id: String,
        name: String,
        username: String,
        gender: String,
        dateOfBirth: String,
        phoneNumber: String,
        email: String
    ) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi()
            .editUserById(id, name, username, gender, dateOfBirth, phoneNumber, email)
        service.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun loginUser(email: String, password: String, callback: ApiCallbackString) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi().loginUser(email, password)
        service.enqueue(object : Callback<StatusLoginResponse> {
            override fun onResponse(call: Call<StatusLoginResponse>, response: Response<StatusLoginResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    responseBody?.loginResult?.let {
                        if (it.id!=null)
                        session.saveAccessId(it.id)

                    }
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)

                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StatusLoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
            }
        })
    }

    fun getUserById(id: String , callback: ApiCallbackString) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi().getUserById(id)
        service.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _getUser.value = response.body()

                callback.onResponse(response.body() != null, Constanta.SUCCESS)

            } else {
                Log.e(TAG, "onFailure1: ${response.message()}")
            }

        }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun createSeller(
        shopName: String,
        province: String,
        city: String,
        streetName: String,
        detailStreet: String,
        sellerName: String,
        phoneNumber: String,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        val service = ApiConfig.getSellerApi().createSeller(
            shopName, province, city, streetName,
            detailStreet, sellerName, phoneNumber
        )
        service.enqueue(object : Callback<StatusResponse>{
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
//
//                    responseBody?.data?.let {
//                        session.saveAccessId(it.userId)
//                    }
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateSeller(
        id: String,
        shopName: String,
        province: String,
        city: String,
        streetName: String,
        detailStreet: String,
        sellerName: String,
        phoneNumber: String,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        val service = ApiConfig.getSellerApi()
            .editSellerById(id, shopName,province,city,streetName,detailStreet,sellerName,phoneNumber)
        service.enqueue(object : Callback<SellerResponse> {
            override fun onResponse(call: Call<SellerResponse>, response: Response<SellerResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<SellerResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    fun getAllProduct( callback: ApiCallbackString){
        val service = ApiConfig.getProductApi().getAllProduct()
        service.enqueue(object : Callback<ProductResponse>{
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
//                    if(response.body() != null ){
//                        _getProduct.value=response.body()?.listProduck
//
//                    }
//                    else{
//                        Log.e(TAG,"GAGAl")
//                    }
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                }else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

            }
        })
    }
    fun getAlUser( callback: ApiCallbackString){
        val service = ApiConfig.getUserApi().getAllUser()
        service.enqueue(object : Callback<PutUser>{
            override fun onResponse(
                call: Call<PutUser>,
                response: Response<PutUser>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                   _getallUser.value = response.body()?.putUserResponse
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                }else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: Call<PutUser>, t: Throwable) {

            }
        })
    }

    companion object {
        const val TAG = "API"
    }
}