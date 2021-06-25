package com.sun.unsplash03.data.source.remote

import com.sun.unsplash03.data.source.PhotoDataSource

class PhotoRemoteImpl(private val apiService: ApiService) : PhotoDataSource.Remote {

    override suspend fun getPhotos(page: Int?) = apiService.getPhotos(page)

    override suspend fun getCollections(page: Int?) = apiService.getCollections(page)
}
