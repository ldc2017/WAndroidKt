package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
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