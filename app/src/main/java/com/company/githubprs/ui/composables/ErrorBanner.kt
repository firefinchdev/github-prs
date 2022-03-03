package com.company.githubprs.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.company.githubprs.R


@Composable
fun ErrorBanner(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.some_error_ocurred)
) {
    Banner(
        text = text,
        color = Color.Red,
        textColor = Color.White,
        modifier
    )
}