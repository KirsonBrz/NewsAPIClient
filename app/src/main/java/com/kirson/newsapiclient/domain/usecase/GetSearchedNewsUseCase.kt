package com.kirson.newsapiclient.domain.usecase

import com.kirson.newsapiclient.data.model.APIResponse
import com.kirson.newsapiclient.data.util.Resource
import com.kirson.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(searchQuery: String): Resource<APIResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }


}