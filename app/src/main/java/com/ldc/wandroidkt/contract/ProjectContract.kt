package com.ldc.wandroidkt.contract

import com.ldc.wandroidkt.core.IView
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectModel

interface ProjectContract {
    interface V : IView {
        fun get_project_resp(dts: BaseModel<ProjectModel>);
    }

    interface P {
        fun get_project_req();
    }
}