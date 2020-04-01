package com.ldc.wandroidkt.http

import com.ldc.wandroidkt.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServer {


    //获取首页banner
    @GET(value = "/banner/json")
    fun get_banner(): Observable<BaseModel<BannerModel>>


    //获取首页文章
    @GET(value = "/article/list/{index}/json")
    fun get_home_article(
        @Path(value = "index") index: Int = 0
    ): Observable<BaseModel<HomeArticleModel>>

    //获取微信公众号
    @GET(value = "/wxarticle/chapters/json")
    fun get_wx_number(): Observable<BaseModel<WXNumberModel>>


    //回去公众号列表
    @GET(value = "/wxarticle/list/{number}/{index}/json")
    fun get_wx_number_list(
        @Path(value = "number") number: String,
        @Path(value = "index") index: Int = 1
    ): Observable<BaseModel<WxNumberArticleModel>>

    //获取项目
    @GET(value = "/project/tree/json")
    fun get_project(): Observable<BaseModel<ProjectModel>>

    //获取项目文章
    @GET(value = "/project/list/{index}/json")
    fun get_project_article(
        @Path(value = "index") index: Int = 1,
        @Query(value = "cid") cid: String
    ): Observable<BaseModel<ProjectArticleModel>>


    //获取体系
    @GET(value = "/tree/json")
    fun get_system(): Observable<BaseModel<SystemModel>>

    //获取体系文章
    @GET(value = "/article/list/{index}/json")
    fun get_system_article(
        @Path(value = "index") index: Int = 0,
        @Query(value = "cid") cid: String
    ): Observable<BaseModel<SystemArticleModel>>

    //登录
    @POST(value = "/user/login")
    fun login(
        @Query(value = "username") user_name: String = "",
        @Query(value = "password") password: String = ""
    ): Observable<BaseModel<LoginModel>>


}