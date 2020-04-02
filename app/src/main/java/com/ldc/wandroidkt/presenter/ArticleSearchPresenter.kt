package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.ArticleSearchContract
import com.ldc.wandroidkt.core.BasePresenter

class ArticleSearchPresenter constructor(v: ArticleSearchContract.V) :
    BasePresenter<ArticleSearchContract.V>(v), ArticleSearchContract.P {
}