package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BannerModel
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.HomeArticleModel
import com.ldc.wandroidkt.model.TopArticleModel

interface HomeContract {
    interface V : IView {
        fun get_banner_resp(dts: BaseModel<BannerModel>)
        fun get_home_article_resp(dts: BaseModel<HomeArticleModel>)
        fun get_top_article_resp(dts: BaseModel<TopArticleModel>)


        fun collect_article_resp(d: BaseModel<Any>)
        fun uncollect_article_resp(d: BaseModel<Any>)


    }

    interface P {
        fun get_banner_req()
        fun get_home_article(index: Int)
        fun get_top_article_req()

        fun collect_article_req(id: String)
        fun uncollect_article_req(id: String)
    }
}