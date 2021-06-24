package com.sun.unsplash03.screen.photo.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sun.unsplash03.BR
import com.sun.unsplash03.data.model.Photo

class ItemPhotoViewModel(private val onClickItem: (photo: Photo) -> Unit) :
    BaseObservable() {

    @Bindable
    var photo: Photo? = null

    fun setData(data: Photo?) {
        data?.let {
            photo = it
            notifyPropertyChanged(BR.photo)
        }
    }

    fun onItemClicked(view: View) {
        photo?.let {
            onClickItem(it)
        }
    }
}
