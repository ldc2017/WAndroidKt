package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.WXNumberContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WXNumberModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class WXNumberPresenter(v: WXNumberContract.V) : BasePresenter<WXNumberContract.V>(v),
    WXNumberContract.P {
    private var apiServer: ApiServer = Api2Request.instances

    override fun get_wx_number_req() {
        getView().show_loading("数据加载中···")
        apiServer.get_wx_number()
            .compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<WXNumberModel>> {
                var disposable: Disposable? = null
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable!!)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<WXNumberModel>) {
                    getView().get_wx_number_resp(t)

                }

                override fun onError(e: Throwable) {
                    getView().hide_loading()
                    destory_disposable(disposable!!)

                }
            })

    }
}