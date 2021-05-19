package org.ldc.module_res.http

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class LogInterceptorLogger : HttpLoggingInterceptor.Logger {
    private val TAG: String = "LogInterceptor"
    override fun log(message: String) {
        Log.e(TAG, "网络日志<---->${message}")
    }
}