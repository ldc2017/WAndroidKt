package com.ldc.wandroidkt.ui.activitys

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.contract.RegisterContract
import org.ldc.module_res.core.BaseActivity
import com.ldc.wandroidkt.databinding.ActivityRegisterBinding
import org.ldc.module_res.http.Api
import com.ldc.wandroidkt.model.BaseModel
import com.ldc.wandroidkt.presenter.RegisterPresenter

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterPresenter>(),
    RegisterContract.V {


    companion object {
        fun actionStart(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(0, 0)
        }
    }

    override fun ui(): Int {
        return R.layout.activity_register
    }

    override fun initPresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

    override fun initView() {
        mBinding.toolBar.title = "注册"
        setSupportActionBar(mBinding.toolBar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun initData() {
        mBinding.eventListner = EventListener()

    }

    override fun register_resp(rt: BaseModel<Any>) {
        rt ?: return
        if (Api.error_code_success == rt.errorCode) {
            finish()
        } else show_toast(str_message = rt.errorMsg)
    }

    override fun show_toast(str_message: String?) {
        ToastUtils.showShort(str_message)

    }

    override fun show_loading(str_message: String?) {
        mBinding.loadingProgress.visibility = View.VISIBLE

    }

    override fun hide_loading() {
        mBinding.loadingProgress.visibility = View.GONE
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
        } else return super.onOptionsItemSelected(item)
    }

    //事件
    inner class EventListener {

        fun register(v: View) {
            val user_name: String = mBinding.etUserName.text.toString()
            val pass_wrod: String = mBinding.etPassWord.text.toString()
            val re_pass_word: String = mBinding.etRePassWord.text.toString()
            if (TextUtils.isEmpty(user_name)) {
                show_toast("账号不能为空")
                return
            }
            if (TextUtils.isEmpty(pass_wrod) || TextUtils.isEmpty(re_pass_word)) {
                show_toast("密码不能为空")
                return
            }
            if (!pass_wrod.equals(re_pass_word, true)) {
                show_toast("密码不一致")
                return
            }

            mPresenter.register_req(user_name, pass_wrod, re_pass_word)

        }
    }


}
