package com.sun.unsplash03.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FAV_COLLECTIONS")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String,
    val coverPath: String
)
