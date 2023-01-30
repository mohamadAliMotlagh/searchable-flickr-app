package com.motlagh.flickrlist.domain.repository

import com.motlagh.flickrlist.domain.model.FlickrModel

interface FlickrRepository {
    suspend fun getList(searchedText: String): List<FlickrModel>
}