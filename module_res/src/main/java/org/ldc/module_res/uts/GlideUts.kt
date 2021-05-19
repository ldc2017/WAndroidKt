package org.ldc.module_res.uts

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.annotations.Nullable
import org.ldc.module_res.R


object GlideUts {

    /**
     * 普通设置图片（含默认图片）
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun setImageFitCenter(context: Context?, url: String?, imageView: ImageView, radius: Float) {
        var radius = radius
        if (radius < 0) {
            radius = 0f
        }
        val options: RequestOptions = RequestOptions
            .fitCenterTransform()
            .transform(RoundedCorners(ConvertUtils.px2dp(radius)))
            .placeholder(R.drawable.no_picture_here)
            .error(R.drawable.no_picture_here)
            .skipMemoryCache(false)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context!!)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(imageView)
    }

    /**
     * 普通设置图片（含默认图片）
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun setImageFitCenter(
        context: Context?,
        @RawRes @DrawableRes @Nullable url: Int?,
        imageView: ImageView,
        radius: Float
    ) {
        var radius = radius
        if (radius < 0) {
            radius = 0f
        }
        val options: RequestOptions = RequestOptions
            .fitCenterTransform()
            .transform(RoundedCorners(ConvertUtils.px2dp(radius)))
            .placeholder(R.drawable.no_picture_here)
            .error(R.drawable.no_picture_here)
            .skipMemoryCache(false)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context!!)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(imageView)
    }

    fun setImageFitCenter(context: Context?, url: String?, imageView: ImageView) {
        setImageFitCenter(context, url, imageView, 2f)
    }

    /**
     * 普通设置图片（含默认图片）
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun setImageCenterCrop(context: Context?, url: String?, imageView: ImageView, radius: Float) {
        var radius = radius
        if (radius < 0) {
            radius = 0f
        }
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.no_picture_here)
            .error(R.drawable.no_picture_here)
            .skipMemoryCache(false)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transforms(CenterCrop(), RoundedCorners(ConvertUtils.px2dp(radius)))
        Glide.with(context!!)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(imageView)
    }

    /**
     * 普通设置图片（含默认图片）
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun setImageCenterCrop(
        context: Context?,
        @RawRes @DrawableRes @Nullable url: Int?,
        imageView: ImageView,
        radius: Float
    ) {
        var radius = radius
        if (radius < 0) {
            radius = 0f
        }
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.no_picture_here)
            .error(R.drawable.no_picture_here)
            .skipMemoryCache(false)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transforms(CenterCrop(), RoundedCorners(ConvertUtils.px2dp(radius)))
        Glide.with(context!!)
            .asBitmap()
            .load(url)
            .apply(options)
            .into(imageView)
    }
}