package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel

interface RegisterContract {
    interface V : IView {

        fun register_resp(rt: BaseModel<Any>)

    }

    interface P {
        fun register_req(
            username: String,
            password: String,
            repassword: String
        )
    }
}