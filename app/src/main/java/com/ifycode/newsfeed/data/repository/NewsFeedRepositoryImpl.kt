package com.ifycode.newsfeed.data.repository

import com.ifycode.newsfeed.data.local.room.NewsDao
import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.data.remote.network.NewsFeedApi
import com.ifycode.newsfeed.domain.repository.NewsFeedRepository
import com.ifycode.newsfeed.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsFeedRepositoryImpl(private val api: NewsFeedApi, private val newsDao: NewsDao) : NewsFeedRepository {

    override suspend fun getTopNews(country: String, apiKey: String): Flow<Resource<List<Article>>> =
        flow {
            emit(Resource.Loading("Loading"))
            // check if data is already cached in Room
            val cachedNews = newsDao.getNews()
            if (cachedNews.isNotEmpty()) {
                emit(Resource.Success(cachedNews))
            } else {
                // make network request
                val response = api.getTopNews(country, apiKey)
                val news = response.articles
                news.let {
                    newsDao.insert(it)
                }
                emit(Resource.Success(response.articles))

            }
        }.flowOn(Dispatchers.IO)
}