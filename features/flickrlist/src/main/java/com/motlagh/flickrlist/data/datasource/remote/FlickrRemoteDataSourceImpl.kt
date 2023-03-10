package com.motlagh.flickrlist.data.datasource.remote

import com.motlagh.flickrlist.data.network.FlickrDataService
import javax.inject.Inject

internal class FlickrRemoteDataSourceImpl
@Inject constructor(private val dataService: FlickrDataService) : FlickrRemoteDataSource {
    override suspend fun getImagesList(searchedText: String) =
        dataService.get(searchedText)
}