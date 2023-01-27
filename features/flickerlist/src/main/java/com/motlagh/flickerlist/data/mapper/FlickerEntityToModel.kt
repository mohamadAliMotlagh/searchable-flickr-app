package com.motlagh.flickerlist.data.mapper

import com.motlagh.flickerlist.data.entity.Photo
import com.motlagh.flickerlist.domain.model.FlickrModel
import java.util.UUID


fun Photo.toModel(): FlickrModel {
    return FlickrModel(
        title = title ?: "",
        mainImageAddress = "https://live.staticflickr.com/$server/{$id}_{$secret}_b.jpg",
        thumbnailAddress = "https://live.staticflickr.com/$server/{$id}_{$secret}_t.jpg",
    )
}