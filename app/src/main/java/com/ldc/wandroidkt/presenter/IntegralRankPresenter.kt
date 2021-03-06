package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.IntegralRankContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.IntegralRankModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class IntegralRankPresenter constructor(v: IntegralRankContract.V) :
    BasePresenter<IntegralRankContract.V>(v), IntegralRankContract.P {
    private val apiServer: ApiServer = Api2Request.instances
    override fun get_integral_rank_req(index: Int) {

        getView().show_loading("加载中···")
        apiServer.get_integral_rank(index).compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<IntegralRankModel>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<IntegralRankModel>) {
                    getView().get_integral_rank_resp(t)

                }

                override fun onError(e: Throwable) {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}