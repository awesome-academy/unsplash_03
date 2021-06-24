package com.sun.unsplash03.data.source

import com.sun.unsplash03.data.model.Photo

interface PhotoDataSource {

    interface Local

    interface Remote {
        suspend fun getPhotos(page: Int?): MutableList<Photo>
    }
}
