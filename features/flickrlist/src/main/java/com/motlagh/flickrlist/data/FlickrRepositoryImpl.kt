package com.motlagh.flickrlist.data

import com.motlagh.flickrlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickrlist.data.mapper.toFlickrModel
import com.motlagh.flickrlist.domain.model.FlickrModel
import com.motlagh.flickrlist.domain.repository.FlickrRepository
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