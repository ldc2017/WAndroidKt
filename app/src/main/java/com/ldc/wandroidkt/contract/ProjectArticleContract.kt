package com.ldc.wandroidkt.contract

import org.ldc.module_res.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectArticleModel

interface ProjectArticleContract {
    interface V : IView {
        fun get_project_article_resp(dts: BaseModel<ProjectArticleModel>)
        fun collect_article_resp(d: BaseModel<Any>)
        fun uncollect_article_resp(d: BaseModel<Any>)

    }

    interface P {
        fun get_project_article_req(cid: String, index: Int = 1)
        fun collect_article_req(id: String)
        fun uncollect_article_req(id: String)
    }
}