package com.motlagh.quicksearch.domain.usecase

import com.motlagh.core.extensions.resultOf
import com.motlagh.core.usecase.ResultUseCase
import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository

class SaveQueryUseCase(private val repository: QuickSearchRepository):ResultUseCase<String,Unit> {
    override suspend fun invoke(rq: String): Result<Unit> {
        return resultOf { repository.saveQueryItem(QueryModel(rq)) }
    }
}