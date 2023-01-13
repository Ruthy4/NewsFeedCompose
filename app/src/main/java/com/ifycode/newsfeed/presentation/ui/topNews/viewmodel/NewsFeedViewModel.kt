package com.ifycode.newsfeed.presentation.ui.topNews.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.domain.usecase.NewsFeedUseCase
import com.ifycode.newsfeed.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val newsFeedUseCase: NewsFeedUseCase) : ViewModel() {
    private var _topNews = mutableStateOf<Resource<List<Article>>?>(null)
    val topNews: State<Resource<List<Article>>?> get() = _topNews

    fun getTopNews(country: String, apiKey: String) {
        viewModelScope.launch {
            newsFeedUseCase(country, apiKey).collect{
                _topNews.value = it
            }
        }
    }

}