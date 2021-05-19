package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.WXNumberArticleContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WxNumberArticleModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WXNumberArticlePresenter(v: WXNumberArticleContract.V) :
    BasePresenter<WXNumberArticleContract.V>(v), WXNumberArticleContract.P {

    private var apiServer: ApiServer = Api2Request.instances

    override fun get_wx_number_article_req(number: String, index: Int) {
        getView().show_loading("加载中···")
        apiServer.get_wx_number_list(number, index)
            .compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<WxNumberArticleModel>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<WxNumberArticleModel>) {
                    getView().get_wx_number_article_resp(t)
                }

                override fun onError(e: Throwable) {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }


    override fun collect_article_req(id: String) {
        apiServer.collect_article(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<Any>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<Any>) {
                    getView().collect_article_resp(t)
                }

                override fun onError(e: Throwable) {

                    destory_disposable(disposable)
                }
            })
    }

    override fun uncollect_article_req(id: String) {
        apiServer.uncollect_article(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<Any>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<Any>) {
                    getView().uncollect_article_resp(t)
                }

                override fun onError(e: Throwable) {

                    destory_disposable(disposable)
                }
            })
    }
}