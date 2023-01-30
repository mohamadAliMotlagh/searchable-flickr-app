package com.motlagh.flickrlist.di

import com.motlagh.core.NetworkManager
import com.motlagh.flickrlist.data.FlickrRepositoryImpl
import com.motlagh.flickrlist.data.datasource.remote.FlickrRemoteDataSource
import com.motlagh.flickrlist.data.datasource.remote.FlickrRemoteDataSourceImpl
import com.motlagh.flickrlist.data.network.FlickrDataService
import com.motlagh.flickrlist.domain.GetListUseCase
import com.motlagh.flickrlist.domain.repository.FlickrRepository
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


