package com.ifycode.newsfeed.domain.usecase

import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.domain.repository.NewsFeedRepository
import com.ifycode.newsfeed.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsFeedUseCase @Inject constructor (private val repository: NewsFeedRepository) {
    suspend operator fun invoke (country: String, apiKey: String) : Flow<Resource<List<Article>>> =
        repository.getTopNews(country, apiKey)
}