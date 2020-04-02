package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.PersonalIntegralModel

interface MainContract {
    interface V : IView {
        fun get_personal_integral_resp(dt: BaseModel<PersonalIntegralModel>)
    }

    interface P {
        fun get_personal_integral_req()
    }
}