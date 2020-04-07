package com.ldc.wandroidkt.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.IntegralRankModel

class IntegralRankAdapter :
    BaseQuickAdapter<IntegralRankModel.Data, BaseViewHolder>(R.layout.layout_item_integral_rank) {
    override fun convert(helper: BaseViewHolder, item: IntegralRankModel.Data) {
        item ?: return
        helper.setText(
            R.id.tv_title,
            "${item.rank}"
        ).setText(
            R.id.tv_rank,
            item.username
        )

    }
}