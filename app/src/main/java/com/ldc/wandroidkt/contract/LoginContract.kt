package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.LoginModel

interface LoginContract {
    interface V : IView {
        fun login_resp(dt: BaseModel<LoginModel>)
    }

    interface P {
        fun login_req(username: String = "", password: String = "")
    }
}