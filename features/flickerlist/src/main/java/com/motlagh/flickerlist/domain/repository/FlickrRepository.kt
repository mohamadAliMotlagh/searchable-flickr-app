package com.motlagh.flickerlist.domain.repository

import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.domain.model.FlickrModel
import kotlinx.coroutines.flow.Flow

interface FlickrRepository {
    suspend fun getList(searchedText: String): List<FlickrModel>
}