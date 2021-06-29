package com.sun.unsplash03.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sun.unsplash03.data.source.local.dao.CollectionDAO
import com.sun.unsplash03.data.source.local.dao.PhotoDAO
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity

@Database(entities = [CollectionEntity::class, PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCollectionDao(): CollectionDAO

    abstract fun getPhotoDao(): PhotoDAO
}
