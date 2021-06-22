package com.sun.unsplash03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collection(
    val id: String = "",
    val title: String = "",
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto
) : Parcelable
