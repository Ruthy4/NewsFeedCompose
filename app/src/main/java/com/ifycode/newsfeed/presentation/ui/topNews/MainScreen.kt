package com.ifycode.newsfeed.presentation.ui.topNews

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ifycode.newsfeed.BuildConfig
import com.ifycode.newsfeed.R
import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.presentation.ui.topNews.viewmodel.NewsFeedViewModel
import com.ifycode.newsfeed.ui.theme.TransparentWhite_x87
import com.ifycode.newsfeed.util.Constants.COUNTRY
import com.ifycode.newsfeed.util.Resource


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    newsFeedViewModel: NewsFeedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    fun launch() {
        newsFeedViewModel.getTopNews(COUNTRY, BuildConfig.API_KEY)
    }
    launch()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when(val newsResponse = newsFeedViewModel.topNews.value){
            is Resource.Loading -> {
                CircularProgressIndicator(color = TransparentWhite_x87)
            }
            is Resource.Success -> {
                NewsFeed(
                    newsList = newsResponse.value,
                    onClickToDetailScreen = {
                        openTab(context, it)
                    }
                )
            }
            is Resource.Error -> {
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.error_message),
                    onClick = {
                        launch()
                    }
                )
            }
            else -> {}
        }
    }
}

fun openTab(context: Context, article: Article) {
    val packageName = "com.android.chrome"
    val url = article.url
    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)

    builder.setInstantAppsEnabled(true)
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.black))

    val customBuilder = builder.build()

    customBuilder.intent.setPackage(packageName)
    customBuilder.launchUrl(context, Uri.parse(url))

}