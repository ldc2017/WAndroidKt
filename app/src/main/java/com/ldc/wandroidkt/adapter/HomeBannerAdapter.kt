package com.ldc.wandroidkt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.BannerModelItem
import com.makeramen.roundedimageview.RoundedImageView
import com.youth.banner.adapter.BannerAdapter
import org.ldc.module_res.uts.GlideUts

class HomeBannerAdapter(data: ArrayList<BannerModelItem>) :
    BannerAdapter<BannerModelItem, HomeBannerAdapter.VH>(data) {


    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        var iconBanner = itemView.findViewById<RoundedImageView>(R.id.icon_banner)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): VH {
        val mView = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.layout_item_banner, parent, false)

        return VH(mView)
    }

    override fun onBindView(holder: VH?, data: BannerModelItem?, position: Int, size: Int) {
        if (null == data) return
        GlideUts.setImageFitCenter(
            holder!!.itemView.context,
            data.imagePath,
            holder.iconBanner
        )
    }
}