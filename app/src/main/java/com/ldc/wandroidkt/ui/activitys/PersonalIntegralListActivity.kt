package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.MenuItem
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.PersonalIntegralListAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.PersonalIntegralListContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityPersonalIntegralListBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.PersonalIntegralListModel
import com.ldc.wandroidkt.presenter.PersonalIntegralListPresenter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class PersonalIntegralListActivity :
    BaseActivity<ActivityPersonalIntegralListBinding, PersonalIntegralListPresenter>(),
    PersonalIntegralListContract.V {


    @Volatile
    private var curr_index: Int = 1
    //适配器
    private val personalIntegralListAdapter: PersonalIntegralListAdapter =
        PersonalIntegralListAdapter()

    private val refresh_code = 0x000
    private val uiHandler = Handler(Handler.Callback { msg ->
        if (refresh_code == msg.what) {
            val dt: MutableList<PersonalIntegralListModel.Data> =
                msg.obj as MutableList<PersonalIntegralListModel.Data>
            dt ?: return@Callback false
            if (1 == curr_index) {
                personalIntegralListAdapter.setNewData(dt)
            } else {
                personalIntegralListAdapter.addData(dt)
            }
            return@Callback true
        } else false
    })

    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, PersonalIntegralListActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    override fun ui(): Int {
        return R.layout.activity_personal_integral_list
    }

    override fun initPresenter(): PersonalIntegralListPresenter {
        return PersonalIntegralListPresenter(this)
    }

    override fun initView() {
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        mBinding.toolBar.title = "个人积分"
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
        init_adapter()

    }

    override fun initData() {
        mPresenter.get_personal_integral_list_req(curr_index)
    }

    override fun get_personal_integral_list_resp(dt: BaseModel<PersonalIntegralListModel>) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (android.R.id.home == item.itemId) {
            finish()
            return true
        } else super.onOptionsItemSelected(item)
    }

    //适配器
    private fun init_adapter() {
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.adapter = personalIntegralListAdapter
        personalIntegralListAdapter.setEmptyView(R.layout.layout_view_no_data)
    }

    //刷新事件
    private val onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_index++
            mPresenter.get_personal_integral_list_req(curr_index)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 1
            mPresenter.get_personal_integral_list_req(curr_index)
        }
    }


}