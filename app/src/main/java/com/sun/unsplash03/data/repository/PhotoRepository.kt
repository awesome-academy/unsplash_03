package com.sun.unsplash03.data.repository

import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.utils.scheduler.DataResult

interface PhotoRepository {
    suspend fun getPhotos(page: Int?): DataResult<MutableList<Photo>>

    suspend fun getCollections(page: Int?): DataResult<MutableList<Collection>>

    suspend fun getCollectionPhotos(
        collectionId: String,
        page: Int?
    ): DataResult<MutableList<Photo>>

    suspend fun searchPhotos(query: String, page: Int?): DataResult<MutableList<Photo>>

    suspend fun searchCollections(query: String, page: Int?): DataResult<MutableList<Collection>>

    suspend fun insertCollection(collection: CollectionEntity)
}
