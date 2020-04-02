package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.PersonalIntegralListContract
import com.ldc.wandroidkt.core.BasePresenter

class PersonalIntegralListPresenter constructor(v: PersonalIntegralListContract.V) :
    BasePresenter<PersonalIntegralListContract.V>(v), PersonalIntegralListContract.P {
}