package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemArticleModel

interface SystemArticleContract {
    interface V : IView {
        fun get_system_article_resp(dts: BaseModel<SystemArticleModel>)
    }

    interface P {
        fun get_system_article_req(index: Int = 0, cid: String)
    }
}