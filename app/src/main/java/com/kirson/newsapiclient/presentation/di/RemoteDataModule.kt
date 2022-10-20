package com.kirson.newsapiclient.presentation.di

import com.kirson.newsapiclient.data.api.NewsAPIService
import com.kirson.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.kirson.newsapiclient.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {


    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: NewsAPIService
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }


}