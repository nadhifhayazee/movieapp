package com.nadhif.hayazee.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String?) {
    val option = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}