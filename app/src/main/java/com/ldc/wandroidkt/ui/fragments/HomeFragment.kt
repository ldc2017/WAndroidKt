package com.ldc.wandroidkt.ui.fragments

import android.os.Handler
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.HomeArticleAdapter
import com.ldc.wandroidkt.adapter.HomeBannerAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.HomeContract
import org.ldc.module_res.core.BaseFragment
import com.ldc.wandroidkt.databinding.FragmentHomeBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.*
import com.ldc.wandroidkt.presenter.HomePresenter
import com.ldc.wandroidkt.ui.activitys.WebViewActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePresenter>(), HomeContract.V {


    private val home_article_adapter: HomeArticleAdapter =
        HomeArticleAdapter()
    @Volatile
    private var curr_index = 0
    private val refresh_banner_code: Int = 0x000
    private val refresh_home_article_code: Int = 0x001
    private var uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_banner_code -> {
                val dts: BannerModel = msg.obj as BannerModel
                init_banner(dts)
                return@Callback true
            }
            refresh_home_article_code -> {
                val dts: MutableList<HomeArticleModel.Data> =
                    msg.obj as MutableList<HomeArticleModel.Data>
                when (curr_index) {
                    0 -> {
                        home_article_adapter.setNewData(dts)
                    }
                    1 -> {
                        home_article_adapter.addData(dts)
                    }
                    else -> {
                        home_article_adapter.addData(dts)
                    }
                }
                return@Callback true
            }


        }
        false
    })


    override fun onStart() {
        super.onStart()
        mBinding.banner.start()
    }


    override fun onStop() {
        super.onStop()
        mBinding.banner.stop()
    }

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
        return R.layout.fragment_home
    }


    override fun init_presenter(): HomePresenter {
        return HomePresenter(this)
    }

    override fun init_view() {
        mBinding.refreshView.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener)
        mBinding.refreshView.setEnableLoadMore(true)
        //
        init_adapter()
    }

    override fun init_data() {
        mPresenter.get_banner_req()
        mPresenter.get_top_article_req()
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


    override fun get_banner_resp(dts: BaseModel<BannerModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_banner_code)
            message.obj = dts.data
            uiHandler.sendMessage(message)
        } else
            show_toast(dts.errorMsg)

    }

    override fun get_home_article_resp(dts: BaseModel<HomeArticleModel>) {
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_home_article_code)
            message.obj = dts.data!!.datas
            uiHandler.sendMessage(message)
        } else {
            show_toast(dts.errorMsg)
        }

    }

    override fun get_top_article_resp(dts: BaseModel<TopArticleModel>) {
        //
        dts ?: return
        if (Api.error_code_success == dts.errorCode) {
            val message = uiHandler.obtainMessage(refresh_home_article_code)
            message.obj = dts.data
            uiHandler.sendMessage(message)
        } else show_toast(dts.errorMsg)
        //获取文章
        curr_index = 1
        mPresenter.get_home_article(curr_index)
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


    // 刷新事件
    private var onRefreshLoadMoreListener = object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            curr_index++
            mPresenter.get_home_article(curr_index)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            curr_index = 1
            mPresenter.get_home_article(curr_index)
        }

    }

    //初始化
    fun init_banner(dts: BannerModel) {
        //--------------------------详细使用-------------------------------
        mBinding.banner.setAdapter(HomeBannerAdapter(dts))
        mBinding.banner.setIndicator(CircleIndicator(activity))
        mBinding.banner.setIndicatorSelectedColorRes(R.color.color_e6ff5252)//选中
        mBinding.banner.setIndicatorNormalColorRes(R.color.color_b3ff5252)//未选中
        mBinding.banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
        mBinding.banner.setDelayTime(3000)
        mBinding.banner.setOnBannerListener(object : OnBannerListener<BannerModelItem> {
            override fun onBannerChanged(position: Int) {
                // TODO(不操作)
            }

            override fun OnBannerClick(data: BannerModelItem?, position: Int) {
                WebViewActivity.actionStart(activity!!, data!!.url, data.title)
            }
        })
    }


    //初始化事件适配器
    fun init_adapter() {
        mBinding.dataList.setItemViewCacheSize(10)
        mBinding.dataList.setHasFixedSize(true)
        mBinding.dataList.adapter = home_article_adapter
        home_article_adapter.setEmptyView(R.layout.layout_view_no_data)
        home_article_adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val dts: MutableList<HomeArticleModel.Data> =
                    adapter.data as MutableList<HomeArticleModel.Data> ?: return
                val dt: HomeArticleModel.Data = dts[position] ?: return
                WebViewActivity.actionStart(activity!!, dt.link, dt.title)
            }
        })
        home_article_adapter.addChildClickViewIds(R.id.ck_favorite)
        home_article_adapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val dts: MutableList<HomeArticleModel.Data> =
                    adapter.data as MutableList<HomeArticleModel.Data> ?: return
                val dt: HomeArticleModel.Data = dts[position] ?: return
                if (!(view as AppCompatCheckBox).isChecked) {
                    mPresenter.uncollect_article_req("${dt.id}")
                } else {
                    mPresenter.collect_article_req("${dt.id}")
                }
            }
        })

    }

}
