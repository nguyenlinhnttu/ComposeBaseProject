package com.compose.base.common.utils

import android.util.Log
import com.compose.base.BuildConfig

object AppLogger {

    private const val DEFAULT_TAG = "AppLogger"

    var isDebugEnabled = BuildConfig.DEBUG

    fun d(message: String, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) Log.d(tag, message)
    }

    fun i(message: String, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) Log.i(tag, message)
    }

    fun w(message: String, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) Log.w(tag, message)
    }

    fun e(message: String, throwable: Throwable? = null, tag: String = DEFAULT_TAG) {
        if (isDebugEnabled) Log.e(tag, message, throwable)
    }
}
