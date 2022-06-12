package com.jn.capstoneproject.d_jahit

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jn.capstoneproject.d_jahit.model.Repository
import com.jn.capstoneproject.d_jahit.viewmodel.*
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(CreateSellerViewmodel::class.java) -> {
                CreateSellerViewmodel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(AddProductViewModel::class.java) -> {
                AddProductViewModel(Injection.provideUserRepository(context)) as T
            }
            modelClass.isAssignableFrom(ProductSellerViewModel::class.java) -> {
                ProductSellerViewModel(Injection.provideUserRepository(context)) as T
            }

            else -> throw IllegalArgumentException("Unkown ViewModel Class ${modelClass.name}")

        }
    }

}