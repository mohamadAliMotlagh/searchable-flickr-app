package com.motlagh.flickerlist.domain.repository

import com.motlagh.flickerlist.domain.model.FlickrModel

interface FlickrRepository {
    suspend fun getList(searchedText: String): List<FlickrModel>
}