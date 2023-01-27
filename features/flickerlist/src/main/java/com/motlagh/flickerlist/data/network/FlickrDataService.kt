package com.motlagh.flickerlist.data.network

import com.motlagh.core.ResultModel
import retrofit2.http.GET

interface FlickrDataService {
    @GET("")
    suspend fun getPairs(): ResultModel<FlickrDataService>
}