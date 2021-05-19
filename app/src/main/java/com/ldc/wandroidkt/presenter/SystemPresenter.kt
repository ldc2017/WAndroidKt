package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.SystemContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SystemPresenter(v: SystemContract.V) : BasePresenter<SystemContract.V>(v), SystemContract.P {
    private val apiServer: ApiServer = Api2Request.instances
    override fun get_system_req() {
        getView().show_loading("加载中···")
        apiServer.get_system().compose(ApiScheduler.io2main())
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