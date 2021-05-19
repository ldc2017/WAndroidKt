package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.ProjectArticleContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectArticleModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProjectArticlePresenter(v: ProjectArticleContract.V) :
    BasePresenter<ProjectArticleContract.V>(v), ProjectArticleContract.P {

    private var apiServer: ApiServer = Api2Request.instances

    override fun get_project_article_req(cid: String, index: Int) {
        getView().show_loading("加载中···")
        apiServer.get_project_article(index, cid).compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<ProjectArticleModel>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<ProjectArticleModel>) {
                    getView().get_project_article_resp(t)
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