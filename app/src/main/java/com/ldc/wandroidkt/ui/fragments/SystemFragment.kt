package com.ldc.wandroidkt.ui.fragments

import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.SystemAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.SystemContract
import org.ldc.module_res.core.BaseFragment
import com.ldc.wandroidkt.databinding.FragmentSystemBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemModel
import com.ldc.wandroidkt.presenter.SystemPresenter
import com.ldc.wandroidkt.ui.activitys.SystemArticleActivity

/**
 * A simple [Fragment] subclass.
 */
class SystemFragment : BaseFragment<FragmentSystemBinding, SystemPresenter>(), SystemContract.V {
    //
    private val systemAdapter: SystemAdapter = SystemAdapter()
    //
    private val refresh_code: Int = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dts: MutableList<SystemModel.SystemModelItem> =
                    msg.obj as MutableList<SystemModel.SystemModelItem>
                systemAdapter.setNewData(dts)

                return@Callback true
            }
        }
        false
    })

    override fun onDestroy() {
        super.onDestroy()
        uiHandler.removeCallbacksAndMessages(null)
    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }

    override fun ui(): Int {
        return R.layout.fragment_system
    }

    override fun init_presenter(): SystemPresenter {
        return SystemPresenter(this)
    }

    override fun init_view() {
        init_adapter()
    }

    override fun init_data() {
        mPresenter.get_system_req()
    }

    override fun get_system_resp(dts: BaseModel<SystemModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dts.data
            uiHandler.sendMessage(message)
        } else show_toast(dts.errorMsg)


    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)
    }

    override fun show_loading(str_message: String?) {
        mBinding.layoutLoading.loadingLayout.visibility = View.VISIBLE
        mBinding.layoutLoading.loadingText.text = str_message

    }

    override fun hide_loading() {
        mBinding.layoutLoading.loadingLayout.visibility = View.GONE
    }


    //初始化适配器
    private fun init_adapter() {
        systemAdapter.addLabelClick(object :
            SystemAdapter.OnLableClickr<SystemModel.SystemModelItem.Children> {
            override fun onClick(v: View, dt: SystemModel.SystemModelItem.Children) {
                //show_toast(dt.name)
                SystemArticleActivity.actionStart(activity!!, "${dt.id}", dt.name)
            }

        })
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.adapter = systemAdapter
        systemAdapter.setEmptyView(R.layout.layout_view_no_data)

    }

}
