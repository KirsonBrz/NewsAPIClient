package com.kirson.newsapiclient.data.repository.dataSource

import com.kirson.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse>

}