package com.motlagh.flickrlist.domain

import com.motlagh.core.extensions.resultOf
import com.motlagh.core.usecase.ResultUseCase
import com.motlagh.flickrlist.domain.model.FlickrModel
import com.motlagh.flickrlist.domain.repository.FlickrRepository
import javax.inject.Inject

class GetListUseCase @Inject constructor(private val repository: FlickrRepository) :
    ResultUseCase<String, List<FlickrModel>> {
    override suspend fun invoke(rq: String) = resultOf { repository.getList(rq) }
}

