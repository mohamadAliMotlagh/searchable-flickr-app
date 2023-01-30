package com.motlagh.flickrlist.data.mapper

import com.motlagh.flickrlist.data.entity.Photo
import com.motlagh.flickrlist.domain.model.FlickrModel


fun Photo.toFlickrModel(): FlickrModel {
    return FlickrModel(
        title = title ?: "",
        mainImageAddress = "https://live.staticflickr.com/${server}/${id}_${secret}_b.jpg",
        thumbnailAddress = "https://live.staticflickr.com/${server}/${id}_${secret}_t.jpg",
        imageID = id ?: ""
    )
}