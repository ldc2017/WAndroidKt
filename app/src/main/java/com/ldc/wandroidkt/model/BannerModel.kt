package com.ldc.wandroidkt.model

class BannerModel : ArrayList<BannerModelItem>()

data class BannerModelItem(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)