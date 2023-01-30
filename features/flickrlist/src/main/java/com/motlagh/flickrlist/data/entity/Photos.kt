package com.motlagh.flickrlist.data.entity


import androidx.annotation.Keep

@Keep
data class Photos(
    val page: Int?,
    val pages: String?,
    val perpage: Int?,
    val photo: List<Photo>?,
    val total: String?
)