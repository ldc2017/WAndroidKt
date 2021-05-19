package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.FavoriteArticleListModel

interface MyFavoriteContract {
    interface V : IView {
        fun get_favorite_article_list_resp(dt: BaseModel<FavoriteArticleListModel>)
        fun uncollect_article_resp(d: BaseModel<Any>)
    }

    interface P {
        fun get_favorite_article_list_req(index: Int = 0)
        fun uncollect_article_req(id: String, originId: String = "-1")
    }
}