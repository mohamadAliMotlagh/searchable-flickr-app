package com.motlagh.quicksearch.data.mapper

import com.motlagh.quicksearch.data.entity.QueryItemEntity
import com.motlagh.quicksearch.domain.model.QueryModel


fun QueryItemEntity.toQueryModel(): QueryModel {
    return QueryModel(query = query)
}

fun QueryModel.toQueryItemEntity(): QueryItemEntity {
    return QueryItemEntity(query = query)
}