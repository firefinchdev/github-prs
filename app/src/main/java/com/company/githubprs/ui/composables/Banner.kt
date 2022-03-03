package com.company.githubprs.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@Composable
fun Banner(
    text: String,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier,
        color = color
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = text,
                Modifier.align(Alignment.Center),
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}



