package com.ldc.wandroidkt.model


import com.google.gson.annotations.SerializedName

class SystemModel : ArrayList<SystemModel.SystemModelItem>() {
    data class SystemModelItem(
        @SerializedName("children")
        val children: List<Children>,
        @SerializedName("courseId")
        val courseId: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("order")
        val order: Int,
        @SerializedName("parentChapterId")
        val parentChapterId: Int,
        @SerializedName("userControlSetTop")
        val userControlSetTop: Boolean,
        @SerializedName("visible")
        val visible: Int
    ) {
        data class Children(
            @SerializedName("children")
            val children: List<Any>,
            @SerializedName("courseId")
            val courseId: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("order")
            val order: Int,
            @SerializedName("parentChapterId")
            val parentChapterId: Int,
            @SerializedName("userControlSetTop")
            val userControlSetTop: Boolean,
            @SerializedName("visible")
            val visible: Int
        )
    }
}