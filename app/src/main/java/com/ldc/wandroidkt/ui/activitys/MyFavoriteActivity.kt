package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.MenuItem
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.MyFavoriteArticleAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.MyFavoriteContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityMyFavoriteBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.FavoriteArticleListModel
import com.ldc.wandroidkt.presenter.MyFavoritePresenter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class MyFavoriteActivity : BaseActivity<ActivityMyFavoriteBinding, MyFavoritePresenter>(),
    MyFavoriteContract.V {

    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, MyFavoriteActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    //
    @Volatile
    private var curr_index = 0
    private val myFavoriteArticleAdapter: MyFavoriteArticleAdapter = MyFavoriteArticleAdapter()
    //

    private val refresh_code = 0x00
    private val uiHandler = Handler(Handler.Callback { msg ->
        if (refresh_code == msg.what) {
            val dt: MutableList<FavoriteArticleListModel.Data> =
                msg.obj as MutableList<FavoriteArticleListModel.Data>
            dt ?: return@Callback false
            if (0 == curr_index) {
                myFavoriteArticleAdapter.setNewData(dt)
            } else {
                myFavoriteArticleAdapter.addData(dt)
            }
            return@Callback true
        } else false
    })

    override fun ui(): Int {
        return R.layout.activity_my_favorite
    }

    override fun initPresenter(): MyFavoritePresenter {
        return MyFavoritePresenter(this)
    }

    override fun initView() {
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        mBinding.toolBar.title = "我的收藏"
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //
        init_adapter()
    }

    override fun initData() {
        mPresenter.get_favorite_article_list_req(curr_index)
    }

    override fun get_favorite_article_list_resp(dt: BaseModel<FavoriteArticleListModel>) {
        dt ?: return
        if (Api.error_code_success == dt.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dt.data?.datas
            uiHandler.sendMessage(message)

        } else show_toast(dt.errorMsg)
    }


    override fun uncollect_article_resp(d: BaseModel<Any>) {
        d ?: return
        if (Api.error_code_success == d.errorCode) {
            curr_index = 0
            mPresenter.get_favorite_article_list_req(curr_index)
            show_toast("取消收藏")
        } else show_toast(d.errorMsg)
    }


    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)
    }

    override fun show_loading(str_message: String?) {
        if (0 == curr_index) {
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
            true
        } else super.onOptionsItemSelected(item)
    }

    //适配器
    private fun init_adapter() {
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.adapter = myFavoriteArticleAdapter
        myFavoriteArticleAdapter.setEmptyView(R.layout.layout_view_no_data)
        myFavoriteArticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val dts: MutableList<FavoriteArticleListModel.Data> =
                    adapter.data as MutableList<FavoriteArticleListModel.Data>
                dts ?: return
                val dt: FavoriteArticleListModel.Data = dts[position]
                dt ?: return
                WebViewActivity.actionStart(activity!!, dt.link, dt.title)
            }
        })
        myFavoriteArticleAdapter.addChildClickViewIds(R.id.ck_favorite)
        myFavoriteArticleAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val dts: MutableList<FavoriteArticleListModel.Data> =
                    adapter.data as MutableList<FavoriteArticleListModel.Data> ?: return
                val dt: FavoriteArticleListModel.Data = dts[position] ?: return
                mPresenter.uncollect_article_req("${dt.id}", "${dt.originId ?: -1}")

            }
        })
    }

    //刷新事件
    private val onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_index++
            mPresenter.get_favorite_article_list_req(curr_index)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 0
            mPresenter.get_favorite_article_list_req(curr_index)
        }
    }
}
