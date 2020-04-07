package com.ldc.wandroidkt

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.ldc.wandroidkt.commom.ErrorHandle
import io.reactivex.plugins.RxJavaPlugins
import me.yokeyword.fragmentation.Fragmentation

class mApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        //
        Fragmentation.builder()
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG)
            .install()
        //
        Thread.setDefaultUncaughtExceptionHandler(ErrorHandle())
        //
        RxJavaPlugins.setErrorHandler { e -> e.printStackTrace() }
    }


}