package com.company.githubprs.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.githubprs.R
import com.company.githubprs.extensions.toHumanReadableDate
import com.company.githubprs.models.GithubPullRequest


@Composable
fun PullRequestItem(
    pullRequestItem: GithubPullRequest,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Box(modifier) {
        Row(
            Modifier
                .clickable { isExpanded = !isExpanded },
        ) {
            Image(
                painterResource(R.drawable.merged_pr),
                contentDescription = null,
                Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
            Column(
                Modifier
                    .padding(end = 10.dp)
                    .weight(1f)
            ) {
                AnimatedVisibility(visible = !isExpanded) {
                    Text(
                        pullRequestItem.createdByUserName,
                        Modifier.padding(top = 10.dp),
                        style = MaterialTheme.typography.h6,
                        color = LocalContentColor.current.copy(alpha = 0.6f)
                    )
                }
                Text(
                    pullRequestItem.title,
                    Modifier.padding(top = 5.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.h6,
                    maxLines = if (isExpanded) 10 else 2,
                    overflow = TextOverflow.Ellipsis
                )
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        Modifier.padding(bottom = 10.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(shape = RoundedCornerShape(20.dp)) {
                                UserImage(
                                    pullRequestItem.createdByUserAvatarUrl,
                                    Modifier
                                        .size(32.dp)
                                )
                            }
                            Text(
                                pullRequestItem.createdByUserName,
                                Modifier
                                    .padding(10.dp),
                                style = MaterialTheme.typography.h6,
                            )
                        }
                        Text(
                            stringResource(R.string.created)
                                + pullRequestItem.createdAt.toHumanReadableDate(),
                            fontSize = 12.sp
                        )
                        Text(
                            stringResource(R.string.merged)
                                + pullRequestItem.closedAt.toHumanReadableDate(),
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Box(
                Modifier
                    .padding(end = 10.dp)
            ) {
                Image(
                    painterResource(if (isExpanded) R.drawable.down else R.drawable.up),
                    contentDescription = null,
                    Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .size(28.dp)
                )
            }
        }
        Divider(
            Modifier.align(Alignment.BottomCenter))
    }
}

