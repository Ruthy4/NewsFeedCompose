package com.ifycode.newsfeed.data.remote.network

import com.ifycode.newsfeed.data.remote.dto.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedApi {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query ("country") country : String,
        @Query ("apiKey") apiKey : String
    ) : News

}
