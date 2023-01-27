package com.motlagh.flickerlist.data.datasource.remote

import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickerlist.data.entity.FlickrEntity

internal class FlickrRemoteDataSourceImpl
    (private val flickrRemoteDataSource: FlickrRemoteDataSource) : FlickrRemoteDataSource {

    override suspend fun getImagesList(searchedText: String, page: Int) =
        flickrRemoteDataSource.getImagesList(searchedText, page)

}