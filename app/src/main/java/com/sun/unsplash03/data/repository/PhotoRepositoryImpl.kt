package com.sun.unsplash03.data.repository

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.utils.base.BaseRepository

class PhotoRepositoryImpl(private val remote: PhotoDataSource.Remote) : PhotoRepository,
    BaseRepository() {

    override suspend fun getPhotos(page: Int?) = withResultContext { remote.getPhotos(page) }

    override suspend fun getCollections(page: Int?) =
        withResultContext { remote.getCollections(page) }

    override suspend fun getCollectionPhotos(
        collectionId: String,
        page: Int?
    ) = withResultContext { remote.getCollectionPhotos(collectionId, page) }

    override suspend fun searchPhotos(query: String, page: Int?) =
        withResultContext { remote.searchPhotos(query, page).results }
}
