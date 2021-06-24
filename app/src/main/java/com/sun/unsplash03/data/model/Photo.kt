package com.sun.unsplash03.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String = "",
    val urls: PhotoUrls
) : Parcelable
