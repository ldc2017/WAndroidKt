package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.LoginContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.LoginModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter constructor(v: LoginContract.V) : BasePresenter<LoginContract.V>(v),
    LoginContract.P {

    private val apiServer: ApiServer = Api2Request.instances
    override fun login_req(username: String, password: String) {
        getView().show_loading("登录中···")
        apiServer.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<LoginModel>> {
                lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d

                }

                override fun onNext(t: BaseModel<LoginModel>) {

                }

                override fun onError(e: Throwable) {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}