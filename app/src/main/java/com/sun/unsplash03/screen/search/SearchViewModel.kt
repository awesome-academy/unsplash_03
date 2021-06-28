package com.sun.unsplash03.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.base.BaseViewModel

class SearchViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private var _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>>
        get() = _photos

    fun searchPhotos(query: String) {
        viewModelScope(onRequest = {
            repository.searchPhotos(query, null)
        }, liveData = _photos)
    }
}
