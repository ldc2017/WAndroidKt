package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.LoginContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityLoginBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.model.LoginModel
import com.ldc.wandroidkt.presenter.LoginPresenter

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresenter>(), LoginContract.V {


    companion object {
        fun actionStart(activity: Activity) {
            SPUtils.getInstance().clear()
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    lateinit var curr_user_name: String
    lateinit var curr_user_password: String
    private val refresh_code: Int = 0x000
    private val check_login_code = 0x001
    private val uiHandler: Handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            refresh_code -> {
                val dt: LoginModel = msg.obj as LoginModel
                dt ?: return@Callback false
                cmConstants.saveUserinfo(dt)//保存数据
                SPUtils.getInstance().put(cmConstants.user_name, curr_user_name)
                SPUtils.getInstance().put(cmConstants.user_password, curr_user_password)
                MainActivity.actionStart(activity!!, refresh_code)

                return@Callback true
            }
            check_login_code -> {

                checkLogin()
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
        return R.layout.activity_login

    }

    override fun initPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun initView() {
        mBinding.eventListener = EventListener()
        //
        mBinding.layoutWelcome.visibility = View.VISIBLE
        mBinding.layoutLogin.visibility = View.GONE
        uiHandler.postDelayed({
            uiHandler.sendEmptyMessage(check_login_code)
        }, 2000)

    }

    override fun initData() {

    }

    override fun login_resp(dt: BaseModel<LoginModel>) {
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
        mBinding.loading.visibility = View.VISIBLE
        mBinding.login.isEnabled = false

    }

    override fun hide_loading() {
        mBinding.loading.visibility = View.GONE
        mBinding.login.isEnabled = true

    }

    override fun isBaseOnWidth(): Boolean {
        return cmConstants.isBaseOnWidth

    }

    override fun getSizeInDp(): Float {
        return cmConstants.SizeInDp
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == refresh_code) {
                finish()
            }
        }
    }


    //点击事件
    inner class EventListener {
        // 登录
        fun login(v: View) {
            curr_user_name = mBinding.username.text.toString()
            curr_user_password = mBinding.password.text.toString()
            if (!TextUtils.isEmpty(curr_user_name) && !TextUtils.isEmpty(curr_user_password)) {

                mPresenter.login_req(curr_user_name, curr_user_password)

            } else show_toast("账号或密码不能合法~~~")
        }

        //注册
        fun register(v: View) {
            RegisterActivity.actionStart(activity!!)
        }
    }


    //检测是否登录
    private fun checkLogin() {
        curr_user_name = SPUtils.getInstance().getString(cmConstants.user_name)
        curr_user_password = SPUtils.getInstance().getString(cmConstants.user_password)
        if (!TextUtils.isEmpty(curr_user_name) && !TextUtils.isEmpty(curr_user_password)) {
            mPresenter.login_req(curr_user_name, curr_user_password)
        } else {
            mBinding.layoutWelcome.visibility = View.GONE
            mBinding.layoutLogin.visibility = View.VISIBLE
        }
    }

}
