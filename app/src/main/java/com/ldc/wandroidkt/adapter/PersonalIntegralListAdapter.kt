package com.ldc.wandroidkt.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.PersonalIntegralListModel

class PersonalIntegralListAdapter :
    BaseQuickAdapter<PersonalIntegralListModel.Data, BaseViewHolder>(R.layout.layout_item_personal_integral_list) {
    override fun convert(helper: BaseViewHolder, item: PersonalIntegralListModel.Data) {
        item ?: return
        val len: Int = item.desc.length
        helper.setText(
            R.id.tv_title,
            item.desc.substring(len - 10, len)
        ).setText(R.id.tv_rank, "${item.coinCount}")

    }
}