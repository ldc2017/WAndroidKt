package com.ldc.wandroidkt.ui.activitys

import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.adapter.MainViewPagerAdapter
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.MainContract
import com.ldc.wandroidkt.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityMainBinding
import com.ldc.wandroidkt.presenter.MainPresenter
import com.ldc.wandroidkt.ui.fragments.HomeFragment
import com.ldc.wandroidkt.ui.fragments.ProjectFragment
import com.ldc.wandroidkt.ui.fragments.SystemFragment
import com.ldc.wandroidkt.ui.fragments.WXNumberFragment
import me.yokeyword.fragmentation.SupportFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.V {

    private val tabs: Array<String> = arrayOf<String>("首页", "项目", "体系", "公众号")
    private val fragments: Array<SupportFragment?> = arrayOfNulls<SupportFragment>(tabs.size)
    private var fragmentAdapter: MainViewPagerAdapter? = null

    override fun ui(): Int {
        return R.layout.activity_main
    }


    override fun init_presenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun init_view() {
        init_tabs()
    }

    override fun init_data() {

    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)
    }

    override fun show_loading(str_message: String?) {

    }

    override fun hide_loading() {

    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }


    //初始化tabs
    fun init_tabs() {
        //
        fragments[0] = HomeFragment()
        fragments[1] = ProjectFragment()
        fragments[2] = SystemFragment()
        fragments[3] = WXNumberFragment()
        //
        tabs.forEach {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(it))
        }
        if (null == fragmentAdapter) fragmentAdapter = MainViewPagerAdapter(supportFragmentManager)
        fragmentAdapter!!.setNewData(fragments = fragments, tabs = tabs)
        mBinding.fragmentContainer.adapter = fragmentAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.fragmentContainer)

    }
}
