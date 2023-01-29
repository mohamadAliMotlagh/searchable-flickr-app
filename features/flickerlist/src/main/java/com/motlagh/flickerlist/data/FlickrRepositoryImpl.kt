package com.motlagh.flickerlist.data

import com.motlagh.core.ResultModel
import com.motlagh.core.extensions.map
import com.motlagh.core.extensions.resultOf
import com.motlagh.flickerlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickerlist.data.mapper.toFlickrModel
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.flickerlist.domain.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FlickrRepositoryImpl @Inject constructor(private val flickrRemoteDataSource: FlickrRemoteDataSource) :
    FlickrRepository {

    override suspend fun getList(
        searchedText: String
    ): List<FlickrModel> {
        return flickrRemoteDataSource.getImagesList(searchedText).photos?.let { photos ->
            photos.photo?.let { listPhoto ->
                listPhoto.map { it.toFlickrModel() }
            } ?: listOf()
        } ?: listOf()
    }
}