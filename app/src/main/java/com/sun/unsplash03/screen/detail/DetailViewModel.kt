package com.sun.unsplash03.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _photos = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photos

    val collections = repository.getAllCollections()

    fun setPhoto(data: Photo?) {
        data?.let { _photos.value = it }
    }

    fun insertPhotoFavorite(photo: PhotoEntity) = viewModelScope.launch {
        repository.insertPhotoFavorite(photo)
    }
}
