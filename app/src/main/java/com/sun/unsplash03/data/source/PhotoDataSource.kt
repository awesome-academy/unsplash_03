package com.sun.unsplash03.data.source

import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.model.SearchCollectionResponse
import com.sun.unsplash03.data.model.SearchPhotoResponse
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

interface PhotoDataSource {

    interface Local {
        suspend fun insertCollection(collection: CollectionEntity)
    }

    interface Remote {
        suspend fun getPhotos(page: Int?): MutableList<Photo>

        suspend fun getCollections(page: Int?): MutableList<Collection>

        suspend fun getCollectionPhotos(collectionId: String, page: Int?): MutableList<Photo>

        suspend fun searchPhotos(query: String, page: Int?): SearchPhotoResponse

        suspend fun searchCollections(query: String, page: Int?): SearchCollectionResponse
    }
}
