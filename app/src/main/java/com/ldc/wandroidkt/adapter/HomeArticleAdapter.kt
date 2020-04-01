package com.ldc.wandroidkt.adapter

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.text.HtmlCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.HomeArticleModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class HomeArticleAdapter :

    BaseQuickAdapter<HomeArticleModel.Data, BaseViewHolder>(R.layout.layout_item_article_image) {

    val TAG: String = HomeArticleAdapter::class.java.name

    override fun convert(helper: BaseViewHolder, modelItem: HomeArticleModel.Data) {

        //描述
        val str_html = HtmlCompat.fromHtml(modelItem.desc, HtmlCompat.FROM_HTML_MODE_COMPACT)
        //事件

        helper.setText(R.id.tv_title, modelItem.chapterName)
            .setText(R.id.tv_content, modelItem.title + str_html)
            .setText(R.id.tv_time_author, modelItem.author + " " + modelItem.niceShareDate)
        val ck: AppCompatCheckBox = helper.getView(R.id.ck_favorite)
        ck.isChecked = modelItem.collect
        //
        Log.e(TAG, "----图片:" + modelItem.envelopePic)
        //
        //
        if (!TextUtils.isEmpty(modelItem.envelopePic)) {
            helper.setGone(R.id.icon_image, false)
            Picasso.get().load(modelItem.envelopePic)
                .resize(200, 160)
                .placeholder(R.drawable.icon_imager_helper)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(helper.getView<RoundedImageView>(R.id.icon_image))
        } else {
            helper.setGone(R.id.icon_image, true)
        }

    }
}