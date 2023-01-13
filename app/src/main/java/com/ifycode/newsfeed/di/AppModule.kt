package com.ifycode.newsfeed.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.ifycode.newsfeed.BuildConfig
import com.ifycode.newsfeed.data.local.room.NewsDao
import com.ifycode.newsfeed.data.remote.network.NewsFeedApi
import com.ifycode.newsfeed.data.repository.NewsFeedRepositoryImpl
import com.ifycode.newsfeed.domain.repository.NewsFeedRepository
import com.ifycode.newsfeed.data.local.room.NewsDatabase
import com.ifycode.newsfeed.data.local.room.NewsDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerAuthorization: Interceptor,
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(headerAuthorization)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /*Add authorization token to the header interceptor*/
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideNewsFeedApi(client: OkHttpClient): NewsFeedApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(NewsFeedApi::class.java)
    }

    //provide repository
    @Provides
    @Singleton
    fun provideNewsFeedRepository(newsFeedApi: NewsFeedApi, newsDao: NewsDao): NewsFeedRepository{
        return NewsFeedRepositoryImpl(api = newsFeedApi , newsDao = newsDao)
    }

    @Singleton
    @Provides
    fun providesNewsDataBase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsDAO(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

}