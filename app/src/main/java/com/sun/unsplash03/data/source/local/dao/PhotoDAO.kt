package com.sun.unsplash03.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sun.unsplash03.data.source.local.entity.PhotoEntity

@Dao
interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoFavorite(photo: PhotoEntity)

    @Query("SELECT * FROM PHOTO_FAV_COLLECTION WHERE collectionId = :collectionId")
    fun getListPhotoFavorite(collectionId: Int): LiveData<MutableList<PhotoEntity>>

    @Delete
    suspend fun deletePhotoFavorite(photo: PhotoEntity)
}
