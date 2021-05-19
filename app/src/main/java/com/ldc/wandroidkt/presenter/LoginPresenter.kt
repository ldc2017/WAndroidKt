package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.LoginContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.LoginModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class LoginPresenter constructor(v: LoginContract.V) : BasePresenter<LoginContract.V>(v),
    LoginContract.P {

    private val apiServer: ApiServer = Api2Request.instances
    override fun login_req(username: String, password: String) {
        getView().show_loading("登录中···")
        apiServer.login(username, password).compose(ApiScheduler.io2main())
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
                    getView().login_resp(t)
                }

                override fun onError(e: Throwable) {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}