package com.motlagh.flickr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.motlagh.quicksearch.data.dao.QueryDao
import com.motlagh.quicksearch.data.entity.QueryItemEntity

private const val DATABASE_VERSION = 1

@Database(
    entities = [QueryItemEntity::class],
    version = DATABASE_VERSION
)
abstract class FlickrDatabase : RoomDatabase() {
    abstract fun queryDao(): QueryDao
}
