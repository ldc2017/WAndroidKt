package org.ldc.module_res.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.jessyan.autosize.internal.CustomAdapt
import me.yokeyword.fragmentation.SupportFragment
import java.lang.Exception

abstract class BaseFragment<B : ViewDataBinding, P : BasePresenter<*>> : SupportFragment(),
    IView, CustomAdapt {
    open val TAG: String = this.javaClass.name
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


    //1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, ui(), container, false)
        return mBinding.root
    }

    //2
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            mPresenter = init_presenter()
            mPresenter.onActivityCreated(savedInstanceState)
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
}