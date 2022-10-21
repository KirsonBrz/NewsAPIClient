package com.kirson.newsapiclient.data.repository.dataSource

import com.kirson.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveArticleToDB(article: Article)
    fun getSavedArticles(): Flow<List<Article>>


}