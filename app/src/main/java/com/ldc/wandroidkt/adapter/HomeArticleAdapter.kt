package com.ldc.wandroidkt.adapter

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.text.HtmlCompat
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.commom.cmConstants
import com.ldc.wandroidkt.commom.cmConstants.home_article_code
import com.ldc.wandroidkt.commom.cmConstants.top_article_code
import com.ldc.wandroidkt.model.HomeArticleModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class HomeArticleAdapter :
    BaseMultiItemQuickAdapter<HomeArticleModel.Data, BaseViewHolder>() {

    init {
        addItemType(cmConstants.top_article_code, R.layout.layout_item_article_image)
        addItemType(cmConstants.home_article_code, R.layout.layout_item_article_image)
    }

    val TAG: String = HomeArticleAdapter::class.java.name

    override fun convert(helper: BaseViewHolder, item: HomeArticleModel.Data) {
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
        if (item.itemType == top_article_code) {
            //置顶文章
            helper.setGone(R.id.tv_top, false)
        } else if (item.itemType == home_article_code) {
            helper.setGone(R.id.tv_top, true)
        }
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