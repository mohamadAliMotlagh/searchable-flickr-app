package com.motlagh.flickerlist.data

import com.motlagh.core.ResultModel
import com.motlagh.core.extensions.map
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
        searchedText: String,
        page: Int
    ): Flow<ResultModel<List<FlickrModel>>> {
        return flow {

            val flickerModel =
                flickrRemoteDataSource.getImagesList(searchedText, page).map { flickerEntity ->
                    flickerEntity.photos?.let { photos ->
                        photos.photo?.let { listPhoto ->
                            listPhoto.map { it.toFlickrModel() }
                        } ?: listOf()
                    } ?: listOf()
                }

            emit(flickerModel)
        }.flowOn(Dispatchers.IO)
    }
}