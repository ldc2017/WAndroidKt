package com.ldc.wandroidkt.presenter

import com.ldc.wandroidkt.contract.PersonalIntegralContract
import com.ldc.wandroidkt.core.BasePresenter

class PersonalIntegralPresenter constructor(v: PersonalIntegralContract.V) :
    BasePresenter<PersonalIntegralContract.V>(v), PersonalIntegralContract.P {
}