package com.sun.unsplash03.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .skipMemoryCache(true)
        .into(this)
}

fun ImageView.loadImageWithUri(uri: String) {
    Glide.with(this)
        .load(File(uri))
        .into(this)
}
