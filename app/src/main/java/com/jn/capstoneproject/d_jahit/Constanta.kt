package com.jn.capstoneproject.d_jahit

import android.Manifest
import androidx.test.espresso.idling.CountingIdlingResource

object Constanta {
    const val CAMERA_X_RESULT = 200
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    const val REQUEST_CODE_PERMISSIONS = 10
    private const val RESOURCE = "GLOBAL"
    const val SUCCESS = "success"
    const val ACCESS_ID = "access_id"
    const val EXTRA_USER = "extra_user"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    Constanta.increment() // Set app as busy.
    return try {
        function()
    } finally {
        Constanta.decrement() // Set app as idle.
    }
}