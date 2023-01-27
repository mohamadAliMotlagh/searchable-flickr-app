package com.motlagh.flickerlist.data.datasource.remote

import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.data.entity.FlickrEntity

interface FlickrRemoteDataSource {
    suspend fun getImagesList(): ResultModel<FlickrEntity>
}