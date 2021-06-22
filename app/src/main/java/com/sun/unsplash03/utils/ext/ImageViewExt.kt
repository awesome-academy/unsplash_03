package com.sun.unsplash03.utils.ext

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this)
        .load(url)
        .skipMemoryCache(true)
        .into(this)
}
