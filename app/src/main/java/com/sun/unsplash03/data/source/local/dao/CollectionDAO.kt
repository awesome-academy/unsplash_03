package com.sun.unsplash03.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

@Dao
interface CollectionDAO {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: CollectionEntity)

    @Query("SELECT * FROM FAV_COLLECTIONS")
    fun getAllCollections(): LiveData<MutableList<CollectionEntity>>

    @Delete
    suspend fun deleteCollection(collection: CollectionEntity)

    @Update
    suspend fun update(collection: CollectionEntity)
}
