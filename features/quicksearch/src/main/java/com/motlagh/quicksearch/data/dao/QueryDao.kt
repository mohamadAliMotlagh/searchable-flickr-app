package com.motlagh.quicksearch.data.dao

import androidx.room.*
import com.motlagh.quicksearch.data.entity.QueryItemEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface QueryDao {
    @Query("SELECT * FROM QueryItemEntity")
    fun getQuery(): Flow<List<QueryItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuery(item: QueryItemEntity)

    @Query("DELETE FROM QueryItemEntity where id NOT IN (SELECT id from QueryItemEntity ORDER BY id DESC LIMIT 10)")
    suspend fun deleteQuery()
}