package com.sun.unsplash03.utils.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingUtils {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            imageView.loadImageWithUrl(it)
        }
    }

    @JvmStatic
    @BindingAdapter("app:adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }
}
