package com.sun.unsplash03.data.repository

import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.utils.scheduler.DataResult

interface PhotoRepository {
    suspend fun getPhotos(page: Int?): DataResult<MutableList<Photo>>
}
