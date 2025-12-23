package com.example.travenorowner.features.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy

@Composable
fun TravenorAsyncImage(
    modifier: Modifier = Modifier,
    image: Any?,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalContext.current
    val imageLoader =
        ImageLoader.Builder(context).crossfade(true).diskCachePolicy(CachePolicy.ENABLED)
            .build()

    SubcomposeAsyncImage(
        modifier = modifier,
        model = image,
        loading = {
            TravenorShimmerLoadingOverlay()
        },
        error = {
            TravenorShimmerLoadingOverlay()
        },
        contentDescription = null,
        imageLoader = imageLoader,
        contentScale = contentScale,
    )
}