package com.kirson.newsapiclient.data.repository.dataSource

import com.kirson.newsapiclient.data.model.Article

interface NewsLocalDataSource {

    suspend fun saveArticleToDB(article: Article)


}