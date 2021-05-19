package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.MainContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.PersonalIntegralModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainPresenter(v: MainContract.V) : BasePresenter<MainContract.V>(v), MainContract.P {
    private val apiServer = Api2Request.instances
    override fun get_personal_integral_req() {
        getView().show_loading("加载中···")
        apiServer.get_personal_integral().compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<PersonalIntegralModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<PersonalIntegralModel>) {
                    getView().get_personal_integral_resp(t)

                }

                override fun onError(e: Throwable) {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })
    }
}