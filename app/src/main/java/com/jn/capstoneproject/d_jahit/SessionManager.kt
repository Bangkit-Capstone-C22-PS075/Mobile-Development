package com.jn.capstoneproject.d_jahit

import android.content.Context
import android.content.SharedPreferences
import com.jn.capstoneproject.d_jahit.Constanta.ACCESS_ID
import com.jn.capstoneproject.d_jahit.Constanta.ACCESS_ID_SELLER
import com.jn.capstoneproject.d_jahit.Constanta.ID_IMAGE

class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAccessId(id: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS_ID, id)
            .apply()
    }

    fun fetchAccessId(): String? {
        return prefs.getString(ACCESS_ID, null)
    }

    fun deleteAccessToken() {
        val editor = prefs.edit()
        editor.clear()
            .apply()
    }



}