package com.motlagh.quicksearch.domain.repository

import com.motlagh.quicksearch.domain.model.QueryModel
import kotlinx.coroutines.flow.Flow

interface QuickSearchRepository {
    fun getQueryItems(): Flow<List<QueryModel>>
    suspend fun saveQueryItem(query: QueryModel)
}