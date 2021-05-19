package com.ldc.wandroidkt.adapter

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.text.HtmlCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ldc.wandroidkt.R
import com.ldc.wandroidkt.model.WxNumberArticleModel
import com.makeramen.roundedimageview.RoundedImageView
import org.ldc.module_res.uts.GlideUts

class WXNumberArticleAdapter :

    BaseQuickAdapter<WxNumberArticleModel.Data, BaseViewHolder>(R.layout.layout_item_article_image) {

    val TAG: String = WXNumberArticleAdapter::class.java.name

    override fun convert(helper: BaseViewHolder, item: WxNumberArticleModel.Data) {
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
            GlideUts.setImageFitCenter(
                helper.itemView.context,
                item.envelopePic,
                helper.getView<RoundedImageView>(R.id.icon_image)
            )
        } else {
            helper.setGone(R.id.icon_image, true)
        }

    }
}