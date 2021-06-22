package com.sun.unsplash03.screen.collection_photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.base.BaseViewModel

class CollectionPhotoViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _collection = MutableLiveData<Collection>()
    val collection: LiveData<Collection>
        get() = _collection

    private val _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>>
        get() = _photos

    fun setCollection(data: Collection?) {
        data?.let { _collection.value = it }
    }

    fun getPhotosCollection(collectionId: String) {
        viewModelScope(
            onRequest = {
                repository.getCollectionPhotos(collectionId, null)
            }, liveData = _photos
        )
    }
}
