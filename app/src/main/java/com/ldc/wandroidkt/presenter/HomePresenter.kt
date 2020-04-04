package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.HomeContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BannerModel
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.HomeArticleModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class HomePresenter(v: HomeContract.V) : BasePresenter<HomeContract.V>(v),
    HomeContract.P {
    private var apiServer: ApiServer = Api2Request.instances

    override fun get_banner_req() {
        apiServer.get_banner()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<BaseModel<BannerModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<BannerModel>) {
                    getView().get_banner_resp(dts = t)

                }

                override fun onError(e: Throwable) {
                    destory_disposable(disposable)
                }
            })

    }

    override fun get_home_article(index: Int) {

        getView().show_loading("加载数据···")
        apiServer.get_home_article(index)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<HomeArticleModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<HomeArticleModel>) {
                    getView().get_home_article_resp(t)

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