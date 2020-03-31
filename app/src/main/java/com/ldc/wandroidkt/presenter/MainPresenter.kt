package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.MainContract
import com.ldc.wandroidkt.core.BasePresenter
import com.ldc.wandroidkt.core.IView

class MainPresenter(v: MainContract.V) : BasePresenter<MainContract.V>(v), MainContract.P {
}