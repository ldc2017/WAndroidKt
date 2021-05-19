package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.MenuItem
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.IntegralRankAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.IntegralRankContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityIntegralRankBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.IntegralRankModel
import com.ldc.wandroidkt.presenter.IntegralRankPresenter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class IntegralRankActivity : BaseActivity<ActivityIntegralRankBinding, IntegralRankPresenter>(),
    IntegralRankContract.V {

    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, IntegralRankActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    //
    @Volatile
    private var curr_index: Int = 1
    //
    private val integralRankAdapter: IntegralRankAdapter = IntegralRankAdapter()
    //

    private val refresh_code: Int = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dt: MutableList<IntegralRankModel.Data> =
                    msg.obj as MutableList<IntegralRankModel.Data>
                dt ?: return@Callback false

                if (1 == curr_index) {
                    integralRankAdapter.setNewData(dt)
                } else {
                    integralRankAdapter.addData(dt)
                }

                return@Callback true
            }
        }


        false
    })

    override fun ui(): Int {
        return R.layout.activity_integral_rank
    }

    override fun initPresenter(): IntegralRankPresenter {
        return IntegralRankPresenter(this)
    }

    override fun initView() {
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        mBinding.toolBar.title = "积分排名"
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //
        init_adapter()

    }

    override fun initData() {
        mPresenter.get_integral_rank_req(curr_index)

    }

    override fun get_integral_rank_resp(dt: BaseModel<IntegralRankModel>) {
        dt ?: return
        if (Api.error_code_success == dt.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dt.data!!.datas
            uiHandler.sendMessage(message)
        } else show_toast(dt.errorMsg)

    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)
    }

    override fun show_loading(str_message: String?) {
        if (1 == curr_index) {
            mBinding.loadingLayout.loadingLayout.visibility = View.VISIBLE
            mBinding.loadingLayout.loadingText.text = str_message
        }
    }

    override fun hide_loading() {
        if (View.GONE != mBinding.loadingLayout.loadingLayout.visibility) {
            mBinding.loadingLayout.loadingLayout.visibility = View.GONE
        }
        if (mBinding.refreshView.state.isOpening) {
            mBinding.refreshView.finishRefresh()
            mBinding.refreshView.finishLoadMore()
        }

    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth
    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }

    //toolbar 返回
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //刷新事件
    private var onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_index++
            mPresenter.get_integral_rank_req(curr_index)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 1
            mPresenter.get_integral_rank_req(curr_index)

        }
    }


    //初始化适配器
    private fun init_adapter() {
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.adapter = integralRankAdapter
        //
        integralRankAdapter.setEmptyView(R.layout.layout_view_no_data)
    }

}
