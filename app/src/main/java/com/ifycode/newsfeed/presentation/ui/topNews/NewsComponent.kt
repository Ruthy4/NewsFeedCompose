package com.ifycode.newsfeed.presentation.ui.topNews

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.ifycode.newsfeed.R
import com.ifycode.newsfeed.ui.theme.ShimmerAnimationColor
import com.ifycode.newsfeed.ui.theme.ShimmerBackgroundColor
import com.ifycode.newsfeed.ui.theme.SubtitleColor
import com.ifycode.newsfeed.ui.theme.Typography

@Composable
fun NewsTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        text = text,
        maxLines = 2,
        textAlign = TextAlign.Left,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        style = Typography.body2
    )
}

@Composable
fun NewsSubtitle(
    modifier: Modifier = Modifier,
    sourceName: String
) {
    Text(
        modifier = modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp),
        text = sourceName,
        textAlign = TextAlign.Left,
        overflow = TextOverflow.Ellipsis,
        style = Typography.h2,
        color = SubtitleColor,
        maxLines = 1
    )
}

@Composable
fun getImageRequest(imageUrl: String) =
    ImageRequest.Builder(LocalContext.current)
        .error(R.drawable.img)
        .data(imageUrl)
        .crossfade(300)
        .scale(Scale.FILL)
        .build()

@Composable
fun ShowShimmer() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .placeholder(
                color = ShimmerBackgroundColor,
                shape = RoundedCornerShape(10.dp),
                visible = true,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = ShimmerAnimationColor,
                    animationSpec = getShimmerAnimationSpec()
                )
            )
    ) {}
}

fun getShimmerAnimationSpec() = infiniteRepeatable<Float>(
    animation = tween(
        durationMillis = 1000,
        easing = FastOutLinearInEasing,
        delayMillis = 0
    ),
    repeatMode = RepeatMode.Restart
)

@Composable
fun ErrorButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(
            1.dp,
            Color.Blue,
        ),
        onClick = onClick,
    ) {
        Text(text = text)
    }
}
