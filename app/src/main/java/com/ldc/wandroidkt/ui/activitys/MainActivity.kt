package com.ldc.wandroidkt.ui.activitys

import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
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
    @Volatile
    private var curr_selected_position = 0
    @Volatile
    private var curr_fragment: SupportFragment? = null

    override fun ui(): Int {
        return R.layout.activity_main
    }


    override fun init_presenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun init_view() {
        init_fragment()
        init_bottomNavigationBar()
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
    fun init_bottomNavigationBar() {
        //
        mBinding.bottomNavigationBar
            .addItem(BottomNavigationItem(R.drawable.icon_home, tabs[0]))
            .addItem(BottomNavigationItem(R.drawable.icon_project, tabs[1]))
            .addItem(BottomNavigationItem(R.drawable.icon_system, tabs[2]))
            .addItem(BottomNavigationItem(R.drawable.icon_wx_number, tabs[3]))
            .setActiveColor(R.color.colorPrimary)
            .setInActiveColor(R.color.ccolor_ffffff)
            .setBarBackgroundColor(R.color.ccolor_ffffff)
            .setMode(BottomNavigationBar.MODE_FIXED)
            .setFirstSelectedPosition(0)
            .setTabSelectedListener(onTabSelectedListener)
            .initialise()
    }

    //加载fragment
    private fun init_fragment() {
        try {
            curr_fragment = findFragment(HomeFragment::class.java)
            if (null == curr_fragment) {
                fragments[0] = HomeFragment()
                fragments[1] = ProjectFragment()
                fragments[2] = SystemFragment()
                fragments[3] = WXNumberFragment()

                loadMultipleRootFragment(
                    mBinding.fragmentContainer.id, 0,
                    fragments[0],
                    fragments[1],
                    fragments[2],
                    fragments[3]
                )
            } else {
                fragments[0] = curr_fragment
                fragments[1] = findFragment(ProjectFragment::class.java)
                fragments[2] = findFragment(SystemFragment::class.java)
                fragments[3] = findFragment(WXNumberFragment::class.java)

                showHideFragment(curr_fragment)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //切换事件
    private val onTabSelectedListener = object : BottomNavigationBar.OnTabSelectedListener {
        override fun onTabReselected(position: Int) {

        }

        override fun onTabUnselected(position: Int) {

        }

        override fun onTabSelected(position: Int) {
            switch_fragment(position)
        }
    }

    // 切换fragment
    private fun switch_fragment(position: Int) {
        if (position < 0 || position > fragments.size - 1) {
            return
        }
        val oldFragment: SupportFragment = fragments[curr_selected_position]!!
        val newFragment: SupportFragment = fragments[position]!!
        showHideFragment(newFragment, oldFragment);
        curr_selected_position = position;
        curr_fragment = newFragment;
    }
}
