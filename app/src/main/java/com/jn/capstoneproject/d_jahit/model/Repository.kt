package com.jn.capstoneproject.d_jahit.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.Constanta
import com.jn.capstoneproject.d_jahit.ResponseLogin
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.model.dataresponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field


class Repository(
    private val session: SessionManager,

    ) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _getUser = MutableLiveData<UserResponse>()
    val getUser: LiveData<UserResponse> = _getUser
    private val _getProduct = MutableLiveData<List<ProductsItem>>()
    val getProduct: LiveData<List<ProductsItem>> = _getProduct
    private val _searchProductCatergory = MutableLiveData<List<SellerProductItem>>()
    private val _seller=MutableLiveData<SellersItem>()
    val seller:LiveData<SellersItem> =_seller
    private val _getProductId = MutableLiveData<ProductsItem>()
    val getProductId: LiveData<ProductsItem> = _getProductId
    private val _transaksi=MutableLiveData<TransactionsItem>()
    val transaksi:LiveData<TransactionsItem> = _transaksi

    fun createUser(
        Name: String,
        username: String,
        Email: String,
        Password: String,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi().addUser(Name, username, Email, Password)
        service.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    responseBody?.data?.let{

                        session.saveAccessId(it.userId)
                    }


                        callback.onResponse(response.body() != null, Constanta.SUCCESS)

                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

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
        password: String,
        gender: String,
        dateOfBirth: String,
        phoneNumber: String,
        email: String,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi()
            .editUserById(id, name, username,password, gender, dateOfBirth, phoneNumber, email)
        service.enqueue(object : Callback<PutUser> {
            override fun onResponse(call: Call<PutUser>, response: Response<PutUser>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${responseBody?.message}")
                }
            }

            override fun onFailure(call: Call<PutUser>, t: Throwable) {
                Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
            }
        })

    }

    fun loginUser(email: String, password: String, callback: ApiCallbackString) {
        _isLoading.value = true
        val service = ApiConfig.getUserApi().loginUser(email, password)
        service.enqueue(object : Callback<StatusLoginResponse> {
            override fun onResponse(
                call: Call<StatusLoginResponse>,
                response: Response<StatusLoginResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()?.loginResult
                if (response.isSuccessful) {
                        if (responseBody !=null){
                            session.saveAccessId(responseBody)
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

    fun getUserById(id: String, callback: ApiCallbackString) {
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
                Log.e(TAG, "onFailure2: ${t.message}")
            }
        })
    }

    // repository Seller

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
        latLng: LatLng?,
        callback: ApiCallbackString
        ) {
        _isLoading.value = true
        if (latLng !=null) {
            val service = ApiConfig.getSellerApi().createSeller(
                userId, shopName, sellerPhoto, province, city, streetName,
                detailStreet, sellerName, phoneNumber,
                latLng.latitude,
                latLng.longitude
            )
            service.enqueue(object : Callback<StatusSellerResponse> {
                override fun onResponse(
                    call: Call<StatusSellerResponse>,
                    response: Response<StatusSellerResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        callback.onResponse(response.body() != null, Constanta.SUCCESS)
                    } else {
                        Log.e(TAG, "onFailure1: ${response.message()}")

                    }
                }

                override fun onFailure(call: Call<StatusSellerResponse>, t: Throwable) {
                    Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
                }
            })
        }
    }

    fun updateSeller(
        id: String,
        userId:RequestBody,
        shopName: RequestBody,
        sellerPhoto: MultipartBody.Part,
        province: RequestBody,
        city: RequestBody,
        streetName: RequestBody,
        detailStreet: RequestBody,
        sellerName: RequestBody,
        phoneNumber: RequestBody,
        latLng: LatLng?,
        callback: ApiCallbackString
    ) {
        _isLoading.value = true
        if (latLng !=null) {
            val service = ApiConfig.getSellerApi()
                .editSellerById(
                    id,
                    userId,
                    shopName,
                    sellerPhoto,
                    province,
                    city,
                    streetName,
                    detailStreet,
                    sellerName,
                    phoneNumber,
                    latLng.latitude,
                    latLng.longitude
                )
            service.enqueue(object : Callback<SellerResponse> {
                override fun onResponse(
                    call: Call<SellerResponse>,
                    response: Response<SellerResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {

                    }
                }

                override fun onFailure(call: Call<SellerResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
    fun getSellerById(id: String){
        val service= ApiConfig.getSellerApi().getSellerById(id)
        service.enqueue(object : Callback<SellersItem>{
            override fun onResponse(call: Call<SellersItem>, response: Response<SellersItem>) {
                if (response.isSuccessful){
                    _seller.value=response.body()
                    Log.d(TAG,"Seller Berhasil di tambah")
                }
                else{
                    Log.e(TAG,"gagal")
                }

            }

            override fun onFailure(call: Call<SellersItem>, t: Throwable) {
                Log.e(TAG, "Gagal : ${t.message}")
            }
        })
    }

    // repository Product

    fun getAllProduct(callback: ApiCallbackString) {
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
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e(TAG, "Gagal : ${t.message}")
            }
        })
    }

    fun searchCategory(category: String): LiveData<List<SellerProductItem>> {
        val service = ApiConfig.getSearch().searchCategory(category)
        service.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchProductCatergory.value = response.body()?.sellers
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "Retrofit: ${t.message}")
            }
        })
        return _searchProductCatergory
    }

    fun addProduct(
        sellerId: RequestBody,
        photoProduct: MultipartBody.Part,
        nameProduct: RequestBody,
        category: RequestBody,
        defination: RequestBody,
        price1: Double,
        price2: Double,
        callback: ApiCallbackString
    ) {
        val service = ApiConfig.getProductApi()
            .addProduct(sellerId,photoProduct, nameProduct, category, defination, price1, price2)
        service.enqueue(object : Callback<AddProductResponse> {
            override fun onResponse(
                call: Call<AddProductResponse>,
                response: Response<AddProductResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
            }
        })
    }

    fun getProductSeller( id:String, callback: ApiCallbackString){
        val service= ApiConfig.getProductApi().getAllProductSeller(id)
        service.enqueue(object : Callback<ProductSellerResponse>{
            override fun onResponse(
                call: Call<ProductSellerResponse>,
                response: Response<ProductSellerResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _getProduct.value = response.body()?.products

                    } else {
                        Log.e(TAG, "GAGAl")
                    }
                    callback.onResponse(response.body() != null, Constanta.SUCCESS)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ProductSellerResponse>, t: Throwable) {
                Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
            }
        })
    }

    fun getProduckById(id: String){
        val service= ApiConfig.getProductApi().getProductById(id)
        service.enqueue(object : Callback<ProductsItem>{
            override fun onResponse(
                call: Call<ProductsItem>,
                response: Response<ProductsItem>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _getProductId.value = response.body()

                    } else {
                        Log.e(TAG, "GAGAl")
                    }
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ProductsItem>, t: Throwable) {
                Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
            }
        })
    }
    //transaksi
    fun createTransaksi(
        userId: String,
        username: String,
        idPurpose: String,
        password: String,
        productAmount: String,
        price: Int,
        totalPrice: Int,
        callback: ApiCallbackString
    ){
        val service= ApiConfig.getTransaksi().addTransaksi(
          userId, username, idPurpose, password, productAmount, price, totalPrice
        )
        service.enqueue(object : Callback<TransaksiResponse>{
            override fun onResponse(
                call: Call<TransaksiResponse>,
                response: Response<TransaksiResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onResponse(response.body() != null, Constanta.SUCCESS)
                    } else {
                        Log.e(TAG, "GAGAl")
                    }
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
            }
        })

        fun getAllTransaksi(){
            val service=ApiConfig.getTransaksi().getAlltransaksi()
            service.enqueue(object : Callback<TransactionsItem>{
                override fun onResponse(
                    call: Call<TransactionsItem>,
                    response: Response<TransactionsItem>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            _transaksi.value=response.body()

                        } else {
                            Log.e(TAG, "GAGAl")
                        }
                    } else {
                        Log.e(TAG, "onFailure1: ${response.message()}")

                    }
                }

                override fun onFailure(call: Call<TransactionsItem>, t: Throwable) {
                    Log.e(TAG, "retrofit gagal terhubung: ${t.message}")
                }
            })
        }
    }

    //Coment



    companion object {
        const val TAG = "API"
    }
}