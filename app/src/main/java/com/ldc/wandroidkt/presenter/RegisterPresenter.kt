package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.RegisterContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.model.BaseModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RegisterPresenter constructor(v: RegisterContract.V) : BasePresenter<RegisterContract.V>(v),
    RegisterContract.P {
    private val apiServer = Api2Request.instances
    override fun register_req(username: String, password: String, repassword: String) {
        getView().show_loading("注册中···")
        apiServer.register(username, password, repassword).compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<Any>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<Any>) {
                    getView().register_resp(t)
                }

                override fun onError(e: Throwable) {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}