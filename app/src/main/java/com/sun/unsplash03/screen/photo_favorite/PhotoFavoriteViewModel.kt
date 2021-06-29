package com.sun.unsplash03.screen.photo_favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class PhotoFavoriteViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _collection = MutableLiveData<CollectionEntity>()
    val collection: LiveData<CollectionEntity>
        get() = _collection

    lateinit var photos: LiveData<MutableList<PhotoEntity>>

    private fun getListPhotoFavorite(collectionId: Int) = viewModelScope.launch {
        photos = repository.getAllPhotoFavorite(collectionId)
    }

    fun setCollection(data: CollectionEntity?) {
        data?.let {
            _collection.value = it
            getListPhotoFavorite(it.id)
        }
    }

    fun deletePhotoFavorite(photo: PhotoEntity) = viewModelScope.launch {
        repository.deletePhotoFavorite(photo)
    }
}
