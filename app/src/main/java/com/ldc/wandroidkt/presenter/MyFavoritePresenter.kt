package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.MyFavoriteContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.FavoriteArticleListModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyFavoritePresenter constructor(v: MyFavoriteContract.V) :
    BasePresenter<MyFavoriteContract.V>(v), MyFavoriteContract.P {
    private val apiServer=Api2Request.instances
    override fun get_favorite_article_list_req(index: Int) {
        getView().show_loading("加载中···")
        apiServer.get_collect_article_list(index)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<BaseModel<FavoriteArticleListModel>>{
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)
                    
                }

                override fun onSubscribe(d: Disposable) {
                    disposable =d
                    
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
}