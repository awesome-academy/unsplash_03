package com.sun.unsplash03.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FAV_COLLECTIONS")
@Parcelize
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val coverPath: String
) : Parcelable {

    override fun toString(): String {
        return title
    }
}
