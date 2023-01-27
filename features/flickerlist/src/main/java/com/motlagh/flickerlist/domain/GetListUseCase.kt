package com.motlagh.flickerlist.domain

import com.motlagh.core.ResultModel
import com.motlagh.core.usecase.FlowUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.flickerlist.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow

internal class GetListUseCase(private val repository: FlickrRepository) :
    FlowUseCase<String, ResultModel<List<FlickrModel>>> {
    override suspend operator fun invoke(rq: String) = repository.getList(rq, 0)
}