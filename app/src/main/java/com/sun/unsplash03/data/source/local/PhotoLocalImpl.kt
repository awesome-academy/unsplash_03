package com.sun.unsplash03.data.source.local

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.local.dao.CollectionDAO
import com.sun.unsplash03.data.source.local.dao.PhotoDAO
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity

class PhotoLocalImpl(private val collectionDAO: CollectionDAO, private val photoDAO: PhotoDAO) :
    PhotoDataSource.Local {

    override suspend fun insertCollection(collection: CollectionEntity) =
        collectionDAO.insert(collection)

    override suspend fun deleteCollection(collection: CollectionEntity) =
        collectionDAO.deleteCollection(collection)

    override suspend fun updateCollection(collection: CollectionEntity) =
        collectionDAO.update(collection)

    override suspend fun insertPhotoFavorite(photo: PhotoEntity) =
        photoDAO.insertPhotoFavorite(photo)

    override suspend fun deletePhotoFavorite(photo: PhotoEntity) =
        photoDAO.deletePhotoFavorite(photo)

    override fun getAllCollections() = collectionDAO.getAllCollections()

    override fun getAllPhotoFavorite(collectionId: Int) =
        photoDAO.getListPhotoFavorite(collectionId)
}
