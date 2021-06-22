package com.sun.unsplash03.screen.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.base.BaseViewModel

class CollectionViewModel(private val repository: PhotoRepository) : BaseViewModel() {

    private val _collections = MutableLiveData<MutableList<Collection>>()
    val collections: LiveData<MutableList<Collection>>
        get() = _collections

    init {
        getCollections()
    }

    private fun getCollections() {
        launchTaskSync(onRequest = {
            repository.getCollections(null)
        }, onSuccess = {
            _collections.value = it
        }, onError = {
            exception.value = it
        })
    }
}
