package com.ldc.wandroidkt.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.SystemModel

class SystemAdapter :
    BaseQuickAdapter<SystemModel.SystemModelItem, BaseViewHolder>(R.layout.layout_item_system) {

    private lateinit var onLabelClick: OnLableClickr<SystemModel.SystemModelItem.Children>

    interface OnLableClickr<T> {
        fun onClick(v: View, dt: T)
    }

    fun addLabelClick(onLabelClick: OnLableClickr<SystemModel.SystemModelItem.Children>) {
        this.onLabelClick = onLabelClick
    }

    override fun convert(helper: BaseViewHolder, item: SystemModel.SystemModelItem) {
        item ?: return
        helper.setText(R.id.tv_title, item.name)
        helper.getView<LabelsView>(R.id.label_view).apply {
            setLabels(item.children) { _, _, data ->
                data.name
            }
            setLabelBackgroundResource(R.drawable.layout_lable_bg)
            setOnLabelClickListener { label, data, position ->
                onLabelClick.onClick(label, item.children[position])

            }
        }

    }
}