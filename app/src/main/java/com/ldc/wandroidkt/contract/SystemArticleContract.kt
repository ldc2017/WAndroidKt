package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemArticleModel

interface SystemArticleContract {
    interface V : IView {
        fun get_system_article_resp(dts: BaseModel<SystemArticleModel>)

        fun collect_article_resp(d: BaseModel<Any>)
        fun uncollect_article_resp(d: BaseModel<Any>)
    }

    interface P {
        fun get_system_article_req(index: Int = 0, cid: String)
        fun collect_article_req(id: String)
        fun uncollect_article_req(id: String)
    }
}