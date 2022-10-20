package com.kirson.newsapiclient.domain.usecase

import com.kirson.newsapiclient.data.model.Article
import com.kirson.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {


    suspend fun execute(article: Article) = newsRepository.saveNews(article)


}