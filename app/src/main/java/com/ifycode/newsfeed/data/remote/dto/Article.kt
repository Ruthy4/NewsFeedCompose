package com.ifycode.newsfeed.data.remote.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val author: String?,
    val publishedAt: String?,
    @Embedded(prefix = "source")
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage : String?,
    val content : String?
)