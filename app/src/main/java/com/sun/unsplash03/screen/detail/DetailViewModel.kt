package com.sun.unsplash03.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.utils.base.BaseViewModel

class DetailViewModel : BaseViewModel() {

    private val _photos = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photos

    fun setPhoto(data: Photo?) {
        data?.let {
            _photos.value = it
        }
    }
}
