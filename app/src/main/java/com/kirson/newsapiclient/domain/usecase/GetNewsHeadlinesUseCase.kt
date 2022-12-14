package com.kirson.newsapiclient.domain.usecase

import com.kirson.newsapiclient.data.model.APIResponse
import com.kirson.newsapiclient.data.util.Resource
import com.kirson.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }

}