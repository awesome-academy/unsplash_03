package com.sun.unsplash03.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.BottomType
import com.sun.unsplash03.utils.base.BaseViewModel

class SearchViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>>
        get() = _photos

    private val _collections = MutableLiveData<MutableList<Collection>>()
    val collections: LiveData<MutableList<Collection>>
        get() = _collections

    private var type = 0

    fun setTypeSearch(typeSearch: Int) {
        type = typeSearch
    }

    fun searchContent(query: String) {
        when (type) {
            BottomType.HOME.ordinal -> {
                searchPhotos(query)
            }
            else -> {
                searchCollections(query)
            }
        }
    }

    private fun searchPhotos(query: String) {
        viewModelScope(onRequest = {
            repository.searchPhotos(query, null)
        }, liveData = _photos)
    }

    private fun searchCollections(query: String) {
        viewModelScope(onRequest = {
            repository.searchCollections(query, null)
        }, liveData = _collections)
    }
}
