package org.ldc.module_res.core

interface IView {

    fun show_toast(str_message: String?)

    fun show_loading(str_message: String?)

    fun hide_loading()
}