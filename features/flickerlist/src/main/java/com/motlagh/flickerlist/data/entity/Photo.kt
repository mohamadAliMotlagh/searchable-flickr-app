package com.motlagh.flickerlist.data.entity


import androidx.annotation.Keep

@Keep
data class Photo(
    val farm: Int?,
    val id: String?,
    val isfamily: Int?,
    val isfriend: Int?,
    val ispublic: Int?,
    val owner: String?,
    val secret: String?,
    val server: String?,
    val title: String?
)