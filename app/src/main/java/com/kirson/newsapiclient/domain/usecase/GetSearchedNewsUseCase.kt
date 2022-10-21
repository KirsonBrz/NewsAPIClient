package com.kirson.newsapiclient.domain.usecase

import com.kirson.newsapiclient.data.model.APIResponse
import com.kirson.newsapiclient.data.util.Resource
import com.kirson.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }


}