package com.ldc.wandroidkt.ui.fragments

import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.WXNumberArticleAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.WXNumberArticleContract
import org.ldc.module_res.core.BaseFragment
import com.ldc.wandroidkt.databinding.FragmentWxNumberArticleBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.WxNumberArticleModel
import com.ldc.wandroidkt.presenter.WXNumberArticlePresenter
import com.ldc.wandroidkt.ui.activitys.WebViewActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * A simple [Fragment] subclass.
 */
class WXNumberArticleFragment :
    BaseFragment<FragmentWxNumberArticleBinding, WXNumberArticlePresenter>(),
    WXNumberArticleContract.V {


    @Volatile
    private var curr_idnex: Int = 1
    @Volatile
    private var curr_number: String = ""
    @Volatile
    private var curr_name: String = ""
    //
    private val wxNumberArticleAdapter: WXNumberArticleAdapter = WXNumberArticleAdapter()
    //
    private val refresh_code: Int = 0x00
    private var uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dts: MutableList<WxNumberArticleModel.Data> =
                    msg.obj as MutableList<WxNumberArticleModel.Data>
                if (1 == curr_idnex) {
                    wxNumberArticleAdapter.setNewData(dts)
                } else {
                    wxNumberArticleAdapter.addData(dts)
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

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }

    override fun ui(): Int {
        return R.layout.fragment_wx_number_article
    }

    override fun init_presenter(): WXNumberArticlePresenter {
        return WXNumberArticlePresenter(this)

    }

    override fun init_view() {
        curr_number = arguments?.getString("number")!!
        curr_name = arguments?.getString("name")!!
        //
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        init_adapter()

    }

    override fun init_data() {
        mPresenter.get_wx_number_article_req(curr_number, curr_idnex)

    }

    override fun get_wx_number_article_resp(dts: BaseModel<WxNumberArticleModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message: Message = uiHandler.obtainMessage(refresh_code)
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
        if (1 == curr_idnex) {
            mBinding.loadingLayout.loadingLayout.visibility = View.GONE
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

    //数据加载时间
    private val onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_idnex++
            mPresenter.get_wx_number_article_req(curr_number, curr_idnex)

        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_idnex = 1
            mPresenter.get_wx_number_article_req(curr_number, curr_idnex)
        }
    }


    //初始阿虎适配器
    private fun init_adapter() {
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.adapter = wxNumberArticleAdapter
        wxNumberArticleAdapter.setEmptyView(R.layout.layout_view_no_data)
        wxNumberArticleAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val dts: MutableList<WxNumberArticleModel.Data> =
                    adapter.data as MutableList<WxNumberArticleModel.Data> ?: return
                val dt: WxNumberArticleModel.Data = dts[position] ?: return
                WebViewActivity.actionStart(activity!!, dt.link, dt.title)
            }
        })
        wxNumberArticleAdapter.addChildClickViewIds(R.id.ck_favorite)
        wxNumberArticleAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val dts: MutableList<WxNumberArticleModel.Data> =
                    adapter.data as MutableList<WxNumberArticleModel.Data> ?: return
                val dt: WxNumberArticleModel.Data = dts[position] ?: return
                if (!(view as AppCompatCheckBox).isChecked) {
                    mPresenter.uncollect_article_req("${dt.id}")
                } else {
                    mPresenter.collect_article_req("${dt.id}")
                }
            }
        })
    }


}
