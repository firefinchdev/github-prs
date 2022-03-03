package com.company.githubprs.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.company.githubprs.R


@Composable
fun EmptyScreen(
    modifier: Modifier
) {
    Surface(
        modifier
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.no_results_message),
                Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }
    }
}