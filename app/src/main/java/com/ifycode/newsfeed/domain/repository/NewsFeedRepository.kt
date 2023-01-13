package com.ifycode.newsfeed.domain.repository

import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {
    suspend fun getTopNews(country : String, apiKey : String) : Flow<Resource<List<Article>>>
}