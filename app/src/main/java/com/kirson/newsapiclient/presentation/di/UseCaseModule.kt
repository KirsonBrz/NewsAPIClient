package com.kirson.newsapiclient.presentation.di

import com.kirson.newsapiclient.domain.repository.NewsRepository
import com.kirson.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsheadLinesUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }

}