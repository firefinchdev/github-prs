package com.company.githubprs.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter


@Composable
fun UserImage(
    url: String,
    modifier: Modifier = Modifier
) {
    Image(
        rememberImagePainter(url),
        contentDescription = "User Image",
        modifier = modifier
    )
}