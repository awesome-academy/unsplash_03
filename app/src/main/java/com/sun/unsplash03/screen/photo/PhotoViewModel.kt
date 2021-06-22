package com.sun.unsplash03.screen.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.base.BaseViewModel

class PhotoViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>>
        get() = _photos

    init {
        getPhotos()
    }

    fun getPhotos() {
        launchTaskSync(onRequest = {
            repository.getPhotos(null)
        }, onSuccess = {
            _photos.value = it
        }, onError = {
            exception.value = it
        })
    }
}
