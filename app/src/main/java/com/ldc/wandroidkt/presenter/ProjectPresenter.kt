package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.ProjectContract
import org.ldc.module_res.core.BasePresenter
import com.ldc.wandroidkt.http.Api2Request
import org.ldc.module_res.http.ApiScheduler
import com.ldc.wandroidkt.http.ApiServer
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ProjectPresenter(v: ProjectContract.V) : BasePresenter<ProjectContract.V>(v),
    ProjectContract.P {
    private val apiServer: ApiServer = Api2Request.instances
    override fun get_project_req() {
        getView().show_loading("加载中···")
        apiServer.get_project().compose(ApiScheduler.io2main())
            .subscribe(object : Observer<BaseModel<ProjectModel>> {
                private lateinit var disposable: Disposable
                override fun onComplete() {
                    getView().hide_loading()
                    destory_disposable(disposable)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: BaseModel<ProjectModel>) {
                    getView().get_project_resp(t)
                }

                override fun onError(e: Throwable) {

                    getView().hide_loading()
                    destory_disposable(disposable)
                }
            })

    }
}