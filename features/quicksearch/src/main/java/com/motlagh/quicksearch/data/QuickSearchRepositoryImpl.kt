package com.motlagh.quicksearch.data

import com.motlagh.quicksearch.data.datasource.local.QuickSearchLocalDataSource
import com.motlagh.quicksearch.data.mapper.toQueryItemEntity
import com.motlagh.quicksearch.data.mapper.toQueryModel
import com.motlagh.quicksearch.domain.model.QueryModel
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class QuickSearchRepositoryImpl(private val dataSource: QuickSearchLocalDataSource) :
    QuickSearchRepository {
    override fun getQueryItems(): Flow<List<QueryModel>> {
        val list = dataSource.getQueryItems().map { list ->
            list.map { it.toQueryModel() }
        }
        return list
    }

    override suspend fun saveQueryItem(query: QueryModel) {
        withContext(IO) {
            dataSource.saveQueryItem(query.toQueryItemEntity())
            dataSource.deleteQueryItem()// it helps us to have last 10 searched item.
        }
    }
}