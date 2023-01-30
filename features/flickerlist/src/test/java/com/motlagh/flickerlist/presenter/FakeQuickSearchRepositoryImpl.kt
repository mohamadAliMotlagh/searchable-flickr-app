package com.motlagh.flickerlist.presenter

import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuickSearchRepositoryImpl : QuickSearchRepository {
    override fun getQueryItems(): Flow<List<QueryModel>> {
        return flow { emit(listOf<QueryModel>(QueryModel("test"))) }
    }

    override suspend fun saveQueryItem(query: QueryModel) {

    }
}