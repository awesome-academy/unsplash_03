package com.sun.unsplash03.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PHOTO_FAV_COLLECTION")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrl: String,
    val collectionId: Int
)
