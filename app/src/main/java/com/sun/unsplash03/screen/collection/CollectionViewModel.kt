package com.sun.unsplash03.screen.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.LoadMoreRecyclerViewListener
import com.sun.unsplash03.utils.SwipeRefreshListener
import com.sun.unsplash03.utils.base.BaseViewModel
import com.sun.unsplash03.utils.ext.plusAssign

class CollectionViewModel(private val repository: PhotoRepository) : BaseViewModel(),
    LoadMoreRecyclerViewListener, SwipeRefreshListener {

    private val _collections = MutableLiveData<MutableList<Collection>>()
    val collections: LiveData<MutableList<Collection>>
        get() = _collections

    private var currentPage = 1

    init {
        getCollections()
    }

    private fun getCollections() {
        viewModelScope(onRequest = {
            repository.getCollections(null)
        }, liveData = _collections)
    }

    override fun onLoadData() {
        launchTaskSync(onRequest = {
            repository.getCollections(++currentPage)
        }, onSuccess = {
            _collections.plusAssign(it)
        }, onError = {
            exception.value = it
        })
    }

    override fun onRefresh() {
        getCollections()
    }
}
