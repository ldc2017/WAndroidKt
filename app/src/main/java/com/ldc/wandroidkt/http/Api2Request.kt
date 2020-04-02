package com.ldc.wandroidkt.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api2Request {

    val TAG: String = Api2Request::class.java.name

    //客户端
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RequestLogStatusInterceptor())//检测是否登录
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(35, TimeUnit.SECONDS)
        .addInterceptor(netLog())
        .build()

    //请求书服务
    val instances: ApiServer by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.base_url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()


        retrofit.create(ApiServer::class.java)

    }

    //显示网络日志
    fun netLog(): HttpLoggingInterceptor {
        val show_net_log = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e(TAG, "网络请求----$message")
            }
        })
        show_net_log.level = HttpLoggingInterceptor.Level.BODY
        return show_net_log
    }
}