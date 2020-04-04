package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.SearchArticleContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SearchArticleModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchArticlePresenter constructor(v: SearchArticleContract.V) :
    BasePresenter<SearchArticleContract.V>(v), SearchArticleContract.P {


    private val apiServer = Api2Request.instances
    override fun search_article_req(index: Int, query: String) {
        getView().show_loading("")
        apiServer.search_article(index, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<SearchArticleModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<SearchArticleModel>) {
                    getView().search_article_resp(t)
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