package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.PersonalIntegralListModel

interface PersonalIntegralListContract {

    interface V : IView {
        fun get_personal_integral_list_resp(dt: BaseModel<PersonalIntegralListModel>)
    }

    interface P {
        fun get_personal_integral_list_req(index: Int = 1)
    }
}