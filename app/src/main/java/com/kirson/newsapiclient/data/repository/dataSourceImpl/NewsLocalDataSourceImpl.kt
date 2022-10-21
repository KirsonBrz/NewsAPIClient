package com.kirson.newsapiclient.data.repository.dataSourceImpl

import com.kirson.newsapiclient.data.db.ArticleDAO
import com.kirson.newsapiclient.data.model.Article
import com.kirson.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {


    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }


}