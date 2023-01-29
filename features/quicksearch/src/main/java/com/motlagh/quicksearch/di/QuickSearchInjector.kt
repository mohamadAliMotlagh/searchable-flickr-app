package com.motlagh.quicksearch.di

import com.motlagh.core.NetworkManager
import com.motlagh.quicksearch.data.QuickSearchRepositoryImpl
import com.motlagh.quicksearch.data.dao.QueryDao
import com.motlagh.quicksearch.data.datasource.local.QuickSearchLocalDataSource
import com.motlagh.quicksearch.data.datasource.local.QuickSearchLocalDataSourceImpl
import com.motlagh.quicksearch.domain.repository.QuickSearchRepository
import com.motlagh.quicksearch.domain.usecase.GetSavedQueriesUseCase
import com.motlagh.quicksearch.domain.usecase.SaveQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuickSearchInjector {

    @Provides
    @Singleton
    fun provideDataSource(dao: QueryDao): QuickSearchLocalDataSource {
        return QuickSearchLocalDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideRepository(dataSource: QuickSearchLocalDataSource): QuickSearchRepository {
        return QuickSearchRepositoryImpl(dataSource);
    }

    @Provides
    fun provideQueryUseCase(repository: QuickSearchRepository): SaveQueryUseCase {
        return SaveQueryUseCase(repository);
    }

    @Provides
    fun provideGetQueriesUseCase(repository: QuickSearchRepository): GetSavedQueriesUseCase {
        return GetSavedQueriesUseCase(repository)
    }
}