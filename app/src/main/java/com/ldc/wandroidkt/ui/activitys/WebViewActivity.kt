package com.ldc.wandroidkt.ui.activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.WebViewContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityWebViewBinding
import com.ldc.wandroidkt.presenter.WebViewPresenter
import com.yanzhenjie.permission.AndPermission

class WebViewActivity : BaseActivity<ActivityWebViewBinding, WebViewPresenter>(),
    WebViewContract.V {

    private lateinit var mAgentWeb: AgentWeb

    companion object {
        @Volatile
        private lateinit var strLink: String

        @Volatile
        private lateinit var strName: String

        fun actionStart(act: Activity, curr_link: String, curr_name: String) {
            strLink = curr_link
            strName = curr_name
            val intent = Intent(act, WebViewActivity::class.java)
            act.startActivity(intent)
            act.overridePendingTransition(0, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init_web()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }


    override fun ui(): Int {
        return R.layout.activity_web_view
    }

    override fun initPresenter(): WebViewPresenter {
        return WebViewPresenter(this)
    }

    override fun initView() {
        mBinding.eventListener = EventListener()
        show_toast(strName)

    }

    @SuppressLint("WrongConstant")
    override fun initData() {
        AndPermission.with(activity).runtime()
            .permission(android.Manifest.permission.ACCESS_NETWORK_STATE).start()

    }

    override fun show_toast(str_message: String?) {
        //ToastUtils.showShort(str_message)

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


    //点击事件
    inner class EventListener {

        //点击返回按钮
        fun onBack(v: View) {
            finish()
        }
    }


    // 初始化web
    private fun init_web() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                mBinding.lineWebView as LinearLayout,
                LinearLayout.LayoutParams(-1, -1)
            )
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(strLink);

    }


}
