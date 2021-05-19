package org.ldc.module_depends

object Depends {

    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Vers.kotlin_version}"
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val v4 = "androidx.legacy:legacy-support-v4:1.0.0"
    const val material = "com.google.android.material:material:1.3.0"
    const val annotation = "androidx.annotation:annotation:1.1.0"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val junit = "junit:junit:4.13.2"
    const val test_ext_junit = "androidx.test.ext:junit:1.1.2"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.3.0"

    //
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    const val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"

    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"

    // https://mvnrepository.com/artifact/com.squareup.retrofit2/adapter-rxjava2
    const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"

    //
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.8.0"

    //
    const val rxandroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.18"

    //
    const val fragmentationx = "me.yokeyword:fragmentationx:1.0.2"
    const val fragmentationx_swipeback = "me.yokeyword:fragmentationx-swipeback:1.0.2"

    //
    const val utilcodex = "com.blankj:utilcodex:1.29.0"

    //
    const val navigation_fragment = "androidx.navigation:navigation-fragment:2.3.3"
    const val navigation_ui = "androidx.navigation:navigation-ui:2.3.3"

    //
    const val room_runtime = "androidx.room:room-runtime:2.2.5"
    const val room_compiler = "androidx.room:room-compiler:2.2.5"

    //
    const val multidex = "com.android.support:multidex:1.0.3"

    //
    const val bottom_navigation_bar = "com.ashokvarma.android:bottom-navigation-bar:2.1.0"

    //
    const val refresh_layout_kernel = "com.scwang.smart:refresh-layout-kernel:2.0.1"      //核心必须依赖
    const val refresh_header_classics = "com.scwang.smart:refresh-header-classics:2.0.1"    //经典刷新头
    const val refresh_header_material = "com.scwang.smart:refresh-header-material:2.0.1"    //谷歌刷新头
    const val refresh_footer_classics = "com.scwang.smart:refresh-footer-classics:2.0.1"    //经典加载

    //
    const val BaseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.0"

    //
    const val banner = "com.youth.banner:banner:2.0.0-alpha02"  //预览版

    //
    const val picasso = "com.squareup.picasso:picasso:2.71828"

    //
    const val circleimageview = "de.hdodenhof:circleimageview:3.1.0"

    //
    const val permission = "com.yanzhenjie:permission:2.0.3"

    //
    const val agentweb = "com.just.agentweb:agentweb:4.1.4" // (必选)

    //
    const val flexbox = "com.google.android:flexbox:2.0.1"

    //圆角图片
    const val roundedimageview = "com.makeramen:roundedimageview:2.3.0"

    //接入微信分享
    const val open_wx_sdk = "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:5.5.8"

    //屏幕适配
    const val autosize = "me.jessyan:autosize:1.2.1"

    //label
    const val LabelsView = "com.github.donkingliang:LabelsView:1.6.1"
}