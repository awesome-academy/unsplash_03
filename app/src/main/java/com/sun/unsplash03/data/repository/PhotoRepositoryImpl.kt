package com.sun.unsplash03.data.repository

import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.utils.base.BaseRepository
import com.sun.unsplash03.utils.scheduler.DataResult

class PhotoRepositoryImpl(private val remote: PhotoDataSource.Remote) : PhotoRepository,
    BaseRepository() {

    override suspend fun getPhotos(page: Int?) = withResultContext {
        remote.getPhotos(page)
    }
}
