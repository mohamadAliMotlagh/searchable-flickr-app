package com.motlagh.flicker.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.motlagh.flicker.database.FlickrDatabase
import com.motlagh.quicksearch.data.dao.QueryDao
import javax.inject.Singleton

private const val APP_DATABASE_NAME = "flickr_database"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): FlickrDatabase {
        return Room.databaseBuilder(
            context,
            FlickrDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRocketDao(database: FlickrDatabase): QueryDao {
        return database.queryDao()
    }
}