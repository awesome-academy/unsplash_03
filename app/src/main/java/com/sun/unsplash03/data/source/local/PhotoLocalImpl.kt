package com.sun.unsplash03.data.source.local

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.local.dao.CollectionDAO
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

class PhotoLocalImpl(private val collectionDAO: CollectionDAO) : PhotoDataSource.Local {

    override suspend fun insertCollection(collection: CollectionEntity) =
        collectionDAO.insert(collection)

    override fun getAllCollections() = collectionDAO.getAllCollections()
}
