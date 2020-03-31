package com.ldc.wandroidkt.commom

import android.util.Log
import java.lang.StringBuilder

class ErrorHandle : Thread.UncaughtExceptionHandler {
    val TAG: String = ErrorHandle::class.java.name
    override fun uncaughtException(t: Thread, e: Throwable) {
        val logs = StringBuilder()
        logs.append("\n")
            .append("------")
            .append("thread_id - ${t.id} \n")
            .append("thread_name - ${t.name} \n")
            .append("thread_priority - ${t.priority} \n")
            .append("thread_message - ${e.message} \n")
            .append("------")
        Log.e(TAG, logs.toString())
    }
}