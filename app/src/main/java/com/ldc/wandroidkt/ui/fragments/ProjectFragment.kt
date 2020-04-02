package com.ldc.wandroidkt.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.ProjectAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.ProjectContract
import com.ldc.wandroidkt.core.BaseFragment
import com.ldc.wandroidkt.databinding.FragmentProjectBinding
import com.ldc.wandroidkt.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.ProjectModel
import com.ldc.wandroidkt.presenter.ProjectPresenter
import me.yokeyword.fragmentation.SupportFragment

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding, ProjectPresenter>(),
    ProjectContract.V {

    private lateinit var projectAdapter: ProjectAdapter


    private val refresh_code = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dts: MutableList<ProjectModel.ProjectModelItem> =
                    msg.obj as MutableList<ProjectModel.ProjectModelItem>
                init_tab(dts)
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
        return R.layout.fragment_project
    }


    override fun init_presenter(): ProjectPresenter {
        return ProjectPresenter(this)
    }

    override fun init_view() {

    }

    override fun init_data() {
        mPresenter.get_project_req()

    }

    override fun get_project_resp(dts: BaseModel<ProjectModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dts.data
            uiHandler.sendMessage(message)
        } else {
            show_toast(dts.errorMsg)
        }

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
    private fun init_tab(dts: List<ProjectModel.ProjectModelItem>) {
        uiHandler.post {
            try {

                //
                val tabs: MutableList<String> = mutableListOf()
                val fragments: MutableList<SupportFragment> = mutableListOf()
                //初始化适配器
                projectAdapter = ProjectAdapter(childFragmentManager)
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

                projectAdapter.setNewData(fragments, tabs)
                mBinding.fragmentContainer.adapter = projectAdapter
                mBinding.fragmentContainer.offscreenPageLimit = 6
                mBinding.tabLayout.setupWithViewPager(mBinding.fragmentContainer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}
