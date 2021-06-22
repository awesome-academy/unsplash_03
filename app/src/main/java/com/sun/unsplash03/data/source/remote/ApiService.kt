package com.sun.unsplash03.data.source.remote

import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int = Constants.PER_PAGE_DEFAULT
    ): MutableList<Photo>

    @GET("/collections")
    suspend fun getCollections(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int = Constants.PER_PAGE_DEFAULT
    ): MutableList<Collection>
}
