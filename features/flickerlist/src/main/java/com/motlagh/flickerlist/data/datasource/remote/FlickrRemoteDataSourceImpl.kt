package com.motlagh.flickerlist.data.datasource.remote

import com.motlagh.core.ResultModel
import com.motlagh.flickerlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickerlist.data.entity.FlickrEntity
import com.motlagh.flickerlist.data.network.FlickrDataService
import javax.inject.Inject

internal class FlickrRemoteDataSourceImpl
@Inject constructor(private val dataService: FlickrDataService) : FlickrRemoteDataSource {
    override suspend fun getImagesList(searchedText: String) =
        dataService.get(searchedText)
}