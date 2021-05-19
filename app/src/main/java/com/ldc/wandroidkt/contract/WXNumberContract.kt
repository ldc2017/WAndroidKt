package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WXNumberModel

interface WXNumberContract {
    interface V : IView {
        fun get_wx_number_resp(dts: BaseModel<WXNumberModel>)

    }

    interface P {
        fun get_wx_number_req()
    }
}