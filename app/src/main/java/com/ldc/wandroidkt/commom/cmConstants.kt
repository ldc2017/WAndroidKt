package com.ldc.wandroidkt.commom

import com.blankj.utilcode.util.SPUtils
import com.ldc.wandroidkt.model.LoginModel

object cmConstants {
    const val SizeInDp: Float = 667f
    const val isBaseOnWidth: Boolean = false


    //
    const val user_name: String = "user_name"
    const val user_password: String = "user_password"

    //
    private const val key_admin = "admin"
    private const val key_email = "email"
    private const val key_icon = "icon"
    private const val key_id = "id"
    private const val key_nickname = "nickname"
    private const val key_password = "password"
    private const val key_publicName = "publicName"
    private const val key_token = "token"
    private const val key_type = "type"
    private const val key_username = "username"
    //

    fun saveUserinfo(dt: LoginModel) {
        dt ?: return
        SPUtils.getInstance().put(key_admin, dt.admin)
        SPUtils.getInstance().put(key_email, dt.email)
        SPUtils.getInstance().put(key_icon, dt.icon)
        SPUtils.getInstance().put(key_id, "${dt.id}")
        SPUtils.getInstance().put(key_nickname, dt.nickname)
        SPUtils.getInstance().put(key_password, dt.password)
        SPUtils.getInstance().put(key_publicName, dt.publicName)
        SPUtils.getInstance().put(key_token, dt.token)
        SPUtils.getInstance().put(key_type, "${dt.type}")
        SPUtils.getInstance().put(key_username, dt.username + "")
    }

    fun get_user_admin(): Boolean {
        return SPUtils.getInstance().getBoolean(key_admin, false)
    }

    fun get_user_email(): String {
        return SPUtils.getInstance().getString(key_email, "")
    }

    fun get_user_icon(): String {
        return SPUtils.getInstance().getString(key_icon, "")
    }

    fun get_user_id(): String {
        return SPUtils.getInstance().getString(key_id, "")
    }

    fun get_user_nickname(): String {
        return SPUtils.getInstance().getString(key_nickname, "")
    }

    fun get_user_password(): String {
        return SPUtils.getInstance().getString(key_password, "")
    }

    fun get_user_publicName(): String {
        return SPUtils.getInstance().getString(key_publicName, "")
    }

    fun get_user_token(): String {
        return SPUtils.getInstance().getString(key_token, "")
    }

    fun get_user_type(): String {
        return SPUtils.getInstance().getString(key_type, "")
    }

    fun get_user_username(): String {
        return SPUtils.getInstance().getString(key_username, "")
    }


}