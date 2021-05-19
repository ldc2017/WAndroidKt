package org.ldc.module_res.core

import android.os.Bundle
import io.reactivex.disposables.Disposable
import java.lang.ref.SoftReference

abstract class BasePresenter<V>(v: V) {
    private var mView: SoftReference<V> = SoftReference<V>(v)

    fun getView(): V {
        return mView.get()!!
    }

    open fun onStart() {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onStop() {}
    open fun onDestroy() {
        mView.clear()
    }

    open fun onActivityCreated(arguments: Bundle?) {}


    open fun destory_disposable(disposable: Disposable) {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }

    }

}