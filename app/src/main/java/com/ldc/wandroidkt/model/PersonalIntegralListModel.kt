package com.ldc.wandroidkt.model


import com.google.gson.annotations.SerializedName

data class PersonalIntegralListModel(
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
        @SerializedName("date")
        val date: Long,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("reason")
        val reason: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("userName")
        val userName: String
    )
}