package com.sun.unsplash03.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPhoto(
    val urls: PhotoUrls
) : Parcelable
