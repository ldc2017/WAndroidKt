package com.ldc.wandroidkt.model


import com.google.gson.annotations.SerializedName

data class PersonalIntegralModel(
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