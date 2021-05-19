package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.SystemArticleAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.SystemArticleContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivitySystemArticleBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SystemArticleModel
import com.ldc.wandroidkt.presenter.SystemArticlePresenter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class SystemArticleActivity : BaseActivity<ActivitySystemArticleBinding, SystemArticlePresenter>(),
    SystemArticleContract.V {


    private val systemArticleAdapter: SystemArticleAdapter = SystemArticleAdapter()
    //
    @Volatile
    private var curr_index: Int = 0

    companion object {
        @Volatile
        private var curr_cid: String = ""
        @Volatile
        private var curr_name: String = ""

        fun actionStart(activity: Activity, cid: String, name: String) {
            curr_cid = cid
            curr_name = name
            val intent = Intent(activity, SystemArticleActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    //
    private val refresh_code: Int = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dts: MutableList<SystemArticleModel.Data> =
                    msg.obj as MutableList<SystemArticleModel.Data>
                if (0 == curr_index) {
                    systemArticleAdapter.setNewData(dts)
                } else {
                    systemArticleAdapter.addData(dts)
                }

                return@Callback true
            }
        }

        false
    })

    override fun onDestroy() {
        super.onDestroy()
        uiHandler.removeCallbacksAndMessages(null)
    }

    override fun ui(): Int {
        return R.layout.activity_system_article
    }

    override fun initPresenter(): SystemArticlePresenter {
        return SystemArticlePresenter(this)
    }

    override fun initView() {
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.title = curr_name
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)

        init_adapter()
    }

    override fun initData() {
        mPresenter.get_system_article_req(curr_index, curr_cid)
    }

    override fun get_system_article_resp(dts: BaseModel<SystemArticleModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dts.data!!.datas
            uiHandler.sendMessage(message)
        } else show_toast(dts.errorMsg)

    }

    override fun collect_article_resp(d: BaseModel<Any>) {
        d ?: return
        if (Api.error_code_success == d.errorCode) {
            show_toast("已收藏")
        } else show_toast(d.errorMsg)

    }

    override fun uncollect_article_resp(d: BaseModel<Any>) {
        d ?: return
        if (Api.error_code_success == d.errorCode) {
            show_toast("取消收藏")
        } else show_toast(d.errorMsg)
    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)

    }

    override fun show_loading(str_message: String?) {
        if (0 == curr_index) {
            mBinding.loadingLayout.loadingLayout.visibility = View.VISIBLE
            mBinding.loadingLayout.loadingLayout.visibility = View.GONE
        }

    }

    override fun hide_loading() {
        if (View.GONE != mBinding.loadingLayout.loadingLayout.visibility) {
            mBinding.loadingLayout.loadingLayout.visibility = View.GONE
        }
        if (mBinding.refreshView.state.isOpening) {
            mBinding.refreshView.finishLoadMore()
            mBinding.refreshView.finishRefresh()
        }

    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }


    //初始化适配器
    private fun init_adapter() {
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.adapter = systemArticleAdapter
        systemArticleAdapter.setEmptyView(R.layout.layout_view_no_data)
        systemArticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val dts: MutableList<SystemArticleModel.Data> =
                    adapter.data as MutableList<SystemArticleModel.Data> ?: return
                val dt: SystemArticleModel.Data = dts[position] ?: return
                WebViewActivity.actionStart(activity!!, dt.link, dt.title)
            }
        })
        systemArticleAdapter.addChildClickViewIds(R.id.ck_favorite)
        systemArticleAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val dts: MutableList<SystemArticleModel.Data> =
                    adapter.data as MutableList<SystemArticleModel.Data> ?: return
                val dt: SystemArticleModel.Data = dts[position] ?: return
                if (!(view as AppCompatCheckBox).isChecked) {
                    mPresenter.uncollect_article_req("${dt.id}")
                } else {
                    mPresenter.collect_article_req("${dt.id}")
                }
            }
        })
    }

    //刷新事件
    private val onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_index++
            mPresenter.get_system_article_req(curr_index, curr_cid)

        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 0
            mPresenter.get_system_article_req(curr_index, curr_cid)
        }
    }

    //toolbar 返回
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
