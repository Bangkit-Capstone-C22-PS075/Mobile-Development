package com.jn.capstoneproject.d_jahit

import android.content.Context
import com.jn.capstoneproject.d_jahit.api.ApiConfig
import com.jn.capstoneproject.d_jahit.model.Repository

object Injection {
    fun provideUserRepository(context: Context): Repository {
        val sessionManager = SessionManager(context)
        return Repository(sessionManager)
    }
}