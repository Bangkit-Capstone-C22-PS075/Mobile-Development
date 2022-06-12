package com.jn.capstoneproject.d_jahit

import android.content.Context
import android.content.SharedPreferences

class SessionImageManager(context: Context)  {
    private var prefsImage: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveImageId(id:String){
        val editor= prefsImage.edit()
        editor.putString(Constanta.ID_IMAGE,id)
    }
    fun getAccesImageId():String?{
        return prefsImage.getString(Constanta.ID_IMAGE,null)
    }


}