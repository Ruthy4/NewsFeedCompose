package com.ifycode.newsfeed.data.local.room

import androidx.room.*
import com.ifycode.newsfeed.data.remote.dto.Article

@Dao
interface NewsDao {
    @Query("SELECT * from articles")
    suspend fun getNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<Article>)

    @Delete
    suspend fun delete(articles: List<Article>)
}