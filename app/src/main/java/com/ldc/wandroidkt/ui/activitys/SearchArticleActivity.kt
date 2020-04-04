package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.SearchArticleAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.SearchArticleContract
import com.ldc.wandroidkt.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivitySearchArticleBinding
import com.ldc.wandroidkt.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.SearchArticleModel
import com.ldc.wandroidkt.presenter.SearchArticlePresenter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

class SearchArticleActivity : BaseActivity<ActivitySearchArticleBinding, SearchArticlePresenter>(),
    SearchArticleContract.V {

    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, SearchArticleActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }


    //适配器
    private val searchArticleAdapter = SearchArticleAdapter()
    //页数
    @Volatile
    private var curr_index: Int = 0
    @Volatile
    private var curr_query: String = ""

    private val refresh_code = 0x000
    private val uiHandler = Handler(Handler.Callback { msg ->

        if (refresh_code == msg.what) {
            val dts: MutableList<SearchArticleModel.Data> =
                msg.obj as MutableList<SearchArticleModel.Data>
            dts ?: return@Callback false
            if (0 == curr_index) {
                searchArticleAdapter.setNewData(dts)
            } else {
                searchArticleAdapter.addData(dts)
            }
            return@Callback true
        }

        false
    })


    override fun ui(): Int {
        return R.layout.activity_search_article
    }


    override fun init_presenter(): SearchArticlePresenter {
        return SearchArticlePresenter(this)
    }

    override fun init_view() {
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        mBinding.toolBar.title = "文章搜索"
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        //
        init_adapter()
    }

    override fun init_data() {
        mPresenter.search_article_req(curr_index, curr_query)

    }

    override fun search_article_resp(dt: BaseModel<SearchArticleModel>) {
        dt ?: return
        if (Api.error_code_success == dt.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dt.data!!.datas
            uiHandler.sendMessage(message)
        } else show_toast(dt.errorMsg)

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
            mBinding.loading.visibility = View.VISIBLE

        }


    }

    override fun hide_loading() {
        if (View.GONE != mBinding.loading.visibility) {
            mBinding.loading.visibility = View.GONE
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_article_search_view, menu)
        init_search_view(menu!!.findItem(R.id.menu_search_view))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (android.R.id.home == item.itemId) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }

    //搜索
    private fun init_search_view(item: MenuItem) {
        item ?: return
        val searchView: SearchView = MenuItemCompat.getActionView(item) as SearchView
        //设置搜索栏的默认提示
        searchView.setQueryHint("请输入文章关键字")
        //默认刚进去就打开搜索栏
        searchView.setIconified(true)
        //设置提交按钮是否可见
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                curr_index = 0
                curr_query = query!!
                mPresenter.search_article_req(curr_index, curr_query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    //适配器
    private fun init_adapter() {

        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.adapter = searchArticleAdapter
        searchArticleAdapter.setEmptyView(R.layout.layout_view_no_data)
        searchArticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val dts: MutableList<SearchArticleModel.Data> =
                    adapter.data as MutableList<SearchArticleModel.Data> ?: return
                val dt: SearchArticleModel.Data = dts[position] ?: return
                WebViewActivity.actionStart(activity!!, dt.link, dt.title)
            }
        })
        searchArticleAdapter.addChildClickViewIds(R.id.ck_favorite)
        searchArticleAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val dts: MutableList<SearchArticleModel.Data> =
                    adapter.data as MutableList<SearchArticleModel.Data> ?: return
                val dt: SearchArticleModel.Data = dts[position] ?: return
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
            mPresenter.search_article_req(curr_index, curr_query)

        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 0
            mPresenter.search_article_req(curr_index, curr_query)
        }
    }

}
