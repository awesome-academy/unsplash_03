package com.sun.unsplash03.screen.photo_favorite.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sun.unsplash03.BR
import com.sun.unsplash03.data.source.local.entity.PhotoEntity

class ItemPhotoFavoriteViewModel(private val onClickItem: (photo: PhotoEntity) -> Unit) :
    BaseObservable() {

    @Bindable
    var photo: PhotoEntity? = null

    fun setData(data: PhotoEntity?) {
        data?.let {
            photo = it
            notifyPropertyChanged(BR.photo)
        }
    }

    fun onItemClick(view: View) {
        photo?.let {
            onClickItem(it)
        }
    }
}
