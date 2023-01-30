package com.motlagh.flickrlist.data.datasource.remote

import com.motlagh.flickrlist.data.entity.FlickrEntity

interface FlickrRemoteDataSource {
    suspend fun getImagesList(searchedText:String): FlickrEntity
}