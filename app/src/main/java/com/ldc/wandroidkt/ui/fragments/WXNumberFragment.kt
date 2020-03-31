package com.ldc.wandroidkt.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.WxNumberAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.WXNumberContract
import com.ldc.wandroidkt.core.BaseFragment
import com.ldc.wandroidkt.databinding.FragmentWxNumberBinding
import com.ldc.wandroidkt.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WXNumberModel
import com.ldc.wandroidkt.model.WXNumberModelItem
import com.ldc.wandroidkt.presenter.WXNumberPresenter
import me.yokeyword.fragmentation.SupportFragment
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class WXNumberFragment : BaseFragment<FragmentWxNumberBinding, WXNumberPresenter>(),
    WXNumberContract.V {


    lateinit var wxNumberAdapter: WxNumberAdapter
    //消息机制
    private val refresh_code: Int = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val data: List<WXNumberModelItem> = msg.obj as List<WXNumberModelItem>
                init_tab(data)
                return@Callback true
            }

        }
        false
    })


    override fun onDestroy() {
        super.onDestroy()
        uiHandler.removeCallbacksAndMessages(null)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            mPresenter.get_wx_number_req()
        }
    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }

    override fun ui(): Int {
        return R.layout.fragment_wx_number
    }

    override fun init_presenter(): WXNumberPresenter {
        return WXNumberPresenter(this)
    }

    override fun init_view() {
    }

    override fun init_data() {
        mPresenter.get_wx_number_req()
    }

    override fun get_wx_number_resp(dts: BaseModel<WXNumberModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message: Message = uiHandler.obtainMessage(refresh_code)
            message.obj = dts.data
            uiHandler.sendMessage(message)
        } else show_toast(dts.msg)
    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)
    }

    override fun show_loading(str_message: String?) {
        mBinding.loadingLayout.loadingLayout.visibility = View.VISIBLE
        mBinding.loadingLayout.loadingText.text = str_message

    }

    override fun hide_loading() {
        mBinding.loadingLayout.loadingLayout.visibility = View.GONE
    }


    //初始化 tab
    private fun init_tab(dts: List<WXNumberModelItem>) {
        uiHandler.post {
            try {

                //
                val tabs: MutableList<String> = mutableListOf()
                val fragments: MutableList<SupportFragment> = mutableListOf()
                //初始化适配器
                wxNumberAdapter = WxNumberAdapter(childFragmentManager)
                dts.forEach {
                    Log.e(TAG, it.name)
                    //页面
                    val fragment: SupportFragment = WXNumberArticleFragment()
                    val data = Bundle()
                    data.putString("number", "${it.id}")
                    data.putString("name", it.name)
                    fragment.arguments = data
                    //页面
                    fragments.add(fragment)
                    //添加标题
                    tabs.add(it.name)
                    mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(it.name))
                }

                wxNumberAdapter.setNewData(fragments, tabs)
                mBinding.fragmentContainer.adapter = wxNumberAdapter
                mBinding.fragmentContainer.offscreenPageLimit = 3
                mBinding.tabLayout.setupWithViewPager(mBinding.fragmentContainer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}
