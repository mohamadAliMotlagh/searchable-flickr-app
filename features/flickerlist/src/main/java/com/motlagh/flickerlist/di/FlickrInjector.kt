package com.motlagh.flickerlist.di

import com.motlagh.core.NetworkManager
import com.motlagh.flickerlist.data.FlickrRepositoryImpl
import com.motlagh.flickerlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickerlist.data.datasource.remote.FlickrRemoteDataSourceImpl
import com.motlagh.flickerlist.data.network.FlickrDataService
import com.motlagh.flickerlist.domain.GetListUseCase
import com.motlagh.flickerlist.domain.repository.FlickrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataSource(dataService: FlickrDataService): FlickrRemoteDataSource {
        return FlickrRemoteDataSourceImpl(dataService)
    }

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: FlickrRemoteDataSource): FlickrRepository {
        return FlickrRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideDataService(networkManager: NetworkManager): FlickrDataService {
        return networkManager.create(FlickrDataService::class.java)
    }

    @Singleton
    @Provides
    fun provideUseCase(repository: FlickrRepository): GetListUseCase {
        return GetListUseCase(repository)
    }
}


