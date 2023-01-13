package com.ifycode.newsfeed.presentation.ui.topNews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ifycode.newsfeed.data.remote.dto.Article
import com.ifycode.newsfeed.ui.theme.NewsFeedTheme
import com.ifycode.newsfeed.ui.theme.TransparentBlack_x67
import com.ifycode.newsfeed.ui.theme.TransparentBlack_x87
import com.ifycode.newsfeed.ui.theme.TransparentWhite_x87

@Composable
fun NewsFeed(
    modifier: Modifier = Modifier,
    newsList: List<Article>? = null,
    onClickToDetailScreen: (Article) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = TransparentBlack_x87,
                title = { Text(text = "News Feed", color = TransparentWhite_x87) }
            )
        }
    ) { padding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
            color = TransparentBlack_x87
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(TransparentBlack_x87),
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = TransparentWhite_x87,
                    text = "Top News",
                    textAlign = TextAlign.Center
                )
                if (newsList != null) {
                    NewsList(newsList, onClickToDetailScreen)
                }
            }

        }
    }
}

@Composable
fun NewsList(newsList: List<Article>, onNewsClick :(Article) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(TransparentBlack_x87),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(newsList) { article ->
            ArticleCard(article, onNewsClick)
        }
    }
}

@Composable
fun ArticleCard(article: Article, onNewsClick :(Article) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 0.dp, end = 0.dp, bottom = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onNewsClick(article)},
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = TransparentWhite_x87
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .matchParentSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = getImageRequest(article.urlToImage?: ""),
                        contentScale = ContentScale.FillBounds
                    ),
                    contentDescription = "News Content Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(TransparentBlack_x67),
                        verticalAlignment = Alignment.Bottom){
                        NewsContent(article)
                    }

                }
            }
        }

    }
}

@Composable
fun NewsContent(article: Article) {
    Column() {
        NewsTitle(text = article.title?: "")
        NewsSubtitle(sourceName = article.source?.Name?: "None")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsFeedTheme {
        NewsFeed()
    }
}