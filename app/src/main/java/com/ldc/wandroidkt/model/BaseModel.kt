package com.ldc.wandroidkt.model

class BaseModel<T> {
    var data: T? = null
    var errorCode: Int = 0
    var msg: String? = ""
}