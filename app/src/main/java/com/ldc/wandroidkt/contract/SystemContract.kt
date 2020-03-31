package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemModel

interface SystemContract {
    interface V : IView {

        fun get_system_resp(dts: BaseModel<SystemModel>)

    }

    interface P {
        fun get_system_req()
    }
}