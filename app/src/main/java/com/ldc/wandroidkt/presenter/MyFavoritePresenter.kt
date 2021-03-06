package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.MyFavoriteContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.FavoriteArticleListModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyFavoritePresenter constructor(v: MyFavoriteContract.V) :
    BasePresenter<MyFavoriteContract.V>(v), MyFavoriteContract.P {
    private val apiServer = Api2Request.instances
    override fun get_favorite_article_list_req(index: Int) {
        getView().show_loading("加载中···")
        apiServer.get_collect_article_list(index).compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<FavoriteArticleListModel>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<FavoriteArticleListModel>) {
                    getView().get_favorite_article_list_resp(t)
                }

                override fun onError(e: Throwable) {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }


    override fun uncollect_article_req(id: String, originId: String) {
        apiServer.favorite_article_uncollect(id, originId)
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