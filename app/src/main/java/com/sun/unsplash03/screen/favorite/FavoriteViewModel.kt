package com.sun.unsplash03.screen.favorite

import androidx.lifecycle.viewModelScope
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    val collections = repository.getAllCollections()

    fun insertCollection(collection: CollectionEntity) = viewModelScope.launch {
        repository.insertCollection(collection)
    }

    fun deleteCollection(collection: CollectionEntity) = viewModelScope.launch {
        repository.deleteCollection(collection)
    }

    fun updateCollection(collection: CollectionEntity) = viewModelScope.launch {
        repository.updateCollection(collection)
    }
}
