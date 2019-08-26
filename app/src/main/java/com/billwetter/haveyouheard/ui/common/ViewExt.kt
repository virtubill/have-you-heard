package com.billwetter.haveyouheard.ui.common

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.Serializable

/**
 * Utility functions added to views
 *
 * Created by bill.wetter
 */
fun ViewGroup.inflateBinding(layoutResId: Int): ViewDataBinding {
    val inflater = LayoutInflater.from(context)
    return DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutResId, this, false)
}

@BindingAdapter("imgSrc")
fun ImageView.loadImageSrc(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
                .into(this)
    }
}

@BindingAdapter("imgSrcSizeToFit", "placeHolderDrawable")
fun ImageView.loadImageSrcSizeToFitWithPlaceholder(url: String?, placeHolder: Drawable) {
    if (url.isNullOrBlank()) {
        Glide.with(context)
                .load(placeHolder)
                .apply(RequestOptions.fitCenterTransform())
                .into(this)
    } else {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.fitCenterTransform())
                .into(this)
    }
}

@BindingAdapter("imgSrcSizeToFit")
fun ImageView.loadImageSrcSizeToFit(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.fitCenterTransform())
                .into(this)
    }
}

@BindingAdapter("imgSrcAsCircle")
fun ImageView.loadImageSrcCircleCrop(url: String?) {
    if (url != null) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
                .apply(RequestOptions.circleCropTransform())
                .into(this)
    }
}

@BindingAdapter("imgSrcResId")
fun ImageView.loadImageSrcResId(resId: Int?) {
    if (resId != null) {
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.fitCenterTransform())
                .into(this)
    }
}

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("valueOrHide")
fun View.valueOrHide(content: String?) {
    visibility = if (!content.isNullOrBlank()) {
        View.VISIBLE
    } else {
        View.GONE
    }
    this.requestLayout()
}

@BindingAdapter("textOrHide")
fun TextView.textOrHide(content: String?) {
    text = content ?: ""
    visibility = if (!content.isNullOrBlank()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun clearImage(holder: CommonPageAdapter.CommonViewHolder) {
    Glide.with(holder.binding.root.context).clear(holder.binding.root)
}

fun Map<String, Serializable>?.toBundle(): Bundle? {
    return if (this != null) {
        val bundle = Bundle()
        forEach { (key, value) -> bundle.putSerializable(key, value) }
        bundle
    } else {
        null
    }
}
