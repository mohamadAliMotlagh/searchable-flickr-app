package com.motlagh.flickrlist.data.network

import com.motlagh.flickrlist.data.entity.FlickrEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrDataService {
    @GET("rest?format=json&nojsoncallback=1&method=flickr.photos.search&api_key=1508443e49213ff84d566777dc211f2a&page=0&per_page=25")
    suspend fun get(
        @Query("text") searchText: String
    ): FlickrEntity
}