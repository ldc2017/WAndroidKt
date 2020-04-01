package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.WebViewContract
import com.ldc.wandroidkt.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityWebViewBinding
import com.ldc.wandroidkt.presenter.WebViewPresenter

class WebViewActivity : BaseActivity<ActivityWebViewBinding, WebViewPresenter>(),
    WebViewContract.V {


    companion object {
        @Volatile
        private lateinit var strLink: String

        @Volatile
        private lateinit var strName: String

        fun actionStart(act: Activity, curr_link: String, curr_name: String) {
            strLink = curr_link
            strName = curr_name
            val intent = Intent(act, WebViewActivity.javaClass)
            act.startActivity(intent)
        }

    }


    override fun ui(): Int {
        return R.layout.activity_web_view
    }

    override fun init_presenter(): WebViewPresenter {
        return WebViewPresenter(this)
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
