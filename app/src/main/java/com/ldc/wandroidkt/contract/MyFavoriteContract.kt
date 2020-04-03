package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.FavoriteArticleListModel

interface MyFavoriteContract {
    interface V : IView {
        fun get_favorite_article_list_resp(dt: BaseModel<FavoriteArticleListModel>)
    }

    interface P {
        fun get_favorite_article_list_req(index: Int = 0)
    }
}