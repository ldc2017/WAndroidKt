package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WXNumberModel
import com.ldc.wandroidkt.model.WxNumberArticleModel

interface WXNumberArticleContract {
    interface V : IView {
        fun get_wx_number_article_resp(dts: BaseModel<WxNumberArticleModel>)

    }

    interface P {
        fun get_wx_number_article_req(number: String, index: Int = 1)
    }
}