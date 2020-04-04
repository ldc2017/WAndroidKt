package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WxNumberArticleModel

interface WXNumberArticleContract {
    interface V : IView {
        fun get_wx_number_article_resp(dts: BaseModel<WxNumberArticleModel>)

        fun collect_article_resp(d: BaseModel<Any>)
        fun uncollect_article_resp(d: BaseModel<Any>)
    }

    interface P {
        fun get_wx_number_article_req(number: String, index: Int = 1)
        fun collect_article_req(id: String)
        fun uncollect_article_req(id: String)
    }
}