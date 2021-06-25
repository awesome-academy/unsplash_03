package com.sun.unsplash03.data.model

import com.google.gson.annotations.SerializedName

data class Collection(
    val id: String = "",
    val title: String = "",
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto
)
