package com.ldc.wandroidkt.adapter

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.text.HtmlCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.SearchArticleModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class SearchArticleAdapter :

    BaseQuickAdapter<SearchArticleModel.Data, BaseViewHolder>(R.layout.layout_item_article_image) {

    val TAG: String = SearchArticleAdapter::class.java.name

    override fun convert(helper: BaseViewHolder, item: SearchArticleModel.Data) {
        item ?: return
        //描述
        val str_html = HtmlCompat.fromHtml(item.desc, HtmlCompat.FROM_HTML_MODE_COMPACT)
        val str_title = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
        //事件

        helper.setText(R.id.tv_title, item.chapterName)
            .setText(R.id.tv_content, "${str_title}\n${str_html}")
            .setText(R.id.tv_time_author, item.author + " " + item.niceShareDate)
        val ck: AppCompatCheckBox = helper.getView<AppCompatCheckBox>(R.id.ck_favorite)
        ck.isChecked = item.collect
        //
        Log.e(TAG, "----图片:" + item.envelopePic)
        //
        //
        if (!TextUtils.isEmpty(item.envelopePic)) {
            helper.setGone(R.id.icon_image, false)
            Picasso.get().load(item.envelopePic)
                .resize(200, 160)
                .placeholder(R.drawable.icon_imager_helper)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(helper.getView<RoundedImageView>(R.id.icon_image))
        } else {
            helper.setGone(R.id.icon_image, true)
        }

    }
}