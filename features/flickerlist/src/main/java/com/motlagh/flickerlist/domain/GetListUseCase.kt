package com.motlagh.flickerlist.domain

import com.motlagh.core.ResultModel
import com.motlagh.core.extensions.resultOf
import com.motlagh.core.usecase.FlowUseCase
import com.motlagh.core.usecase.ResultUseCase
import com.motlagh.flickerlist.domain.model.FlickrModel
import com.motlagh.flickerlist.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListUseCase @Inject constructor(private val repository: FlickrRepository) :
    ResultUseCase<String, List<FlickrModel>> {
    override suspend fun invoke(rq: String) = resultOf { repository.getList(rq) }
}

