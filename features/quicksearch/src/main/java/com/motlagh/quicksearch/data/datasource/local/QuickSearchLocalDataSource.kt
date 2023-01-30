package com.motlagh.quicksearch.data.datasource.local

import com.motlagh.quicksearch.data.entity.QueryItemEntity
import kotlinx.coroutines.flow.Flow

interface QuickSearchLocalDataSource {
    fun getQueryItems(): Flow<List<QueryItemEntity>>
    suspend fun saveQueryItem(item: QueryItemEntity)
    suspend fun deleteQueryItem()
}