package com.sun.unsplash03.screen.photo

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.utils.LoadMoreRecyclerViewListener
import com.sun.unsplash03.utils.SwipeRefreshListener
import com.sun.unsplash03.utils.base.BaseViewModel
import com.sun.unsplash03.utils.ext.plusAssign

class PhotoViewModel(private val repository: PhotoRepository) : BaseViewModel(), LoadMoreRecyclerViewListener, SwipeRefreshListener {

    private val _photos = MutableLiveData<MutableList<Photo>>()
    val photos: LiveData<MutableList<Photo>>
        get() = _photos

    private var currentPage = 1

    init {
        getPhotos()
    }

    private fun getPhotos() {
        launchTaskSync(onRequest = {
            repository.getPhotos(null)
        }, onSuccess = {
            _photos.value = it
        }, onError = {
            exception.value = it
        })
    }

    override fun onLoadData() {
        launchTaskSync(
            onRequest = {
                repository.getPhotos(++currentPage)
            }, onSuccess = {
                _photos.plusAssign(it)
            }, onError = {
                exception.value = it
            }
        )
    }

    override fun onRefresh() {
        getPhotos()
    }
}
