package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.MyFavoriteContract
import com.ldc.wandroidkt.core.BasePresenter

class MyFavoritePresenter constructor(v: MyFavoriteContract.V) :
    BasePresenter<MyFavoriteContract.V>(v), MyFavoriteContract.P {
}