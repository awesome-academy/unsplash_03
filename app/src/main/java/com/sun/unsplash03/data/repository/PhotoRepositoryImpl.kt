package com.sun.unsplash03.data.repository

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.utils.base.BaseRepository

class PhotoRepositoryImpl(
    private val local: PhotoDataSource.Local,
    private val remote: PhotoDataSource.Remote
) : PhotoRepository,
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

    override suspend fun searchCollections(
        query: String,
        page: Int?
    ) = withResultContext { remote.searchCollections(query, page).results }

    override suspend fun insertCollection(collection: CollectionEntity) =
        local.insertCollection(collection)

    override fun getAllCollections() = local.getAllCollections()

    override suspend fun deleteCollection(collection: CollectionEntity) =
        local.deleteCollection(collection)

    override suspend fun updateCollection(collection: CollectionEntity) =
        local.updateCollection(collection)

    override suspend fun insertPhotoFavorite(photo: PhotoEntity) = local.insertPhotoFavorite(photo)

    override suspend fun deletePhotoFavorite(photo: PhotoEntity) = local.deletePhotoFavorite(photo)

    override fun getAllPhotoFavorite(collectionId: Int) = local.getAllPhotoFavorite(collectionId)
}
