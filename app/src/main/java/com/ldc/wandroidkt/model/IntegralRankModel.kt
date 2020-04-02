package com.ldc.wandroidkt.model


import com.google.gson.annotations.SerializedName

data class IntegralRankModel(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<Data>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
) {
    data class Data(
        @SerializedName("coinCount")
        val coinCount: Int,
        @SerializedName("level")
        val level: Int,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("username")
        val username: String
    )
}