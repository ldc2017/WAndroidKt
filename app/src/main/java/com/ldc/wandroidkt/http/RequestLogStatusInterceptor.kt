package com.ldc.wandroidkt.http

import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.text.TextUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.ui.activitys.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class RequestLogStatusInterceptor() : Interceptor {
    private var handlerThread: HandlerThread = HandlerThread("check_login_thread")
    private lateinit var handler: Handler
    private val notify_code: Int = 0x000
    private val handlerCallback: Handler.Callback = Handler.Callback { msg ->
        if (notify_code == msg.what) {
            val json: String = msg.obj as String
            json ?: return@Callback false
            handleJson(json)
            return@Callback true
        }
        false
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper, handlerCallback)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val resp = chain.proceed(req)
        val mediaType = resp.body!!.contentType()
        val json: String = resp.body!!.string()

        //
        val message = handler.obtainMessage(notify_code)
        message.obj = json
        handler.sendMessage(message)
        //
        return resp.newBuilder().body(ResponseBody.create(mediaType, json)).build()

    }


    private fun handleJson(json: String = "") {
        if (TextUtils.isEmpty(json)) return
        try {
            val model: BaseModel<Any> =
                GsonUtils.fromJson<BaseModel<Any>>(json, BaseModel::class.java)
            model ?: return
            if (Api.error_code_no_login == model.errorCode) {
                val intent = Intent(Utils.getApp(), LoginActivity::class.java)
                ActivityUtils.startActivity(intent)
                ActivityUtils.finishAllActivities()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}