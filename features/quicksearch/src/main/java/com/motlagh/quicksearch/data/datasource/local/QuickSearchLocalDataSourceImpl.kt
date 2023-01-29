package com.motlagh.quicksearch.data.datasource.local

import com.motlagh.quicksearch.data.dao.QueryDao
import com.motlagh.quicksearch.data.entity.QueryItemEntity
import kotlinx.coroutines.flow.Flow

class QuickSearchLocalDataSourceImpl(private val dao: QueryDao) : QuickSearchLocalDataSource {
    override fun getQueryItems() = dao.getQuery()

    override suspend fun saveQueryItem(item: QueryItemEntity) = dao.saveQuery(item)
    override suspend fun deleteQueryItem() {
        dao.deleteQuery()
    }
}