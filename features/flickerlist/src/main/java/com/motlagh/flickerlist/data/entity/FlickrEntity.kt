package com.motlagh.flickerlist.data.entity


import androidx.annotation.Keep

@Keep
data class FlickrEntity(
    val photos: Photos?,
    val stat: String?
)