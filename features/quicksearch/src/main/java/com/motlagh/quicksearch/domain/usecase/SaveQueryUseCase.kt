package com.motlagh.quicksearch.domain.usecase

import com.motlagh.core.extensions.resultOf
import com.motlagh.core.usecase.FlowUseCase
import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveQueryUseCase(private val repository: QuickSearchRepository) {
    suspend fun invoke(rq: String): Result<Unit> {
        return resultOf { repository.saveQueryItem(QueryModel(rq)) }
    }
}