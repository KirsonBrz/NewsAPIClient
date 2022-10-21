package com.kirson.newsapiclient.presentation.di

import android.app.Application
import com.kirson.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.kirson.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.kirson.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(application, getNewsHeadlinesUseCase, getSearchedNewsUseCase)
    }

}