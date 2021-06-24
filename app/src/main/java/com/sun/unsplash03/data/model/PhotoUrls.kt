package com.sun.unsplash03.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUrls(
    val full: String = "",
    val small: String = ""
) : Parcelable
