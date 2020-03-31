package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.SystemContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SystemPresenter(v: SystemContract.V) : BasePresenter<SystemContract.V>(v), SystemContract.P {
    private val apiServer: ApiServer = Api2Request.instances
    override fun get_system_req() {
        getView().show_loading("加载中···")
        apiServer.get_system()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<BaseModel<SystemModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<SystemModel>) {
                    getView().get_system_resp(t)

                }

                override fun onError(e: Throwable) {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}