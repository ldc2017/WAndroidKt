package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SearchArticleModel

interface SearchArticleContract {
    interface P {
        fun search_article_req(index: Int, query: String)
        fun collect_article_req(id: String)
        fun uncollect_article_req(id: String)
    }

    interface V : IView {
        fun search_article_resp(dt: BaseModel<SearchArticleModel>)
        fun collect_article_resp(d: BaseModel<Any>)
        fun uncollect_article_resp(d: BaseModel<Any>)
    }
}