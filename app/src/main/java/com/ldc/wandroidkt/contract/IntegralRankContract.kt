package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.IntegralRankModel

interface IntegralRankContract {

    interface V : IView {
        fun get_integral_rank_resp(dt: BaseModel<IntegralRankModel>)
    }

    interface P {
        fun get_integral_rank_req(index: Int = 1)
    }
}