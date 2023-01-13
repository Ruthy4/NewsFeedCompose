package com.ifycode.newsfeed.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ifycode.newsfeed.data.remote.dto.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        var DATABASE_NAME: String = "news_db"
    }
}
