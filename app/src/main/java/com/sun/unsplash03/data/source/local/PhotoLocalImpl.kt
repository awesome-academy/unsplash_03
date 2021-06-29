package com.sun.unsplash03.data.source.local

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.local.dao.CollectionDAO
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

class PhotoLocalImpl(private val collectionDAO: CollectionDAO) : PhotoDataSource.Local {

    override suspend fun insertCollection(collection: CollectionEntity) =
        collectionDAO.insert(collection)

    override suspend fun deleteCollection(collection: CollectionEntity) =
        collectionDAO.deleteCollection(collection)

    override suspend fun updateCollection(collection: CollectionEntity) =
        collectionDAO.update(collection)

    override fun getAllCollections() = collectionDAO.getAllCollections()
}
