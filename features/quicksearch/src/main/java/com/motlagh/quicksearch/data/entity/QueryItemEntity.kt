package com.motlagh.quicksearch.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "QueryItemEntity", indices = [Index(value = ["query"], unique = true)])
data class QueryItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "query")
    val query: String,
)
