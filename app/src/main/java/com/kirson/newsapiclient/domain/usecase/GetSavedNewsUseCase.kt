package com.kirson.newsapiclient.domain.usecase

import com.kirson.newsapiclient.data.model.Article
import com.kirson.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }


}