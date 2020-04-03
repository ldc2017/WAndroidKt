package com.ldc.wandroidkt.ui.activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.navigation.NavigationView
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.MainContract
import com.ldc.wandroidkt.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityMainBinding
import com.ldc.wandroidkt.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.PersonalIntegralModel
import com.ldc.wandroidkt.presenter.MainPresenter
import com.ldc.wandroidkt.ui.fragments.HomeFragment
import com.ldc.wandroidkt.ui.fragments.ProjectFragment
import com.ldc.wandroidkt.ui.fragments.SystemFragment
import com.ldc.wandroidkt.ui.fragments.WXNumberFragment
import com.yanzhenjie.permission.AndPermission
import me.yokeyword.fragmentation.SupportFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.V {


    companion object {
        fun actionStart(act: Activity, requestCode: Int = 0x000) {
            val intent = Intent(act, MainActivity::class.java)
            act.startActivityForResult(intent, requestCode)
            act.overridePendingTransition(0, 0)
        }
    }

    private val refresh_code: Int = 0x000
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dt: PersonalIntegralModel = msg.obj as PersonalIntegralModel
                dt ?: return@Callback false
                mBinding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_rank).text =
                    "排名:${dt.rank}"
                mBinding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_coinCount).text =
                    "积分:${dt.coinCount}"

                return@Callback true
            }
        }

        false
    })

    //
    private lateinit var drawerToggle: ActionBarDrawerToggle

    //
    private val tabs: Array<String> = arrayOf<String>("首页", "项目", "体系", "公众号")
    private val fragments: Array<SupportFragment?> = arrayOfNulls<SupportFragment>(tabs.size)
    @Volatile
    private var curr_selected_position: Int = 0
    @Volatile
    private var curr_fragment: SupportFragment? = null


    override fun ui(): Int {
        return R.layout.activity_main
    }


    override fun init_presenter(): MainPresenter {
        return MainPresenter(this)
    }

    @SuppressLint("WrongConstant")
    override fun init_view() {
        setResult(Activity.RESULT_OK)
        init_fragment()
        init_bottomNavigationBar()

        AndPermission.with(activity).runtime()
            .permission(android.Manifest.permission.ACCESS_NETWORK_STATE).start()
        //
        mBinding.navigationView.itemIconTintList = null
        mBinding.navigationView.setNavigationItemSelectedListener(navItemSelectedListener)
        //
        setSupportActionBar(mBinding.mainContent.toolBar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init_drawer()
    }

    override fun init_data() {

        mBinding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.tv_user_name).text =
            cmConstants.get_user_username()

    }

    override fun get_personal_integral_resp(dt: BaseModel<PersonalIntegralModel>) {
        dt ?: return
        if (Api.error_code_success == dt.errorCode) {
            val message = uiHandler.obtainMessage(refresh_code)
            message.obj = dt.data
            uiHandler.sendMessage(message)
        } else show_toast(dt.errorMsg)
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
        mBinding.mainContent.toolBar.title = tabs[curr_selected_position]
        //
        mBinding.mainContent.bottomNavigationBar
            .addItem(BottomNavigationItem(R.drawable.icon_home, tabs[0]))
            .addItem(BottomNavigationItem(R.drawable.icon_project, tabs[1]))
            .addItem(BottomNavigationItem(R.drawable.icon_system, tabs[2]))
            .addItem(BottomNavigationItem(R.drawable.icon_wx_number, tabs[3]))
            .setInActiveColor(R.color.color_aaa)
            .setActiveColor(R.color.color_ffffff)
            .setBarBackgroundColor(R.color.color_f00)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
            .setMode(BottomNavigationBar.MODE_FIXED)
            .setFirstSelectedPosition(curr_selected_position)
            .setTabSelectedListener(onTabSelectedListener)
            .initialise()
    }

    //初始化侧滑
    private fun init_drawer() {
        drawerToggle = object : ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            mBinding.mainContent.toolBar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                mPresenter.get_personal_integral_req()//获取个人积分
                super.onDrawerOpened(drawerView)
                print("打开抽屉")
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                print("关闭抽屉")
            }
        }
        drawerToggle.syncState()
        mBinding.drawerLayout.addDrawerListener(drawerToggle)
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
                    mBinding.mainContent.fragmentContainer.id, 0,
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
            mBinding.mainContent.toolBar.title = tabs[position]
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

    //侧边栏点击事件
    private val navItemSelectedListener: NavigationView.OnNavigationItemSelectedListener =
        object : NavigationView.OnNavigationItemSelectedListener {
            @SuppressLint("WrongConstant")
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                mBinding.drawerLayout.closeDrawer(Gravity.START)
                when (item.itemId) {
                    R.id.item_personal_integral -> {
                        PersonalIntegralListActivity.actionStart(activity!!)
                        return true
                    }
                    R.id.item_integral_rank -> {
                        IntegralRankActivity.actionStart(activity!!)
                        return true
                    }
                    R.id.item_my_favorite -> {
                        MyFavoriteActivity.actionStart(activity!!)
                        return true
                    }
                    R.id.item_search -> {
                        show_toast("敬请期待")
                        // ArticleSearchActivity.actionStart(activity!!)
                        return true
                    }
                    R.id.item_about_author -> {
                        show_toast("作者")
                        return true
                    }
                    R.id.item_about_app -> {
                        show_toast("关于程序")
                        return true
                    }
                }
                return false
            }
        }


    @Volatile
    private var last_time: Long = 0L

    override fun onBackPressedSupport() {
        val curr_time = System.currentTimeMillis()
        if (curr_time - last_time > 2000) {
            show_toast("再点一次退出程序")
            last_time = curr_time
        } else {
            last_time = 0
            showDialog()
        }
    }


    private fun showDialog() {
        val items: Array<String> = arrayOf("切换账号", "退出程序", "取消操作")
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle("操作")
        dialog.setItems(items, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        LoginActivity.actionStart(activity!!)
                        finish()
                    }
                    1 -> finish()
                }

            }
        })
        dialog.create()
        dialog.show()
    }
}
