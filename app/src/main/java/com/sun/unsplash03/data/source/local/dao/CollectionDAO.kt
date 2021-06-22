package com.sun.unsplash03.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

@Dao
interface CollectionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: CollectionEntity)

    @Query("SELECT * FROM FAV_COLLECTIONS")
    fun getAllCollections(): LiveData<MutableList<CollectionEntity>>
}
