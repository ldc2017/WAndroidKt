package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectArticleModel
import com.ldc.wandroidkt.model.WXNumberModel
import com.ldc.wandroidkt.model.WxNumberArticleModel

interface ProjectArticleContract {
    interface V : IView {
        fun get_project_article_resp(dts: BaseModel<ProjectArticleModel>)

    }

    interface P {
        fun get_project_article_req(cid: String, index: Int = 1)
    }
}