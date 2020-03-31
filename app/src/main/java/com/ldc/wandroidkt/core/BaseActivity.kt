package com.ldc.wandroidkt.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.jessyan.autosize.internal.CustomAdapt
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseActivity<B : ViewDataBinding, P : BasePresenter<*>> : SupportActivity(),
    IView, CustomAdapt {
    open var activity: Activity? = null
    open lateinit var mBinding: B
    open lateinit var mPresenter: P


    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        mPresenter.onDestroy()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            activity = this
            handleIntent(intent)
            mBinding = DataBindingUtil.setContentView(this, ui())
            mPresenter = init_presenter()
            init_view()
            init_data()
        } catch (e: Exception) {
            handleError(e)
            e.printStackTrace()
        }
    }

    abstract fun ui(): Int
    abstract fun init_presenter(): P
    abstract fun init_view()
    abstract fun init_data()
    open fun handleError(e: Exception) {}
    open fun handleIntent(it: Intent?) {}
}