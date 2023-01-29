package com.motlagh.quicksearch.domain.usecase

import com.motlagh.core.extensions.resultOf
import com.motlagh.core.usecase.FlowUseCaseWithoutInput
import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class GetSavedQueriesUseCase(private val quickSearchRepository: QuickSearchRepository) :
    FlowUseCaseWithoutInput<Result<List<QueryModel>>> {
    override suspend fun invoke(): Flow<Result<List<QueryModel>>> =
        quickSearchRepository.getQueryItems().map {
            resultOf { it }
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
}