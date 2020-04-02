package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.PersonalIntegralContract
import com.ldc.wandroidkt.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityPersonalIntegralBinding
import com.ldc.wandroidkt.presenter.PersonalIntegralPresenter

class PersonalIntegralActivity :
    BaseActivity<ActivityPersonalIntegralBinding, PersonalIntegralPresenter>(),
    PersonalIntegralContract.V {

    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, PersonalIntegralActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    override fun ui(): Int {
        return R.layout.activity_personal_integral
    }

    override fun init_presenter(): PersonalIntegralPresenter {
        return PersonalIntegralPresenter(this)
    }

    override fun init_view() {

    }

    override fun init_data() {

    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)

    }

    override fun show_loading(str_message: String?) {

    }

    override fun hide_loading() {

    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth
    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }


}