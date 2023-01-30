package com.motlagh.flickerlist.data.datasource.remote

import com.motlagh.flickerlist.data.entity.FlickrEntity

interface FlickrRemoteDataSource {
    suspend fun getImagesList(searchedText:String): FlickrEntity
}